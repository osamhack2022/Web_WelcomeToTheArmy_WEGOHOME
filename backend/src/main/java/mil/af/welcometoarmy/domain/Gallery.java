package mil.af.welcometoarmy.domain;

import lombok.*;
import mil.af.welcometoarmy.web.dto.gallery.GalleryResponseDto;
import mil.af.welcometoarmy.web.dto.gallery.ImageResponseDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class Gallery extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @Positive
    private int generation;

    @NotNull
    private String belong;

    @OneToMany(
            mappedBy = "gallery",
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<Image> imgFiles = new ArrayList<>();

    public void setImgFiles(List<Image> imgFiles) {
        this.imgFiles = imgFiles;
    }

    public void update(Gallery gallery) {
        title = gallery.getTitle();
        content = gallery.getContent();
        generation = gallery.getGeneration();
        belong = gallery.getBelong();
    }

    public GalleryResponseDto toDto() {
        List<ImageResponseDto> imgList = new ArrayList<>();
        imgFiles.stream().forEach(i -> imgList.add(i.toDto()));
        return GalleryResponseDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .generation(generation)
                .belong(belong)
                .imgList(imgList)
                .build();
    }
}
