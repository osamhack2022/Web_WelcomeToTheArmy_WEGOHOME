package mil.af.welcometoarmy.web.dto.schedule;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import mil.af.welcometoarmy.domain.Schedule;
import mil.af.welcometoarmy.domain.enums.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Builder
@Getter
@ToString
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleUpdateDto {

    @NotBlank(message = "일정명을 입력해주세요.")
    @ApiModelProperty(value = "일정명", required = true)
    private String name;

    @NotNull(message = "기수를 입력해주세요.")
    @Positive(message = "기수는 양수만 입력해주세요.")
    @ApiModelProperty(value = "기수", required = true)
    private int generation;

    @NotBlank(message = "공개범위를 입력해주세요.")
    @ApiModelProperty(value = "공개범위", required = true, example = "111")
    private String belong;

    @NotBlank(message = "시작일을 입력해주세요.")
    @ApiModelProperty(value = "시작일", required = true, example = "2000-01-01")
    private String startDate;

    @NotBlank(message = "종료일을 입력해주세요.")
    @ApiModelProperty(value = "종료일", required = true, example = "2000-01-01")
    private String endDate;

    @NotBlank(message = "시작시간을 입력해주세요.")
    @ApiModelProperty(value = "시작시간", required = true, example = "08:30")
    private String startTime;

    @NotBlank(message = "종료시간을 입력해주세요.")
    @ApiModelProperty(value = "종료시간", required = true, example = "16:00")
    private String endTime;

    public Schedule toEntity() {
        return Schedule.builder()
                .name(name)
                .generation(generation)
                .belong(belong)
                .startDate(LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE))
                .endDate(LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE))
                .startTime(LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm")))
                .endTime(LocalTime.parse(endTime, DateTimeFormatter.ofPattern("HH:mm")))
                .build();
    }
}
