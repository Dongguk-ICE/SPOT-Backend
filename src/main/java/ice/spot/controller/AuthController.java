package ice.spot.controller;

import ice.spot.annotation.UserId;
import ice.spot.dto.global.ResponseDto;
import ice.spot.dto.request.OauthSignUpDto;
import ice.spot.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/oauth2/sign-up")
    public ResponseDto<?> signUp(@UserId Long userId, @RequestBody OauthSignUpDto oauthSignUpDto) {
        authService.signUp(userId, oauthSignUpDto);
        return ResponseDto.ok(null);
    }
}
