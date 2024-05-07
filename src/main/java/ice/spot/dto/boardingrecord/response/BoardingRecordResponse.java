package ice.spot.dto.boardingrecord.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record BoardingRecordResponse(
        @JsonProperty("created_at")
        String createdAt,

        @JsonProperty("image")
        String image,

        @JsonProperty("distance")
        Double distance,

        @JsonProperty("time")
        Integer time,

        @JsonProperty("point")
        Integer point
) {
    public static BoardingRecordResponse of(String createdAt, String image, Double distance, Integer time, Integer point) {
        return new BoardingRecordResponse(createdAt, image, distance, time, point);
    }
}
