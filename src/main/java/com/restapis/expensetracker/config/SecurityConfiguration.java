package com.restapis.expensetracker.config;

import com.restapis.expensetracker.constant.Endpoint;
import com.restapis.expensetracker.filter.JwtFilter;
import com.restapis.expensetracker.model.user_info.UserInfoUserDetailsService;
import com.restapis.expensetracker.repository.UserInfoRepository;
import com.restapis.expensetracker.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserInfoUserDetailsService userInfoUserDetailsService;

    public SecurityConfiguration(
            HandlerExceptionResolver handlerExceptionResolver,
            UserInfoRepository userInfoRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil,
            UserInfoUserDetailsService userInfoUserDetailsService) {
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userInfoUserDetailsService = userInfoUserDetailsService;
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(userInfoUserDetailsService, jwtUtil, handlerExceptionResolver);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoUserDetailsService(userInfoRepository);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry.requestMatchers(WHITE_LIST).permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        httpSecurity.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return httpSecurity.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST"));
        corsConfiguration.setAllowedHeaders(List.of(

        ));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/v1/", corsConfiguration);

        return source;
    }

    private static final String[] WHITE_LIST = {
            Endpoint.SWAGGER_UI_RESOURCES,
            Endpoint.SWAGGER_RESOURCES,
            Endpoint.BASE_URL + Endpoint.SIGN_UP,
            Endpoint.BASE_URL + Endpoint.VERIFY_EMAIL,
            Endpoint.BASE_URL + Endpoint.SEND_VERIFICATION_MAIL_AGAIN,
            Endpoint.BASE_URL + Endpoint.LOGIN,
            Endpoint.BASE_URL + Endpoint.FORGET_PASSWORD,
            Endpoint.BASE_URL + Endpoint.VALIDATE_RESET_PASSWORD_TOKEN,
            Endpoint.BASE_URL + Endpoint.RESET_PASSWORD,
            Endpoint.BASE_URL + Endpoint.RENEW_ACCESS_TOKEN,
    };
}
