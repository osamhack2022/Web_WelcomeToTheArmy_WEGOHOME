package mil.af.welcometoarmy.service;

import lombok.RequiredArgsConstructor;
import mil.af.welcometoarmy.domain.Manager;
import mil.af.welcometoarmy.domain.Schedule;
import mil.af.welcometoarmy.domain.Soldier;
import mil.af.welcometoarmy.domain.Survey;
import mil.af.welcometoarmy.exception.EntityNotFoundException;
import mil.af.welcometoarmy.exception.ExceptionMessage;
import mil.af.welcometoarmy.repository.ManagerRepository;
import mil.af.welcometoarmy.repository.SoldierRepository;
import mil.af.welcometoarmy.repository.SurveyRepository;
import mil.af.welcometoarmy.util.AuthChecker;
import mil.af.welcometoarmy.web.dto.schedule.ScheduleResponseDto;
import mil.af.welcometoarmy.web.dto.soldier.SoldierResponseDto;
import mil.af.welcometoarmy.web.dto.survey.SurveyCreateDto;
import mil.af.welcometoarmy.web.dto.survey.SurveyResponseDto;
import mil.af.welcometoarmy.web.dto.survey.SurveyUpdateDto;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;

    private final SoldierRepository soldierRepository;

    private final ManagerRepository managerRepository;

    private final AuthChecker authChecker;

    @Transactional
    public void save(SurveyCreateDto surveyCreateDto, UserDetails userDetails) {
        Survey survey = surveyCreateDto.toEntity();
        survey.setTotal(calculateTotal(survey.getBelong(), survey.getGeneration()));
        if (survey.getStartDate().isAfter(survey.getEndDate()))
            throw new IllegalArgumentException("조사 마감일보다 조사 시작일이 빨라야 합니다");
        Manager manager = managerRepository.findByManagerId(userDetails.getUsername()).orElseThrow(() -> new
                EntityNotFoundException(ExceptionMessage.NONE_MANAGER_MESSAGE));
        survey.setManager(manager);
        surveyRepository.save(survey);
    }

    @Transactional
    public void update(Long id, SurveyUpdateDto surveyUpdateDto) {
        Survey survey = surveyRepository.findById(id).orElseThrow(() -> new
                EntityNotFoundException(ExceptionMessage.NONE_SURVEY_MESSAGE));
        survey.update(surveyUpdateDto.toEntity());
        survey.setTotal(calculateTotal(survey.getBelong(), survey.getGeneration()));
    }

    public SurveyResponseDto getOne(Long id) {
        return surveyRepository.findById(id).orElseThrow(() -> new
                EntityNotFoundException(ExceptionMessage.NONE_SURVEY_MESSAGE)).toDto();
    }

    public List<SurveyResponseDto> getAll(boolean loadCompleted, UserDetails userDetails) {
        String belong = authChecker.getBelong(userDetails);
        int generation = authChecker.getGeneration(userDetails);

        String battalion = belong.substring(0, 1);
        String company = belong.substring(1, 2);
        String platoon = belong.substring(2, 3);

        List<Survey> surveys;
        if (generation == 0) surveys = surveyRepository.findAll(Sort.by(Sort.Direction.DESC, "startDate"));
        else surveys = surveyRepository.findAllByGeneration(generation, Sort.by(Sort.Direction.DESC, "startDate"));
        if (!battalion.equals("0")) {
            surveys = surveys.stream().filter(s -> s.getBelong().substring(0, 1).equals(battalion)).collect(Collectors.toList());

            if (!company.equals("0")) {
                surveys = surveys.stream().filter(s -> s.getBelong().substring(1, 2).equals(company) ||
                        s.getBelong().charAt(1) == '0').collect(Collectors.toList());

                if (!platoon.equals("0")) {
                    surveys = surveys.stream().filter(s -> s.getBelong().substring(2, 3).equals(platoon) ||
                            s.getBelong().charAt(2) == '0').collect(Collectors.toList());
                }
            }
        }

        if (loadCompleted) return getDtoList(surveys);
        else {
            LocalDateTime targetDate = LocalDateTime.now();
            return getDtoList(surveys.stream().filter(s -> !s.getStartDate().isAfter(targetDate) && !s.getEndDate().isBefore(targetDate)).collect(Collectors.toList()));
        }
    }

    @Transactional
    public void delete(Long id) {
        Survey survey = surveyRepository.findById(id).orElseThrow(() -> new
                EntityNotFoundException(ExceptionMessage.NONE_SURVEY_MESSAGE));
        surveyRepository.delete(survey);
    }

    private List<SurveyResponseDto> getDtoList(List<Survey> surveys) {
        List<SurveyResponseDto> list = new ArrayList<>();

        for (Survey survey : surveys) {
            list.add(survey.toDto());
        }

        return list;
    }

    private int calculateTotal(String belong, int generation) {
        int total = 0;
        String battalion = belong.substring(0, 1);
        String company = belong.substring(1, 2);
        String platoon = belong.substring(2, 3);

        List<Soldier> soldiers = soldierRepository.findAllByGeneration(generation);
        if (!battalion.equals("0")) {
            soldiers = soldiers.stream().filter(s -> s.getBelong().substring(0, 1).equals(battalion)).collect(Collectors.toList());

            if (!company.equals("0")) {
                soldiers = soldiers.stream().filter(s -> s.getBelong().substring(1, 2).equals(company) ||
                        s.getBelong().charAt(1) == '0').collect(Collectors.toList());

                if (!platoon.equals("0")) {
                    soldiers = soldiers.stream().filter(s -> s.getBelong().substring(2, 3).equals(platoon) ||
                            s.getBelong().charAt(2) == '0').collect(Collectors.toList());
                }
            }
        }
        return soldiers.size();
    }
}
