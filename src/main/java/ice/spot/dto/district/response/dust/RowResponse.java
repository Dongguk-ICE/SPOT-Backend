package ice.spot.dto.district.response.dust;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RowResponse(
        @JsonProperty("MSRDT")
        String MSRDT,

        @JsonProperty("MSRRGN_NM")
        String MSRRGN_NM,

        @JsonProperty("MSRSTE_NM")
        String MSRSTE_NM,

        @JsonProperty("PM10")
        Double PM10,

        @JsonProperty("PM25")
        Double PM25,

        @JsonProperty("O3")
        Double O3,

        @JsonProperty("NO2")
        Double NO2,

        @JsonProperty("CO")
        Double CO,

        @JsonProperty("SO2")
        Double SO2,

        @JsonProperty("IDEX_NM")
        String IDEX_NM,

        @JsonProperty("IDEX_MVL")
        Double IDEX_MVL,

        @JsonProperty("ARPLT_MAIN")
        String ARPLT_MAIN
) {
}