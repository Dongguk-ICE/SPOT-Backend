package ice.spot.dto.district.response.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record WeatherItemListResponse(
        @JsonProperty("item")
        List<WeatherItemResponse> weatherItemResponses
) {
}
