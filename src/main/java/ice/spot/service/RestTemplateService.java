package ice.spot.service;

import ice.spot.dto.district.response.TemperatureResponse;
import ice.spot.dto.district.response.dust.DustResponse;
import ice.spot.dto.district.response.dust.DustResultResponse;
import ice.spot.dto.district.response.weather.WeatherItemResponse;
import ice.spot.dto.district.response.weather.WeatherResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

        Double pm10 = dustResultResponse.realtimeCityAirResponse().rowResponses().get(0).PM10();
        String pm10grade = null;
        Double pm25 = dustResultResponse.realtimeCityAirResponse().rowResponses().get(0).PM25();
        String pm25grade = null;

        if(pm10 > 0 && pm10 <= 30) {
            pm10grade = "좋음";
        } else if (pm10 > 30 && pm10 <= 80) {
            pm10grade = "보통";
        } else if (pm10 > 80 && pm10 <= 150) {
            pm10grade = "나쁨";
        } else if (pm10 > 150) {
            pm10grade = "매우 나쁨";
        }

        if(pm25 > 0 && pm25 <= 15) {
            pm25grade = "좋음";
        } else if (pm25 > 15 && pm25 <= 50) {
            pm25grade = "보통";
        } else if (pm25 > 50 && pm25 <= 100) {
            pm25grade = "나쁨";
        } else if (pm25 > 100) {
            pm25grade = "매우 나쁨";
        }

        DustResponse dustResponse = DustResponse.builder()
                .pm10(pm10grade)
                .pm25(pm25grade)
                .build();

        return dustResponse;
    }


    @Transactional
    public TemperatureResponse getRealtimeWeather(String nx, String ny) {

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<?> entity = new HttpEntity<>(headers);

        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(-20);
        LocalDateTime parsedLocalDateTime = LocalDateTime.parse(localDateTime.toString());
        String yyyy = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy"));
        String MM = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("MM"));
        String DD = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("dd"));
        String HH = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("HH")) + "00";

        try {
            URI uri = UriComponentsBuilder
                    .fromUriString("http://apis.data.go.kr")
                    .path("/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst")
                    .queryParam("ServiceKey", "zNmiod/0aq5/06rrd8wHCmHhake9Vvj+Y+X+snrK7AU9uvTvg2qtAfZrjm9Gu1navvJG3+9sSN6MfriPi0rlLA==")
                    .queryParam("pageNo", "1")
                    .queryParam("numOfRows", "10")
                    .queryParam("dataType", "JSON")
                    .queryParam("base_date", yyyy+MM+DD)
                    .queryParam("base_time", HH)
                    .queryParam("nx", nx)
                    .queryParam("ny", ny)
                    .encode()
                    .build()
                    .toUri();
            WeatherResultResponse weatherResultResponse = restTemplate.exchange(uri, HttpMethod.GET, entity, WeatherResultResponse.class).getBody();

            List<WeatherItemResponse> weatherItemResponses = weatherResultResponse.realtimeWeatherResponse()
                    .weatherBody()
                    .weatherItemListResponse()
                    .weatherItemResponses();

            String T1H = null;
            String REH = null;
            for(WeatherItemResponse weatherItemResponse : weatherItemResponses) {
                if(weatherItemResponse.category().equals("T1H")) T1H = weatherItemResponse.obsrValue();
                if(weatherItemResponse.category().equals("REH")) REH = weatherItemResponse.obsrValue();
            }

            TemperatureResponse temperatureResponse = TemperatureResponse.builder()
                    .temperature(Double.parseDouble(T1H))
                    .humidity(Double.parseDouble(REH))
                    .build();

            return temperatureResponse;
        } catch (Exception e) {
            return TemperatureResponse.builder()
                    .temperature(14.0)
                    .humidity(79.0)
                    .build();
        }
    }
}
