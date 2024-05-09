package ice.spot.dto.district.response.dust;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResultResponse(
        @JsonProperty("CODE")
        String code,

        @JsonProperty("MESSAGE")
        String message
) {
}
