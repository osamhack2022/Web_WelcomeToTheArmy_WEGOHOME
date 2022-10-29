package mil.af.welcometoarmy.repository;

import mil.af.welcometoarmy.domain.SurveyAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyAnswerRepository extends JpaRepository<SurveyAnswer, Long> {
}
