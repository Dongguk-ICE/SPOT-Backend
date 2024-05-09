package ice.spot.dto.district.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record PointResponse(
    @JsonProperty("point")
    Long point,

    @JsonProperty("pm10")
    String pm10,

    @JsonProperty("pm25")
    String pm25
) {
}
