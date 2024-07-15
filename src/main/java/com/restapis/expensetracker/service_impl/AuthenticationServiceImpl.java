package com.restapis.expensetracker.service_impl;

import com.restapis.expensetracker.entity.Otp;
import com.restapis.expensetracker.entity.ResetPasswordToken;
import com.restapis.expensetracker.entity.Role;
import com.restapis.expensetracker.entity.UserInfo;
import com.restapis.expensetracker.enumeration.OtpType;
import com.restapis.expensetracker.exception.RestException;
import com.restapis.expensetracker.model.api.ApiResponse;
import com.restapis.expensetracker.model.forget_password.ForgetPasswordRequest;
import com.restapis.expensetracker.model.login.LoginRequest;
import com.restapis.expensetracker.model.login.LoginResponse;
import com.restapis.expensetracker.model.reset_password.ResetPasswordRequest;
import com.restapis.expensetracker.model.send_otp_mail_again.SendOtpMailAgainRequest;
import com.restapis.expensetracker.model.sign_up.UserInfoRequest;
import com.restapis.expensetracker.model.sign_up.UserInfoResponse;
import com.restapis.expensetracker.model.verify_otp.OtpRequest;
import com.restapis.expensetracker.repository.OtpRepository;
import com.restapis.expensetracker.repository.ResetPasswordTokenRepository;
import com.restapis.expensetracker.repository.RoleRepository;
import com.restapis.expensetracker.repository.UserInfoRepository;
import com.restapis.expensetracker.service.AuthenticationService;
import com.restapis.expensetracker.util.JwtUtil;
import com.restapis.expensetracker.util.MailUtil;
import com.restapis.expensetracker.util.OtpUtil;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final int OTP_LENGTH = 6;

    private final UserInfoRepository userInfoRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RoleRepository roleRepository;
    private final OtpRepository otpRepository;
    private final OtpUtil otpUtil;
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;
    private final TemplateEngine templateEngine;

    public AuthenticationServiceImpl(
            UserInfoRepository userInfoRepository,
            ModelMapper modelMapper,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            RoleRepository roleRepository,
            OtpRepository otpRepository,
            OtpUtil otpUtil,
            ResetPasswordTokenRepository resetPasswordTokenRepository,
            TemplateEngine templateEngine) {
        this.userInfoRepository = userInfoRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.roleRepository = roleRepository;
        this.otpRepository = otpRepository;
        this.otpUtil = otpUtil;
        this.resetPasswordTokenRepository = resetPasswordTokenRepository;
        this.templateEngine = templateEngine;
    }

    @Override
    public UserInfoResponse signup(UserInfoRequest userInfoRequest) throws RestException {
        String email = userInfoRequest.getEmail();

        Optional<UserInfo> isUserInfoExists = userInfoRepository.findByEmail(email);

        if(isUserInfoExists.isPresent()) {
            throw new RestException("User already exists");
        }

        UserInfo userInfo = modelMapper.map(userInfoRequest, UserInfo.class);
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));

        Optional<Role> isNormalRoleExists = roleRepository.findByName("ROLE_NORMAL");
        if(isNormalRoleExists.isEmpty()) {
            throw new RestException("Normal role not found");
        }

        userInfo.setRoles(Set.of(isNormalRoleExists.get()));
        userInfo.setVerified(false);
        UserInfo userInfoInserted = userInfoRepository.save(userInfo);

        CompletableFuture.runAsync(() -> otpUtil.generateOtpAndSendEmail(userInfoInserted, OtpType.EMAIL));

        return modelMapper.map(userInfoInserted, UserInfoResponse.class);
    }

    @Override
    public ApiResponse verifyOtp(OtpRequest otpRequest) throws RestException {
        UserInfo userInfo = userInfoRepository.findByEmail(otpRequest.getEmail()).orElseThrow(
                () -> new RestException("User not found for email: " + otpRequest.getEmail())
        );

        Otp otp = otpRepository.findByEmail(otpRequest.getEmail()).orElseThrow(() ->
                new RestException("Invalid OTP"));

        if(!otpRequest.getOtp().equals(otp.getOtp())) {
            throw new RestException("Invalid OTP");
        }

        if(Instant.now().isAfter(otp.getExpiryTime())) {
            otpRepository.delete(otp);
            throw new RestException("OTP has expired");
        }

        otpRepository.delete(otp);

        userInfo.setVerified(true);
        userInfoRepository.save(userInfo);

        return new ApiResponse("OTP verified successfully");
    }

    @Override
    public ApiResponse sendOtpMailAgain(SendOtpMailAgainRequest sendOtpMailAgainRequest) throws RestException {
        UserInfo userInfo = userInfoRepository.findByEmail(sendOtpMailAgainRequest.getEmail()).orElseThrow(() ->
                new RestException("User not found for email: " + sendOtpMailAgainRequest.getEmail()));

        otpUtil.generateOtpAndSendEmail(userInfo, OtpType.EMAIL);

        return new ApiResponse("OTP sent successfully");
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws RestException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        if(authentication.isAuthenticated()) {
            String email = loginRequest.getEmail();

            Optional<UserInfo> isUserInfoExists = userInfoRepository.findByEmail(email);

            if(isUserInfoExists.isEmpty()) {
                throw new RestException("User not found for email: " + email);
            }

            UserInfo userInfo = isUserInfoExists.get();

            if(!userInfo.isVerified()) {
                throw new RestException("User is not verified");
            }

            String accessToken = jwtUtil.generateToken(userInfo,9);
            String refreshToken = jwtUtil.generateToken(userInfo,24 * 30);

            UserInfoResponse userInfoResponse = modelMapper.map(userInfo, UserInfoResponse.class);

            return new LoginResponse(accessToken, refreshToken, userInfoResponse);
        } else {
            throw new RestException("Invalid credentials");
        }
    }

    @Override
    public ApiResponse forgetPassword(ForgetPasswordRequest forgetPasswordRequest) throws RestException {
        UserInfo userInfo = userInfoRepository.findByEmail(forgetPasswordRequest.getEmail()).orElseThrow(() ->
                new RestException("User not found for email: " + forgetPasswordRequest.getEmail()));

        UUID token = UUID.randomUUID();

        String resetPasswordUrl = "http://localhost:8080/api/v1/validate-reset-password-token?token=" + token;

        Context context = new Context();
        context.setVariable("name", userInfo.getName());
        context.setVariable("resetPasswordUrl", resetPasswordUrl);

        String emailTemplate = templateEngine.process("reset-password", context);

        MailUtil.sendEmail(userInfo.getEmail(), "Reset Password", emailTemplate);


        ResetPasswordToken resetPasswordToken = ResetPasswordToken.builder()
                .email(userInfo.getEmail())
                .token(token)
                .isUsed(false)
                .expiryTime(Instant.now().plus(Duration.ofMinutes(30)))
                .build();

        resetPasswordTokenRepository.save(resetPasswordToken);

        return new ApiResponse(
                "Reset password token generated successfully. Please check your email for the reset password link"
        );
    }

    @Override
    public ApiResponse validateResetPasswordToken(UUID token) throws RestException {
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByToken(token).orElseThrow(() ->
                new RestException("Invalid token"));

        if(Instant.now().isAfter(resetPasswordToken.getExpiryTime())) {
            resetPasswordTokenRepository.delete(resetPasswordToken);
            throw new RestException("Token has expired");
        }

        if(resetPasswordToken.isUsed()) {
            throw new RestException("Token has already been used");
        }

        resetPasswordToken.setUsed(true);
        resetPasswordTokenRepository.save(resetPasswordToken);

        return new ApiResponse("Token validated successfully");
    }

    @Override
    public ApiResponse resetPassword(UUID token, ResetPasswordRequest resetPasswordRequest) throws RestException {
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByToken(token).orElseThrow(() ->
                new RestException("Invalid token"));

        if(Instant.now().isAfter(resetPasswordToken.getExpiryTime())) {
            resetPasswordTokenRepository.delete(resetPasswordToken);
            throw new RestException("Token has expired");
        }

        String email = resetPasswordToken.getEmail();

        UserInfo userInfo = userInfoRepository.findByEmail(email).orElseThrow(() ->
                new RestException("User not found for email: " + email));

        userInfo.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
        userInfoRepository.save(userInfo);

        resetPasswordTokenRepository.delete(resetPasswordToken);
        return new ApiResponse("Password reset successfully");
    }
}
