package ice.spot.dto.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ERole {

    GUEST("GUEST", "ROLE_GUEST"),
    USER("USER", "ROLE_USER"),
    ADMIN("ADMIN", "ROLE_ADMIN");

    private final String role;
    private final String securityRole;
}
