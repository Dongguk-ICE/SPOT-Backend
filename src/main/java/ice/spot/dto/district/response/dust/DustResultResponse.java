package ice.spot.dto.district.response.dust;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DustResultResponse(
        @JsonProperty("RealtimeCityAir")
        RealtimeCityAirResponse realtimeCityAirResponse
) {
}
