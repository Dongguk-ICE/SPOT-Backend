package ice.spot.dto.district.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LocationResponse(
        @JsonProperty("location")
        String location
) {
        public LocationResponse(String location) {
                this.location = location;
        }

        public static LocationResponse of(String location) {
                return new LocationResponse(location);
        }
}
