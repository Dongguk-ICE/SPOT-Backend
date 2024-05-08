package ice.spot.dto.image.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ImageResponse {
    private String id;
    private String project;
    private String iteration;
    private String created;
    private List<PredictionResponse> predictions;
}
