package ice.spot.service;

import ice.spot.dto.boardingrecord.response.BoardingRecordResponse;
import ice.spot.repository.BoardingRecordRepository;
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
    private final WebClient webClient;

    @Transactional
    public Boolean saveBoardingRecord() {
        return null;
    }

    @Transactional(readOnly = true)
    public List<BoardingRecordResponse> boardingRecordList(Long userId) {
        return null;
    }
}
