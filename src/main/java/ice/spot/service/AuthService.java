package ice.spot.service;

import ice.spot.annotation.UserId;
import ice.spot.domain.User;
import ice.spot.dto.global.ResponseDto;
import ice.spot.dto.request.OauthSignUpDto;
import ice.spot.exeption.CommonException;
import ice.spot.exeption.ErrorCode;
import ice.spot.repository.UserRepository;
import ice.spot.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void signUp(Long userId, OauthSignUpDto oauthSignUpDto){
        User oauthUser = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        oauthUser.register(oauthSignUpDto.nickname());
    }

}
