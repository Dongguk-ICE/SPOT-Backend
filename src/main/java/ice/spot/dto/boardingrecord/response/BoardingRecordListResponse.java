package ice.spot.dto.boardingrecord.response;

import ice.spot.dto.user.response.PersonResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record BoardingRecordListResponse(
        PersonResponse personResponse,

        List<BoardingRecordResponse> boardingRecordResponseList
) {
}
