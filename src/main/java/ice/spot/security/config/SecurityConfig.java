package ice.spot.security.config;

import ice.spot.security.filter.JwtAuthenticationFilter;
import ice.spot.security.filter.JwtExceptionFilter;
import ice.spot.security.handler.exception.CustomAccessDeniedHandler;
import ice.spot.security.handler.exception.CustomAuthenticationEntryPointHandler;
import ice.spot.security.handler.login.Oauth2FailureHandler;
import ice.spot.security.handler.login.Oauth2SuccessHandler;
import ice.spot.security.handler.logout.CustomLogoutProcessHandler;
import ice.spot.security.handler.logout.CustomLogoutResultHandler;
import ice.spot.security.provider.JwtAuthenticationManager;
import ice.spot.security.service.CustomOauth2UserDetailService;
import ice.spot.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final Oauth2SuccessHandler oauth2SuccessHandler;
    private final Oauth2FailureHandler oauth2FailureHandler;
    private final CustomOauth2UserDetailService customOauth2UserDetailService;
    private final CustomLogoutProcessHandler customLogoutProcessHandler;
    private final CustomLogoutResultHandler customLogoutResultHandler;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPointHandler customAuthenticationEntryPointHandler;
    private final JwtUtil jwtUtil;
    private final JwtAuthenticationManager jwtAuthenticationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement((session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                )
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers("/api/oauth2/sign-up").permitAll()
                                .requestMatchers("/api/**").hasAnyRole("USER")
                                .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(oauth2SuccessHandler)
                        .failureHandler(oauth2FailureHandler)
                        .userInfoEndpoint(it -> it.userService(customOauth2UserDetailService))
                )
                .logout(logout -> logout
                        .logoutUrl("/api/users/logout")
                        .addLogoutHandler(customLogoutProcessHandler)
                        .logoutSuccessHandler(customLogoutResultHandler)
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(customAuthenticationEntryPointHandler)
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtUtil, jwtAuthenticationManager), LogoutFilter.class
                )
                .addFilterBefore(
                        new JwtExceptionFilter(), JwtAuthenticationFilter.class
                )
                .getOrBuild();
    }
}
