package com.restapis.expensetracker.util;

import com.restapis.expensetracker.entity.Otp;
import com.restapis.expensetracker.entity.UserInfo;
import com.restapis.expensetracker.enumeration.OtpType;
import com.restapis.expensetracker.repository.OtpRepository;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;

@Component
public class OtpUtil {
    private static final int OTP_LENGTH = 6;

    private final OtpRepository otpRepository;
    private final TemplateEngine templateEngine;

    public OtpUtil(
            OtpRepository otpRepository,
            TemplateEngine templateEngine
    ) {
        this.otpRepository = otpRepository;
        this.templateEngine = templateEngine;
    }

    public void generateOtpAndSendEmail(UserInfo userInfo, OtpType otpType) {
        String email = userInfo.getEmail();

        String otp = generateOtp();

        Context context = new Context();
        context.setVariable("name", userInfo.getName());
        context.setVariable("otp", otp);

        String emailTemplate = templateEngine.process("signup", context);

        MailUtil.sendEmail(email, "OTP for Expense Tracker Password Reset", emailTemplate);

        Otp otpEntity = new Otp();
        otpEntity.setType(OtpType.EMAIL);
        otpEntity.setEmail(email);
        otpEntity.setOtp(otp);
        otpEntity.setExpiryTime(Instant.now().plus(Duration.ofMinutes(5)));
        otpRepository.save(otpEntity);
    }

    public String generateOtp() {
        SecureRandom secureRandom = new SecureRandom();
        int otp = secureRandom.nextInt((int) Math.pow(10, OTP_LENGTH));
        return String.format("%06d", otp);
    }
}
