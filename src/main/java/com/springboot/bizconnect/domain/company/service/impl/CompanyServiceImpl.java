package com.springboot.bizconnect.domain.company.service.impl;

import com.springboot.bizconnect.domain.alarm.service.Impl.AlarmServiceImpl;
import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.company.dto.account.AccountReqeustDto;
import com.springboot.bizconnect.domain.company.dto.account.AccountResponseDto;
import com.springboot.bizconnect.domain.company.dto.create.CreateCompanyRequestDto;
import com.springboot.bizconnect.domain.company.dto.create.CreateCompanyResponseDto;
import com.springboot.bizconnect.domain.company.dto.list.CompanyListRequestDto;
import com.springboot.bizconnect.domain.company.dto.list.CompanyListResponseDto;
import com.springboot.bizconnect.domain.company.dto.update.UpdateCompanyRequestDto;
import com.springboot.bizconnect.domain.company.dto.update.UpdateCompanyResponseDto;
import com.springboot.bizconnect.domain.company.repository.AccountRepository;
import com.springboot.bizconnect.domain.company.repository.CompanyRepository;
import com.springboot.bizconnect.domain.company.repository.CorporateAccountRepository;
import com.springboot.bizconnect.domain.companyAlarm.repository.AffiliationRepository;
import com.springboot.bizconnect.domain.companyAlarm.repository.BranchRepository;
import com.springboot.bizconnect.domain.companyAlarm.repository.CompanyRequestRepository;
import com.springboot.bizconnect.domain.company.service.CompanyService;
import com.springboot.bizconnect.domain.user.repository.RoleRepository;
import com.springboot.bizconnect.domain.user.repository.UserRepository;
import com.springboot.bizconnect.entity.Account;
import com.springboot.bizconnect.entity.Affiliation;
import com.springboot.bizconnect.entity.Branch;
import com.springboot.bizconnect.entity.Company;
import com.springboot.bizconnect.entity.CompanyRequest;
import com.springboot.bizconnect.entity.CorporateAccount;
import com.springboot.bizconnect.entity.Role;
import com.springboot.bizconnect.entity.User;
import com.springboot.bizconnect.enums.AlarmType;
import com.springboot.bizconnect.enums.CompanyType;
import com.springboot.bizconnect.enums.requestStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyRequestRepository companyRequestRepository;
    private final AlarmServiceImpl alarmService;
    private final UserRepository userRepository;
    private final AffiliationRepository affiliationRepository;
    private final BranchRepository branchRepository;
    private final RoleRepository roleRepository;
    private final CorporateAccountRepository corporateAccountRepository;
    private final AccountRepository accountRepository;

    @Override
    public CreateCompanyResponseDto createCompany(CustomUserDetails userDetails, CreateCompanyRequestDto createCompanyRequestDto) {
        String affiliationName = createCompanyRequestDto.getAffiliationName();
        if (affiliationName == null || affiliationName.trim().isEmpty()) {
            affiliationName = "없음";
        }

        String branchName = createCompanyRequestDto.getBranchName();
        if (branchName == null || branchName.trim().isEmpty()) {
            branchName = "없음";
        }

        CompanyRequest companyRequest = CompanyRequest.builder()
                .user(userDetails.getUser())
                .name(createCompanyRequestDto.getCompanyName())
                .affiliationName(affiliationName)
                .branchName(branchName)
                .address(createCompanyRequestDto.getAddress())
                .phoneNumber(createCompanyRequestDto.getPhoneNumber())
                .status(CompanyType.PENDING)
                .build();

        companyRequestRepository.save(companyRequest);

        alarmService.sendToOperationAdmins(
                userDetails.getUser(),
                "회사 등록 요청",
                userDetails.getUser().getName() + "님이 회사 등록을 요청했습니다." + "\n"
                + "회사명 : " + createCompanyRequestDto.getCompanyName() + "\n"
                + "소속명 : " + affiliationName + "\n"
                + "브랜드명 : " + branchName + "\n"
                + "주소 : " + createCompanyRequestDto.getAddress() + "\n"
                + "번호 : " + createCompanyRequestDto.getPhoneNumber() + "\n"
                + "설명 : " + createCompanyRequestDto.getContent() + "\n"
                ,
                AlarmType.COMPANY_REGISTER_REQUEST,
                companyRequest.getCompanyRequestNo()  // 저장 후 ID 사용
        );

        return CreateCompanyResponseDto.builder()
                .message("회사 등록 요청이 완료되었습니다.")
                .build();
    }

    @Override
    public UpdateCompanyResponseDto updateCompany(CustomUserDetails userDetails, UpdateCompanyRequestDto requestDto) {
        User user = userRepository.findById(userDetails.getUser().getUserNo())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        if (user.getRole() == null || !user.getRole().getRoleNo().equals(2L)) throw new RuntimeException("회사 정보 수정 권한이 없습니다.");


        Company company = user.getCompany();
        if (company == null) throw new RuntimeException("소속된 회사가 없습니다.");

        if (requestDto.getCompanyName() != null) company.setName(requestDto.getCompanyName());

        if (requestDto.getCompanyAddress() != null) company.setAddress(requestDto.getCompanyAddress());

        if (requestDto.getCompanyPhone() != null) company.setPhoneNumber(requestDto.getCompanyPhone());

        if (requestDto.getAffiliationName() != null) {
            Affiliation affiliation = affiliationRepository.findByName(requestDto.getAffiliationName())
                    .orElseGet(() -> affiliationRepository.save(
                            Affiliation.builder()
                                    .name(requestDto.getAffiliationName())
                                    .build()
                    ));
            company.setAffiliation(affiliation);
        }

        if (requestDto.getBranchName() != null) {
            Branch branch = branchRepository.findByName(requestDto.getBranchName())
                    .orElseGet(() -> branchRepository.save(
                            Branch.builder()
                                    .name(requestDto.getBranchName())
                                    .build()
                    ));
            company.setBranch(branch);
        }

        companyRepository.save(company);

        return UpdateCompanyResponseDto.builder()
                .message("회사 정보가 수정되었습니다.")
                .companyName(company.getName())
                .companyName(company.getName())
                .branchName(company.getBranch().getName())
                .affiliationName(company.getAffiliation().getName())
                .address(company.getAddress())
                .phoneNumber(company.getPhoneNumber())
                .createdAt(company.getCreatedAt())
                .updatedAt(company.getUpdatedAt())
                .build();
    }

    @Override
    public List<CompanyListResponseDto> companyList(CustomUserDetails userDetails, CompanyListRequestDto requestDto) {
        PageRequest pageRequest = PageRequest.of(requestDto.getPage(), requestDto.getSize());

        return companyRepository.findAll(pageRequest)
                .map(company -> CompanyListResponseDto.builder()
                        .companyNo(company.getCompanyNo())
                        .companyName(company.getName())
                        .affiliationName(company.getAffiliation().getName())
                        .branchName(company.getBranch().getName())
                        .address(company.getAddress())
                        .phoneNumber(company.getPhoneNumber())
                        .build())
                .getContent();
    }

    @Override
    public AccountResponseDto createCompanyAccount(CustomUserDetails userDetails, AccountReqeustDto requestDto) {
        User user = userRepository.findById(userDetails.getUser().getUserNo())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Company company = user.getCompany();
        if (company == null) throw new RuntimeException("소속된 회사가 없습니다.");


        Long userRoleNo = user.getRole().getRoleNo();
        if (!Arrays.asList(2L, 3L, 4L).contains(userRoleNo)) throw new RuntimeException("권한이 없습니다.");

        CorporateAccount corporateAccount = CorporateAccount.builder()
                .number(requestDto.getNumber())
                .build();

        corporateAccountRepository.save(corporateAccount);

        // acount 복합키 테이블에도 저장
        Account account = Account.builder()
                .no(new Account.AccountNo(company.getCompanyNo(), corporateAccount.getCorporateAccountNo()))
                .company(company)
                .corporateAccount(corporateAccount)
                .build();

        accountRepository.save(account);

        return AccountResponseDto.builder()
                .Companyname(user.getCompany().getName())
                .number(corporateAccount.getNumber())
                .build();
    }
}
