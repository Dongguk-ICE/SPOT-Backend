package ice.spot.dto.district.response.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WeatherResultResponse(
        @JsonProperty("response")
        RealtimeWeatherResponse realtimeWeatherResponse
) {
}
