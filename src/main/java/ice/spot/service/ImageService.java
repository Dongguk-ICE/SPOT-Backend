package ice.spot.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import ice.spot.domain.Image;
import ice.spot.domain.ParkingLotResult;
import ice.spot.dto.image.request.ImageCheckRequest;
import ice.spot.dto.image.response.ImageResponse;
import ice.spot.dto.image.request.ImageSaveRequest;
import ice.spot.exeption.CommonException;
import ice.spot.exeption.ErrorCode;
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
    public Long saveImage(ImageSaveRequest imageSaveRequest) {
        MultipartFile multipartFile = imageSaveRequest.getImage();
        String originalName = multipartFile.getOriginalFilename();
        Image image = new Image(originalName);
        String filename = image.getStoredName();

        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getInputStream().available());

            amazonS3Client.putObject(bucketName, filename, multipartFile.getInputStream(), objectMetadata);

            String accessUrl = amazonS3Client.getUrl(bucketName, filename).toString();
            image.setAccessUrl(accessUrl);
        } catch(IOException e) {

        }

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
        String url = "/customvision/v3.0/Prediction/a7fb39e6-90a5-43a9-bee6-366150f3a1ad/classify/iterations/detecting_illegal_parking/url";

        Mono<ImageResponse> responseMono = webClient.post()
                .uri(url)
                .bodyValue(imageCheckRequest)
                .retrieve()
                .bodyToMono(ImageResponse.class);
        ImageResponse imageResponse = responseMono.block();

        log.info("태그이름");
        log.info(imageResponse.getPredictions().get(0).tagName());

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

    @Transactional
    public void deleteImage() {
        Image image = imageRepository.findById(2L).orElseThrow();
        imageRepository.delete(image);
    }
}