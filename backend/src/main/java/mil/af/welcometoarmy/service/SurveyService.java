package mil.af.welcometoarmy.service;

import lombok.RequiredArgsConstructor;
import mil.af.welcometoarmy.domain.Soldier;
import mil.af.welcometoarmy.domain.Survey;
import mil.af.welcometoarmy.exception.EntityNotFoundException;
import mil.af.welcometoarmy.exception.ExceptionMessage;
import mil.af.welcometoarmy.repository.SurveyRepository;
import mil.af.welcometoarmy.web.dto.soldier.SoldierResponseDto;
import mil.af.welcometoarmy.web.dto.survey.SurveyCreateDto;
import mil.af.welcometoarmy.web.dto.survey.SurveyResponseDto;
import mil.af.welcometoarmy.web.dto.survey.SurveyUpdateDto;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;

    @Transactional
    public void save(SurveyCreateDto surveyCreateDto) {
        Survey survey = surveyCreateDto.toEntity();
        if (survey.getStartDate().isAfter(survey.getEndDate()))
            throw new IllegalArgumentException("조사 마감일보다 조사 시작일이 빨라야 합니다");
        surveyRepository.save(survey);
    }

    @Transactional
    public void update(Long id, SurveyUpdateDto surveyUpdateDto) {
        Survey survey = surveyRepository.findById(id).orElseThrow(() -> new
                EntityNotFoundException(ExceptionMessage.NONE_SURVEY_MESSAGE));
        survey.update(surveyUpdateDto.toEntity());
    }

    public SurveyResponseDto getOne(Long id) {
        return surveyRepository.findById(id).orElseThrow(() -> new
                EntityNotFoundException(ExceptionMessage.NONE_SURVEY_MESSAGE)).toDto();
    }

    public List<SurveyResponseDto> getAll() {
        return getDtoList(surveyRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
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
}
