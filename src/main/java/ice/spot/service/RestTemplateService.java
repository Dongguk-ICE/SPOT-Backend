package ice.spot.service;

import ice.spot.dto.district.response.dust.DustResponse;
import ice.spot.dto.district.response.dust.DustResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class RestTemplateService {
    private final RestTemplate restTemplate;

    public RestTemplateService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Transactional
    public DustResponse getRealtimeCityAir(String territory, String district) {
        String pathString = String.format("/77664c7658616c7337367075735970/json/RealtimeCityAir/1/10/%s/%s", territory, district);

        URI uri = UriComponentsBuilder
                .fromUriString("http://openAPI.seoul.go.kr:8088")
                .path(pathString)
                .encode()
                .build()
                .toUri();
        DustResultResponse dustResultResponse = restTemplate.getForObject(uri, DustResultResponse.class);

        DustResponse dustResponse = DustResponse.builder()
                .pm10(dustResultResponse.realtimeCityAirResponse().rowResponses().get(0).PM10())
                .pm25(dustResultResponse.realtimeCityAirResponse().rowResponses().get(0).PM25())
                .build();

        return dustResponse;
    }


}
