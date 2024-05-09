package ice.spot.dto.district.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TemperatureResponse (
    @JsonProperty("temperature")
    Double temperature,

    @JsonProperty("humidity")
    Double humidity
) {

}
