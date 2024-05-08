package ice.spot.dto.parkingLot.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record ParkingLotResponseList(
        @JsonProperty("parkingLotList")
        List<ParkingLotResponse> parkingLotResponseList
) {
    public static ParkingLotResponseList of(final List<ParkingLotResponse> parkingLotResponse) {
        return ParkingLotResponseList.builder()
                .parkingLotResponseList(parkingLotResponse)
                .build();
    }
}
