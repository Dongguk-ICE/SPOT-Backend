package ice.spot.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "boarding_record")
public class BoardingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "time")
    private Integer time;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "parking_lot_id", referencedColumnName = "id")
    private ParkingLot parkingLot;

    public BoardingRecord(String imageUrl, Double distance, Integer time, User user, ParkingLot parkingLot) {
        this.imageUrl = imageUrl;
        this.distance = distance;
        this.time = time;
        this.user = user;
        this.parkingLot = parkingLot;
    }
}
