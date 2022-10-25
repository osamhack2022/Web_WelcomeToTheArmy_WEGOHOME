package mil.af.welcometoarmy.domain;

import lombok.*;
import mil.af.welcometoarmy.domain.enums.Range;
import mil.af.welcometoarmy.web.dto.schedule.ScheduleResponseDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
public class Schedule extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @Positive
    private int generation;

    @NotNull
    private String belong;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    public void update(Schedule schedule) {
        name = schedule.getName();
        generation = schedule.getGeneration();
        belong = schedule.getBelong();
        startDate = schedule.getStartDate();
        endDate = schedule.getEndDate();
        startTime = schedule.getStartTime();
        endTime = schedule.getEndTime();
    }

    public ScheduleResponseDto toDto() {
        return ScheduleResponseDto.builder()
                .id(id)
                .name(name)
                .generation(generation)
                .belong(belong)
                .startDate(startDate.format(DateTimeFormatter.ISO_DATE))
                .endDate(endDate.format(DateTimeFormatter.ISO_DATE))
                .startTime(startTime.format(DateTimeFormatter.ofPattern("HH:mm")))
                .endTime(endTime.format(DateTimeFormatter.ofPattern("HH:mm")))
                .build();
    }

    public void validateDateTime() {
        if (startDate.isAfter(endDate))
            throw new IllegalArgumentException("종료일보다 시작일이 빨라야 합니다");
        if (startTime.isAfter(endTime))
            throw new IllegalArgumentException("종료시각보다 시작시각이 빨라야 합니다");
    }
}
