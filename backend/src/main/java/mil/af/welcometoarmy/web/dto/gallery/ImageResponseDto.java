package mil.af.welcometoarmy.web.dto.gallery;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@ToString
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponseDto {
    private Long id;
    private String fileName;
    private String fileSize;
}
