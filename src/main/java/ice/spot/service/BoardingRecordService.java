package ice.spot.service;

import ice.spot.domain.BoardingRecord;
import ice.spot.domain.Image;
import ice.spot.domain.ParkingLot;
import ice.spot.domain.User;
import ice.spot.dto.boardingrecord.request.BoardingRecordRequest;
import ice.spot.dto.boardingrecord.response.BoardingRecordListResponse;
import ice.spot.dto.boardingrecord.response.BoardingRecordResponse;
import ice.spot.dto.user.response.PersonResponse;
import ice.spot.exception.CommonException;
import ice.spot.exception.ErrorCode;
import ice.spot.repository.BoardingRecordRepository;
import ice.spot.repository.ImageRepository;
import ice.spot.repository.ParkingLotRepository;
import ice.spot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardingRecordService {
    private final BoardingRecordRepository boardingRecordRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final WebClient webClient;
    private final AuthService authService;

    @Transactional
    public Boolean saveBoardingRecord(Long userId, Long imageId, BoardingRecordRequest boardingRecordRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        Image image = imageRepository.findById(imageId)
                .orElseThrow(IllegalArgumentException::new);
        ParkingLot parkingLot = parkingLotRepository.findById(1L)
                .orElseThrow(IllegalArgumentException::new);

        boardingRecordRepository.save(BoardingRecord.builder()
                        .distance(boardingRecordRequest.distance())
                        .time(boardingRecordRequest.time())
                        .point(100)
                        .user(user)
                        .image(image)
                        .parkingLot(parkingLot)
                .build());

        user.plusPoint();

        return Boolean.TRUE;
    }

    @Transactional(readOnly = true)
    public BoardingRecordListResponse boardingRecordList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        BoardingRecordListResponse boardingRecordListResponse = BoardingRecordListResponse.builder()
                .personResponse(PersonResponse.builder()
                        .nickname(user.getNickname())
                        .recordCount(user.getBoardingRecords().size())
                        .point(user.getPoint())
                        .build())
                .boardingRecordResponseList(user.getBoardingRecords().stream()
                        .map(boardingRecord ->
                                BoardingRecordResponse.builder()
                                .createdAt(boardingRecord.getCreatedAt().toString())
                                .image(boardingRecord.getImage().getImageUrl())
                                .distance(boardingRecord.getDistance())
                                .time(boardingRecord.getTime())
                                .point(boardingRecord.getPoint())
                                .build())
                        .toList())
                .build();
        return boardingRecordListResponse;
    }
}
