package ice.spot.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "origin_name")
    private String originName;

    @Column(name = "stored_name")
    private String storedName;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToOne(mappedBy = "image")
    private BoardingRecord boardingRecord;

    public Image(String originName) {
        this.originName = originName;
        this.storedName = getFileName(originName);
        this.imageUrl = "";
    }

    public void setAccessUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String extractExtension(String originName) {
        int index = originName.lastIndexOf('.');

        return originName.substring(index, originName.length());
    }

    public String getFileName(String originName) {
        return UUID.randomUUID() + "." + extractExtension(originName);
    }

}
