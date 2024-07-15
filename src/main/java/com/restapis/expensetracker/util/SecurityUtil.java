package com.restapis.expensetracker.util;

import com.restapis.expensetracker.entity.UserInfo;
import com.restapis.expensetracker.exception.RestException;
import com.restapis.expensetracker.repository.UserInfoRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {
    private final UserInfoRepository userInfoRepository;

    public SecurityUtil(
            UserInfoRepository userInfoRepository
    ) {
        this.userInfoRepository = userInfoRepository;
    }

    public String retrieveEmailFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getName();
    }

    public UserInfo checkIfUserIsAuthorized(int userId) throws RestException {
        UserInfo userInfo = userInfoRepository.findById(userId).orElseThrow(() -> new RestException(
                "User not found"
        ));

        String email = retrieveEmailFromSecurityContext();

        if (!email.equals(userInfo.getEmail())) {
            throw new RestException("You are not authorized to perform this action");
        }

        return userInfo;
    }
}
