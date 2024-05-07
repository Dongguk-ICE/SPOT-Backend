package ice.spot.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "boarding_record")
public class BoardingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "time")
    private Integer time;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "parking_lot_id")
    private ParkingLot parkingLot;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @Builder
    public BoardingRecord(Double distance, Integer time, User user, ParkingLot parkingLot, Image image) {
        this.distance = distance;
        this.time = time;
        this.user = user;
        this.parkingLot = parkingLot;
        this.image = image;
    }
}
