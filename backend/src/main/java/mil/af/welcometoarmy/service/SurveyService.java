package mil.af.welcometoarmy.service;

import lombok.RequiredArgsConstructor;
import mil.af.welcometoarmy.repository.SurveyRepository;
import mil.af.welcometoarmy.web.dto.survey.SurveyCreateDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;

    @Transactional
    public void save(SurveyCreateDto surveyCreateDto) {
        surveyRepository.save(surveyCreateDto.toEntity());
    }
}
