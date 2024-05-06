package ice.spot.controller;

import ice.spot.annotation.UserId;
import ice.spot.dto.boardingrecord.request.BoardingRecordRequest;
import ice.spot.dto.global.ResponseDto;
import ice.spot.service.BoardingRecordService;
import ice.spot.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardingRecordController {

    private final ImageService imageService;
    private final BoardingRecordService boardingRecordService;

    @PostMapping("/boarding-record")
    public ResponseDto<?> saveBoardingRecord(
            @UserId Long userId,
            @RequestPart(value = "image", required = false) MultipartFile multipartFile,
            @RequestPart(value = "dto") BoardingRecordRequest boardingRecordRequest
    ) {
        Long imageId = imageService.saveImage(multipartFile);

        return ResponseDto.created(boardingRecordService
                .saveBoardingRecord(userId, imageId, boardingRecordRequest));
    }
}
