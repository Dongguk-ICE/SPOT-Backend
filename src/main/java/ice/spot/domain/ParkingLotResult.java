package ice.spot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ParkingLotResult {

    CORRECT_PARKING_LOT(0, "올바른 주차구역에 주차되었습니다."),
    WRONG_PARKING_LOT(1, "올바르지 않은 주차구역입니다."),
    NOT_FOUND_KICKBOARD(2, "킥보드가 인식되지 않습니다."),
    ;

    private final Integer code;
    private final String message;
}
