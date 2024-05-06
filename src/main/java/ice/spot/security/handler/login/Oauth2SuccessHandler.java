package ice.spot.security.handler.login;

import ice.spot.dto.response.JwtTokenDto;
import ice.spot.repository.UserRepository;
import ice.spot.security.info.AuthenticationResponse;
import ice.spot.security.info.UserPrincipal;
import ice.spot.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler implements AuthenticationSuccessHandler {

    @Value("${server.domain}")
    private String domain;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        JwtTokenDto jwtTokenDto = jwtUtil.generateTokens(principal.getUserId(), principal.getRole());

        userRepository.updateRefreshToken(principal.getUserId(), jwtTokenDto.refreshToken());

        AuthenticationResponse.makeLoginSuccessResponse(response, domain, jwtTokenDto, jwtUtil.getRefreshExpiration());

        response.sendRedirect("https://" + domain);
    }
}
