package mil.af.welcometoarmy.web.dto.gallery;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder
@Getter
@ToString
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class GalleryResponseDto {
    private Long id;
    private String title;
    private String content;
    private int generation;
    private String belong;
    private List<ImageResponseDto> imgList;
}
