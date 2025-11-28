package com.springboot.bizconnect.domain.auth;

import com.springboot.bizconnect.domain.user.repository.UserRepository;
import com.springboot.bizconnect.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CostomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /*
        데이터베이스에서 email을 가지고 동일 email이 있는지 확인하는 메소드
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return new CustomUserDetails(user);
    }
}
