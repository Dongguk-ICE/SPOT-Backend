package ice.spot.dto.district.response.dust;

import lombok.Builder;

@Builder
public record DustResponse(
        Double pm10,

        Double pm25
) {
}
