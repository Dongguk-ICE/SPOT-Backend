package ice.spot.dto.district.response.dust;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record RealtimeCityAirResponse(
    @JsonProperty("list_total_count")
    Integer count,

    @JsonProperty("RESULT")
    ResultResponse resultResponse,

    @JsonProperty("row")
    List<RowResponse> rowResponses
) {
}