package ice.spot.service;

import ice.spot.dto.district.response.TemperatureResponse;
import ice.spot.dto.district.response.dust.DustResponse;
import ice.spot.dto.district.response.dust.DustResultResponse;
import ice.spot.dto.district.response.weather.WeatherItemResponse;
import ice.spot.dto.district.response.weather.WeatherResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
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

        DustResponse dustResponse = DustResponse.builder()
                .pm10(dustResultResponse.realtimeCityAirResponse().rowResponses().get(0).PM10())
                .pm25(dustResultResponse.realtimeCityAirResponse().rowResponses().get(0).PM25())
                .build();

        return dustResponse;
    }


    @Transactional
    public TemperatureResponse getRealtimeWeather(String nx, String ny) {

        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(-20);
        LocalDateTime parsedLocalDateTime = LocalDateTime.parse(localDateTime.toString());
        String yyyy = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy"));
        String MM = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("MM"));
        String DD = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("dd"));
        String HH = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("HH")) + "00";

        URI uri = UriComponentsBuilder
                .fromUriString("http://apis.data.go.kr")
                .path("/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst")
                .queryParam("ServiceKey", "zNmiod%2F0aq5%2F06rrd8wHCmHhake9Vvj%2BY%2BX%2BsnrK7AU9uvTvg2qtAfZrjm9Gu1navvJG3%2B9sSN6MfriPi0rlLA%3D%3D")
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

        WeatherResultResponse weatherResultResponse = restTemplate.getForObject(uri, WeatherResultResponse.class);
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
    }
}
