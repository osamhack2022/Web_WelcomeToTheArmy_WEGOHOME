package mil.af.welcometoarmy.web.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import mil.af.welcometoarmy.domain.Image;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo {

    private String origFileName;

    private String fileName;

    @NotBlank
    private String filePath;

    @NotBlank
    private String fileExtension;

    @NotBlank
    private String fileSize;

    public Image toImage() {
        return Image.builder()
                .fileName(fileName)
                .filePath(filePath)
                .fileSize(fileSize)
                .build();
    }
}
