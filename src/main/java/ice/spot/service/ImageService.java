package ice.spot.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import ice.spot.domain.Image;
import ice.spot.domain.type.ParkingLotResult;
import ice.spot.dto.image.request.ImageCheckRequest;
import ice.spot.dto.image.response.ImageResponse;
import ice.spot.exception.CommonException;
import ice.spot.exception.ErrorCode;
import ice.spot.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final AmazonS3Client amazonS3Client;
    private final ImageRepository imageRepository;
    private final WebClient webClient;

    @Transactional
    public Long saveImage(MultipartFile multipartFile) throws IOException {

        String originalName = multipartFile.getOriginalFilename();
        Image image = new Image(originalName);
        String filename = image.getStoredName();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getInputStream().available());

        amazonS3Client.putObject(bucketName, filename, multipartFile.getInputStream(), objectMetadata);

        String accessUrl = amazonS3Client.getUrl(bucketName, filename).toString();
        image.setAccessUrl(accessUrl);

        ParkingLotResult parkingLotResult = checkImage(new ImageCheckRequest(image.getImageUrl()));
        if(parkingLotResult == ParkingLotResult.CORRECT_PARKING_LOT) {
            imageRepository.save(image);
        } else if (parkingLotResult == ParkingLotResult.WRONG_PARKING_LOT) {
            deleteImage(image.getStoredName());
            throw new CommonException(ErrorCode.INVALID_PARKING_LOT);
        } else if (parkingLotResult == ParkingLotResult.NOT_FOUND_KICKBOARD){
            deleteImage(image.getStoredName());
            throw new CommonException(ErrorCode.INVALID_KICKBOARD_REQUEST);
        }

        return image.getId();
    }

    @Transactional
    public void deleteImage(String fileName) {
        try {
            amazonS3Client.deleteObject(bucketName, fileName);
        } catch (SdkClientException e) {

        }
    }

    @Transactional
    public ParkingLotResult checkImage(ImageCheckRequest imageCheckRequest) {
        String url = "/customvision/v3.0/Prediction/32861a23-8e48-4fbc-9fbf-42fe3954bd67/classify/iterations/Iteration4/url";

        Mono<ImageResponse> responseMono = webClient.post()
                .uri(url)
                .bodyValue(imageCheckRequest)
                .retrieve()
                .bodyToMono(ImageResponse.class);
        ImageResponse imageResponse = responseMono.block();

        String hisgestProbabilityTag = imageResponse.getPredictions().get(0).tagName();
        String secondProbabilityTag = imageResponse.getPredictions().get(1).tagName();

        if(hisgestProbabilityTag.equals("kickboard") && secondProbabilityTag.equals("parkinglot")) {
            return ParkingLotResult.CORRECT_PARKING_LOT;
        } else if (hisgestProbabilityTag.equals("kickboard") && !secondProbabilityTag.equals("parkinglot")) {
            return ParkingLotResult.WRONG_PARKING_LOT;
        } else {
            return ParkingLotResult.NOT_FOUND_KICKBOARD;
        }
    }
}