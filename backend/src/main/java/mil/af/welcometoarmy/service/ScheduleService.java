package mil.af.welcometoarmy.service;

import lombok.RequiredArgsConstructor;
import mil.af.welcometoarmy.domain.Schedule;
import mil.af.welcometoarmy.domain.Survey;
import mil.af.welcometoarmy.domain.enums.Authority;
import mil.af.welcometoarmy.exception.EntityNotFoundException;
import mil.af.welcometoarmy.exception.ExceptionMessage;
import mil.af.welcometoarmy.repository.ScheduleRepository;
import mil.af.welcometoarmy.util.AuthChecker;
import mil.af.welcometoarmy.web.dto.schedule.ScheduleCreateDto;
import mil.af.welcometoarmy.web.dto.schedule.ScheduleResponseDto;
import mil.af.welcometoarmy.web.dto.schedule.ScheduleUpdateDto;
import mil.af.welcometoarmy.web.dto.survey.SurveyResponseDto;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final AuthChecker authChecker;

    @Transactional
    public void save(ScheduleCreateDto scheduleCreateDto) {
        Schedule schedule = scheduleCreateDto.toEntity();
        schedule.validateDateTime();

        scheduleRepository.save(schedule);
    }

    @Transactional
    public void update(Long id, ScheduleUpdateDto scheduleUpdateDto) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new
                EntityNotFoundException(ExceptionMessage.NONE_SCHEDULE_MESSAGE));

        schedule.update(scheduleUpdateDto.toEntity());
        schedule.validateDateTime();
    }

    public ScheduleResponseDto getOne(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new
                EntityNotFoundException(ExceptionMessage.NONE_SCHEDULE_MESSAGE)).toDto();
    }

    public List<ScheduleResponseDto> getAll(String date, UserDetails userDetails) {
        String belong = authChecker.getBelong(userDetails);
        int generation = authChecker.getGeneration(userDetails);

        String battalion = belong.substring(0, 1);
        String company = belong.substring(1, 2);
        String platoon = belong.substring(2, 3);

        List<Schedule> schedules;
        if (generation == 0) schedules = scheduleRepository.findAll(Sort.by(Sort.Direction.DESC, "startDate"));
        else schedules = scheduleRepository.findAllByGeneration(generation, Sort.by(Sort.Direction.DESC, "startDate"));
        if (!battalion.equals("0")) {
            schedules = schedules.stream().filter(s -> s.getBelong().substring(0, 1).equals(battalion)).collect(Collectors.toList());

            if (!company.equals("0")) {
                schedules = schedules.stream().filter(s -> s.getBelong().substring(1, 2).equals(company) ||
                        s.getBelong().charAt(1) == '0').collect(Collectors.toList());

                if (!platoon.equals("0")) {
                    schedules = schedules.stream().filter(s -> s.getBelong().substring(2, 3).equals(platoon) ||
                            s.getBelong().charAt(2) == '0').collect(Collectors.toList());
                }
            }
        }

        if (date == null) return getDtoList(schedules);
        else {
            LocalDate targetDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            return getDtoList(schedules.stream().filter(s -> !s.getStartDate().isAfter(targetDate) && !s.getEndDate().isBefore(targetDate)).collect(Collectors.toList()));
        }
    }

    @Transactional
    public void delete(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new
                EntityNotFoundException(ExceptionMessage.NONE_SCHEDULE_MESSAGE));

        scheduleRepository.delete(schedule);
    }

    private List<ScheduleResponseDto> getDtoList(List<Schedule> schedules) {
        List<ScheduleResponseDto> list = new ArrayList<>();

        for (Schedule schedule : schedules) {
            list.add(schedule.toDto());
        }

        return list;
    }
}
