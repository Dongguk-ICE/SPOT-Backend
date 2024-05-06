package ice.spot.security.info;

import ice.spot.dto.type.ERole;

public record JwtUserInfo(Long userId, ERole role) {
}
