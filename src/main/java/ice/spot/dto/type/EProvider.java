package ice.spot.dto.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EProvider {

    KAKAO("KAKAO");

    private final String name;
}
