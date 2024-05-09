package ice.spot.dto.district.response.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WeatherHeader(
    @JsonProperty("resultCode")
    String resultCode,

    @JsonProperty("resultMsg")
    String resultMsg
) {
}