package ice.spot.dto.district.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record TemperatureResponse (
    @JsonProperty("temperature")
    Double temperature,

    @JsonProperty("humidity")
    Double humidity
) {

}
