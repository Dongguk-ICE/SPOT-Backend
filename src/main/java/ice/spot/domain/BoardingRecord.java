package ice.spot.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    @Column(name = "depart_at")
    private LocalDate departAt;

    @Column(name = "arrive_at")
    private LocalDate arriveAt;

    @Column(name = "depart_lat")
    private Double departLat;

    @Column(name = "depart_lon")
    private Double departLon;

    @Column(name = "arrive_lat")
    private Double arriveLat;

    @Column(name = "arrive_lon")
    private Double arriveLon;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "parking_lot_id", referencedColumnName = "id")
    private ParkingLot parkingLot;

    public BoardingRecord(String imageUrl, LocalDate departAt, LocalDate arriveAt, Double departLat, Double departLon, Double arriveLat, Double arriveLon, User user, ParkingLot parkingLot) {
        this.imageUrl = imageUrl;
        this.departAt = departAt;
        this.arriveAt = arriveAt;
        this.departLat = departLat;
        this.departLon = departLon;
        this.arriveLat = arriveLat;
        this.arriveLon = arriveLon;
        this.user = user;
        this.parkingLot = parkingLot;
    }
}
