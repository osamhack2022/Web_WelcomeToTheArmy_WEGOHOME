package mil.af.welcometoarmy.repository;

import mil.af.welcometoarmy.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
}
