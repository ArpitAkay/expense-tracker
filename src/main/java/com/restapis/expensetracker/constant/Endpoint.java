package com.restapis.expensetracker.constant;

public interface Endpoint {
    String BASE_URL = "/api/v1";

    // Swagger Endpoints
    String SWAGGER_UI_RESOURCES = "/swagger-ui/**";
    String SWAGGER_RESOURCES = "/v3/api-docs/**";

    // Authentication Endpoints
    String SIGN_UP =  "/signup";
    String VERIFY_OTP = "/verify-otp";
    String SEND_OTP_MAIL_AGAIN = "/send-otp-mail-again";
    String LOGIN = "/login";
    String FORGET_PASSWORD = "/forget-password";
    String VALIDATE_RESET_PASSWORD_TOKEN = "/validate-reset-password-token";
    String RESET_PASSWORD = "/reset-password";
    String RENEW_ACCESS_TOKEN = "/renew-access-token";

    // User Pin Endpoints
    String ADD_USER_PIN = "/user-pin/{userId}";
    String VERIFY_USER_PIN = "/user-pin-verify/{userId}";

    // User Infos Endpoints
    String USER_INFO = "/user-info/{userId}";

    // Roles Endpoints
    String ROLES = "/roles";

    // Permissons Endpoints
    String PERMISSIONS = "/permissions";
}
