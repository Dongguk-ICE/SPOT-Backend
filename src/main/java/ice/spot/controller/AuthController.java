package ice.spot.controller;

import ice.spot.annotation.UserId;
import ice.spot.constant.Constants;
import ice.spot.dto.global.ResponseDto;
import ice.spot.dto.request.OauthSignUpDto;
import ice.spot.dto.response.JwtTokenDto;
import ice.spot.exception.CommonException;
import ice.spot.exception.ErrorCode;
import ice.spot.service.AuthService;
import ice.spot.util.CookieUtil;
import ice.spot.util.HeaderUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    @Value("${server.domain}")
    private String domain;

    private final AuthService authService;

    @PostMapping("/oauth2/sign-up")
    public ResponseDto<?> signUp(@UserId Long userId, @RequestBody OauthSignUpDto oauthSignUpDto) {
        authService.signUp(userId, oauthSignUpDto);
        return ResponseDto.ok(null);
    }

    @PostMapping("/auth/reissue")
    public ResponseDto<?> reissue(
            HttpServletRequest request,
            HttpServletResponse response,
            @UserId Long userId){
        log.info("controller 진입 성공");
        String refreshToken = HeaderUtil.refineHeader(request, Constants.PREFIX_AUTH, Constants.PREFIX_BEARER)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_HEADER_VALUE));
        log.info("헤더값 조회 성공");
        JwtTokenDto jwtTokenDto = authService.reGenerateTokens(userId, refreshToken);

        CookieUtil.addCookie(response, domain, Constants.ACCESS_COOKIE_NAME, jwtTokenDto.accessToken());
        CookieUtil.addSecureCookie(response, domain, Constants.REFRESH_COOKIE_NAME, jwtTokenDto.refreshToken(), 60 * 60 * 24 * 14);

        return ResponseDto.ok(jwtTokenDto);
    }
}
