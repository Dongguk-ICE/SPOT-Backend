package ice.spot.dto.boardingrecord.request;

import lombok.Builder;

@Builder
public record BoardingRecordRequest(
        Double distance,
        Integer time
) {
}
