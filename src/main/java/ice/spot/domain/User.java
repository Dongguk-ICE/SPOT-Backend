package ice.spot.domain;

import ice.spot.dto.type.EProvider;
import ice.spot.dto.type.ERole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
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

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private ERole role;

    @Column(name = "provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private EProvider provider;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BoardingRecord> boardingRecords = new ArrayList<>();

    @Column(name = "refresh_token")
    private String refreshToken;

    @Builder
    public User(String serialId, String password, String nickname, ERole role, EProvider provider, Long point) {
        this.serialId = serialId;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.provider = provider;
        this.point = point;
        this.createdAt = LocalDate.now();
    }

    public void register(String nickname) {
        this.nickname = nickname;
        this.createdAt = LocalDate.now();
        this.role = ERole.USER;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
