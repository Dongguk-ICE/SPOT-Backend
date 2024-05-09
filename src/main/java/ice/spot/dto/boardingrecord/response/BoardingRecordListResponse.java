package ice.spot.dto.boardingrecord.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ice.spot.dto.user.response.PersonResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record BoardingRecordListResponse(
        @JsonProperty("person")
        PersonResponse personResponse,

        @JsonProperty("records")
        List<BoardingRecordResponse> boardingRecordResponseList
) {
}
