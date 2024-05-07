package ice.spot.dto.user.response;

import lombok.Builder;

@Builder
public record PersonResponse(
        String nickname,
        Integer recordCount,
        Long point
) {
}
