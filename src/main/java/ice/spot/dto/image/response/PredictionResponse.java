package ice.spot.dto.image.response;

import jakarta.persistence.Column;

public record PredictionResponse(
        Double probability,

        @Column(name = "tag_id")
        String tagId,

        @Column(name = "tag_name")
        String tagName
) {
}
