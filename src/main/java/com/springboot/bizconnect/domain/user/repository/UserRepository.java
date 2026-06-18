package com.springboot.bizconnect.domain.user.repository;

import com.springboot.bizconnect.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    // 여러 역할 이름으로 찾기
    List<User> findByRole_NameIn(List<String> roleNames);

    // 특정 회사의 특정 역할 찾기
    List<User> findByCompany_CompanyNoAndRole_Name(Long companyNo, String roleName);

    // 특정 회사의 모든 직원 찾기
    List<User> findByCompany_CompanyNo(Long companyNo);

    // 특정 회사의 특정 지점 직원 찾기
    List<User> findByCompany_CompanyNoAndCompany_Branch_BranchNo(Long companyNo, Long branchNo);

    // 이름 또는 이메일로 검색 (UserRepository에 추가)
    Page<User> findByNameContainingOrEmailContaining(String name, String email, Pageable pageable);
}

