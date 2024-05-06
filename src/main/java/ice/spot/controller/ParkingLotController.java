package ice.spot.controller;

import ice.spot.dto.global.ResponseDto;
import ice.spot.service.ParkingLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ParkingLotController {
    private final ParkingLotService parkingLotService;

    @GetMapping("/kickboard")
    public ResponseDto<?> parkingLotList(@RequestParam Double lat, @RequestParam Double lon) {
        return ResponseDto.ok(parkingLotService.parkingLotList(lat, lon));
    }
}