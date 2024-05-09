package ice.spot.service;

import ice.spot.annotation.UserId;
import ice.spot.domain.District;
import ice.spot.domain.User;
import ice.spot.dto.district.response.LocationResponse;
import ice.spot.dto.district.response.PointResponse;
import ice.spot.dto.district.response.TemperatureResponse;
import ice.spot.dto.district.response.dust.DustResponse;
import ice.spot.exception.CommonException;
import ice.spot.exception.ErrorCode;
import ice.spot.repository.DistrictRepository;
import ice.spot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DistrictService {
    private final UserRepository userRepository;
    private final DistrictRepository districtRepository;
    private final RestTemplateService restTemplateService;

    @Transactional
    public District getCloseDistrict(double lat, double lon) {
        List<District> districtLists = districtRepository.findAll();

        Double distance;
        Double minDistance = 999.0;
        Long districtId = null;
        for(District district : districtLists) {
            distance = Math.sqrt(Math.pow(district.getLat() - lat, 2) + Math.pow(district.getLon() - lon, 2));
            if(distance < minDistance) {
                minDistance = distance;
                districtId = district.getId();
            }
        }

        return districtRepository.findById(districtId)
                .orElseThrow(() -> new CommonException(ErrorCode.INTERNAL_SERVER_ERROR));
    }

    @Transactional
    public LocationResponse getLocation(double lat, double lon) {
        District district = getCloseDistrict(lat, lon);

        return LocationResponse.of(district.getDetailAddress());
    }

    @Transactional
    public TemperatureResponse getTemperatureAndHumidity(double lat, double lon) {
        District district = getCloseDistrict(lat, lon);

        return restTemplateService.getRealtimeWeather(Integer.toString(district.getGridX()), Integer.toString(district.getGridY()));
    }

    @Transactional
    public PointResponse getPointAndDust(Long userId, double lat, double lon) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        District district = getCloseDistrict(lat, lon);
        DustResponse dustResponse = restTemplateService.getRealtimeCityAir(district.getTerritory(), district.getAddress());

        PointResponse pointResponse = PointResponse.builder()
                .point(user.getPoint())
                .pm10(dustResponse.pm10())
                .pm25(dustResponse.pm25())
                .build();

        return pointResponse;
    }
}
