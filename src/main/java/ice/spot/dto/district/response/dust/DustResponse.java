package ice.spot.dto.district.response.dust;

import lombok.Builder;

@Builder
public record DustResponse(
        String pm10,

        String pm25
) {
}
