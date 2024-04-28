package ice.spot.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "serial_id")
    private String serialId;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "point")
    private Long point;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BoardingRecord> boardingRecords = new ArrayList<>();

    public User(String serialId, String password, String nickname, Long point) {
        this.serialId = serialId;
        this.password = password;
        this.nickname = nickname;
        this.point = point;
        this.createdAt = LocalDate.now();
    }
}
