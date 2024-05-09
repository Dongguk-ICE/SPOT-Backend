package ice.spot.dto.district.response.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WeatherItemResponse(
        @JsonProperty("baseDate")
        String baseDate,

        @JsonProperty("baseTime")
        String baseTime,

        @JsonProperty("category")
        String category,

        @JsonProperty("nx")
        Integer nx,

        @JsonProperty("ny")
        Integer ny,

        @JsonProperty("obsrValue")
        String obsrValue
) {
}