package ice.spot.dto.district.response.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RealtimeWeatherResponse(
    @JsonProperty("header")
    WeatherHeader weatherHeader,

    @JsonProperty("body")
    WeatherBody weatherBody
) {
}

