package ice.spot.service;

import ice.spot.dto.parkingLot.response.ParkingLotResponse;
import ice.spot.repository.ParkingLotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParkingLotService {
    private final ParkingLotRepository parkingLotRepository;

    @Transactional(readOnly = true)
    public List<ParkingLotResponse> parkingLotList(Double lat, Double lon) {

        List<ParkingLotResponse> parkingLotResponseList = new ArrayList<>(parkingLotRepository.findAll().stream()
                .map(parkingLot -> ParkingLotResponse.builder()
                        .address(parkingLot.getAddress())
                        .detailAddress(parkingLot.getDetailAddress())
                        .lat(parkingLot.getLat())
                        .lon(parkingLot.getLon())
                        .distance(Math.sqrt(Math.pow(parkingLot.getLat() - lat, 2) + Math.pow(parkingLot.getLon() - lon, 2)))
                        .build())
                .toList());
        Collections.sort(parkingLotResponseList, new Comparator<ParkingLotResponse>() {
            @Override
            public int compare(ParkingLotResponse o1, ParkingLotResponse o2) {
                if(o1.distance() > o2.distance()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        return parkingLotResponseList.subList(0, 5);
    }
}
