package com.restapis.expensetracker.model.user_info;

import com.restapis.expensetracker.entity.UserInfo;
import com.restapis.expensetracker.repository.UserInfoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;

    public UserInfoUserDetailsService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<UserInfo> userInfo = userInfoRepository.findByEmail(email);

        if (userInfo.isEmpty()) {
            throw new UsernameNotFoundException("User not found for email: " + email);
        }
        System.out.println(userInfo);

        return new UserInfoUserDetails(userInfo.get());
    }
}
