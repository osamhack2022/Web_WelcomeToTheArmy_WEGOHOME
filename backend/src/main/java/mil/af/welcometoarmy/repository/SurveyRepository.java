package mil.af.welcometoarmy.repository;

import mil.af.welcometoarmy.domain.Survey;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findAllByGeneration(int generation, Sort sort);
}
