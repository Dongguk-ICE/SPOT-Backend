package ice.spot.controller;

import ice.spot.dto.boardingrecord.request.BoardingRecordRequest;
import ice.spot.dto.global.ResponseDto;
import ice.spot.dto.image.request.ImageSaveRequest;
import ice.spot.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardingRecordController {

    private final ImageService imageService;

    @PostMapping("/boarding-record")
    public ResponseDto<?> saveBoardingRecord(
            @ModelAttribute ImageSaveRequest imageSaveRequest, @RequestBody BoardingRecordRequest boardingRecordRequest
    ) {
        Long imageId = imageService.saveImage(imageSaveRequest);

        return null;
    }
}
