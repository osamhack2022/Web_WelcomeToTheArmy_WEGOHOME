package mil.af.welcometoarmy.domain;

import lombok.*;
import mil.af.welcometoarmy.web.dto.gallery.ImageResponseDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class Image extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String fileName;

    @NotNull
    private String filePath;

    @NotNull
    private String fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GALLERY_ID", nullable = false)
    private Gallery gallery;

    public void setGallery(Gallery gallery) {
        if (this.gallery != null)
            this.gallery.getImgFiles().remove(this);
        if (gallery == null) this.gallery = null;
        else {
            this.gallery = gallery;
            if (gallery.getImgFiles() == null) {
                List<Image> list = new ArrayList<>();
                list.add(this);
                gallery.setImgFiles(list);
            } else gallery.getImgFiles().add(this);
        }
    }

    public void clear() {
        if (gallery != null) {
            gallery.getImgFiles().remove(this);
        }
    }

    public ImageResponseDto toDto() {
        return ImageResponseDto.builder()
                .id(id)
                .fileName(fileName)
                .fileSize(fileSize)
                .build();
    }
}
