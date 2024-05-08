package ice.spot.service;

import ice.spot.domain.User;
import ice.spot.dto.request.OauthSignUpDto;
import ice.spot.dto.response.JwtTokenDto;
import ice.spot.exception.CommonException;
import ice.spot.exception.ErrorCode;
import ice.spot.repository.UserRepository;
import ice.spot.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signUp(Long userId, OauthSignUpDto oauthSignUpDto){
        User oauthUser = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        oauthUser.register(oauthSignUpDto.nickname());
    }

    @Transactional
    public JwtTokenDto reGenerateTokens(Long userId, String refreshToken){
        log.info("re generate tokens 진입성공");
        User loginUser = userRepository.findByIdAndRefreshToken(userId, refreshToken)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        log.info("유저 조회 성공");
        JwtTokenDto jwtTokenDto = jwtUtil.generateTokens(loginUser.getId(), loginUser.getRole());

        loginUser.updateRefreshToken(jwtTokenDto.refreshToken());
        return jwtTokenDto;
    }
}
