package ice.spot.security.info.factory;

import ice.spot.dto.type.EProvider;
import ice.spot.security.info.KakaoOauth2UserInfo;

import java.util.Map;

public class Oauth2UserInfoFactory {

    public static Oauth2UserInfo getOauth2UserInfo(
            EProvider provider,
            Map<String, Object> attributes
    ) {
        Oauth2UserInfo ret;
        switch (provider) {
            case KAKAO -> ret = new KakaoOauth2UserInfo(attributes);
            default -> throw new IllegalAccessError("잘못된 제공자입니다.");
        }

        return ret;
    }
}
