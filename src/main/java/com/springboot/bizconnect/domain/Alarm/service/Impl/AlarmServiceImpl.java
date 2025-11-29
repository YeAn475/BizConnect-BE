package com.springboot.bizconnect.domain.Alarm.service.Impl;

import com.springboot.bizconnect.domain.Alarm.dto.Create.CreateAlarmRequestDto;
import com.springboot.bizconnect.domain.Alarm.dto.Create.CreateAlarmResponseDto;
import com.springboot.bizconnect.domain.Alarm.repository.AlarmRepository;
import com.springboot.bizconnect.domain.Alarm.repository.UserAlarmRepository;
import com.springboot.bizconnect.domain.Alarm.service.AlarmService;
import com.springboot.bizconnect.domain.auth.CustomUserDetails;
import com.springboot.bizconnect.domain.user.repository.UserRepository;
import com.springboot.bizconnect.entity.Alarm;
import com.springboot.bizconnect.entity.User;
import com.springboot.bizconnect.entity.UserAlarm;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {

    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;
    private final UserAlarmRepository userAlarmRepository;

    @Override
    @Transactional // 둘중 하나라도 저장이 안되면 rollback
    public CreateAlarmResponseDto createAlarm(CustomUserDetails userDetails, CreateAlarmRequestDto requestDto) {
        User user = userRepository.findById(userDetails.getUser().getUserNo())
                .orElseThrow(() -> new RuntimeException("회원정보를 찾을 수 없습니다."));

        Alarm alarm = Alarm.builder()
                .senderUserNo(userDetails.getUser())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        alarmRepository.save(alarm);


        UserAlarm userAlarm = UserAlarm.builder()
                .no(new UserAlarm.userAlarmNo(user.getUserNo(), alarm.getAlarmNo()))
                .user(user)
                .alarm(alarm)
                .isRead(false)
                .isDeleted(false)
                .build();

        userAlarmRepository.save(userAlarm);


        return CreateAlarmResponseDto.builder()
                .message("전송되었습니다.")
                .build();
    }

    @Override
    @Transactional
    public void sendToUser(User sender, Long receiverNo, String title, String content) {
        if(sender.getUserNo().equals(receiverNo)) return;

        Alarm alarm = alarmRepository.save(Alarm.builder()
                        .senderUserNo(sender)
                        .title(title)
                        .content(content)
                .build());

        User receiver = userRepository.findById(receiverNo)
                .orElseThrow(() -> new RuntimeException("수신자를 찾을 수 없습니다."));

        userAlarmRepository.save(UserAlarm.builder()
                        .user(receiver)
                        .alarm(alarm)
                        .isRead(false)
                        .isDeleted(false)
                .build());
    }

    @Override
    @Transactional
    public void sendToOperationAdmins(User sender, String title, String content){
        Alarm alarm = alarmRepository.save(Alarm.builder()
                .senderUserNo(sender)
                .title(title)
                .content(content)
                .build());

        // 운영관리자, 개발관리자 찾기
        List<User> admins = userRepository.findByRole_NameIn(
                Arrays.asList("운영관리자", "개발관리자")
        );

        for (User admin : admins) {
            if(admin.getUserNo().equals(sender.getUserNo())) continue;

            userAlarmRepository.save(UserAlarm.builder()
                    .user(admin)
                    .alarm(alarm)
                    .isRead(false)
                    .isDeleted(false)
                    .build());
        }
    }

    @Override
    @Transactional
    public void sendToCompanyAdmins(User sender, Long companyNo, String title, String content) {
        Alarm alarm = alarmRepository.save(Alarm.builder()
                .senderUserNo(sender)
                .title(title)
                .content(content)
                .build());

        // 특정 회사의 회사관리자들 찾기
        List<User> admins = userRepository.findByCompany_CompanyNoAndRole_Name(
                companyNo, "회사관리자"
        );

        for (User admin : admins) {
            if(admin.getUserNo().equals(sender.getUserNo())) continue;
            userAlarmRepository.save(UserAlarm.builder()
                    .user(admin)
                    .alarm(alarm)
                    .isRead(false)
                    .isDeleted(false)
                    .build());
        }
    }

    @Override
    @Transactional
    public void sendToCompanyMembers(User sender, Long companyNo, String title, String content) {
        Alarm alarm = alarmRepository.save(Alarm.builder()
                .senderUserNo(sender)
                .title(title)
                .content(content)
                .build());

        // 특정 회사의 모든 직원 찾기
        List<User> members = userRepository.findByCompany_CompanyNo(companyNo);

        for (User member : members) {
            if(member.getUserNo().equals(sender.getUserNo())) continue;
            userAlarmRepository.save(UserAlarm.builder()
                    .user(member)
                    .alarm(alarm)
                    .isRead(false)
                    .isDeleted(false)
                    .build());
        }
    }

    @Override
    @Transactional
    public void sendToBranchMembers(User sender, Long companyNo, Long branchNo, String title, String content) {
        Alarm alarm = alarmRepository.save(Alarm.builder()
                .senderUserNo(sender)
                .title(title)
                .content(content)
                .build());

        // 특정 회사의 특정 지점 직원들 찾기
        List<User> members = userRepository.findByCompany_CompanyNoAndCompany_Branch_BranchNo(
                companyNo, branchNo
        );

        for (User member : members) {
            if(member.getUserNo().equals(sender.getUserNo())) continue;
            userAlarmRepository.save(UserAlarm.builder()
                    .user(member)
                    .alarm(alarm)
                    .isRead(false)
                    .isDeleted(false)
                    .build());
        }
    }

    @Override
    @Transactional
    public void sendToUsers(User sender, List<Long> receiverNos, String title, String content) {
        Alarm alarm = alarmRepository.save(Alarm.builder()
                .senderUserNo(sender)
                .title(title)
                .content(content)
                .build());

        for (Long receiverNo : receiverNos) {
            if(receiverNo.equals(sender.getUserNo())) continue;
            User receiver = userRepository.findById(receiverNo)
                    .orElseThrow(() -> new RuntimeException("수신자를 찾을 수 없습니다."));

            userAlarmRepository.save(UserAlarm.builder()
                    .user(receiver)
                    .alarm(alarm)
                    .isRead(false)
                    .isDeleted(false)
                    .build());
        }
    }
}
