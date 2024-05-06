package ice.spot.dto.parkingLot.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ParkingLotResponse(
        @JsonProperty("address")
        String address,

        @JsonProperty("detail_address")
        String detailAddress,

        @JsonProperty("lat")
        Double lat,

        @JsonProperty("lon")
        Double lon,

        @JsonIgnore
        Double distance
) {
        public static ParkingLotResponse of(String address, String detailAddress, Double lat, Double lon, Double distance) {
                return new ParkingLotResponse(address, detailAddress, lat, lon, distance);
        }
}
