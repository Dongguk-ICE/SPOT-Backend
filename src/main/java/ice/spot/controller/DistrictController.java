package ice.spot.controller;

import ice.spot.annotation.UserId;
import ice.spot.dto.global.ResponseDto;
import ice.spot.service.DistrictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/district")
public class DistrictController {
    private final DistrictService districtService;

    @GetMapping("/location")
    public ResponseDto<?> getLocation(@RequestParam Double lat, @RequestParam Double lon) {
        return ResponseDto.ok(districtService.getLocation(lat, lon));
    }

    @GetMapping("/temperature")
    public void getTemperatureAndHumidity(@RequestParam Double lat, @RequestParam Double lon) {
        districtService.getTemperatureAndHumidity(lat, lon);
    }

    @GetMapping("/point")
    public ResponseDto<?> getPointAndDust(@UserId Long userId, @RequestParam Double lat, @RequestParam Double lon) {
        return ResponseDto.ok(districtService.getPointAndDust(userId, lat, lon));
    }
}
