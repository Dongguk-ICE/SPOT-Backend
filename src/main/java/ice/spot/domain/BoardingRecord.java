package ice.spot.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "distance")
    private Double distance;

    @Column(name = "time")
    private Integer time;

    @Column(name = "point")
    private Integer point;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "parking_lot_id")
    private ParkingLot parkingLot;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @Builder
    public BoardingRecord(Double distance, Integer time, Integer point, User user, ParkingLot parkingLot, Image image) {
        this.distance = distance;
        this.time = time;
        this.point = point;
        this.createdAt = LocalDateTime.now();
        this.user = user;
        this.parkingLot = parkingLot;
        this.image = image;
    }
}
