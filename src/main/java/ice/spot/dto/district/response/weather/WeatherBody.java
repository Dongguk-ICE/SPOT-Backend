package ice.spot.dto.district.response.weather;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WeatherBody(
        @JsonProperty("dataType")
        String dataType,

        @JsonProperty("items")
        WeatherItemListResponse weatherItemListResponse,

        @JsonProperty("pageNo")
        Integer pageNo,

        @JsonProperty("numOfRows")
        Integer numOfRows,

        @JsonProperty("totalCount")
        Integer totalCount
) {
}