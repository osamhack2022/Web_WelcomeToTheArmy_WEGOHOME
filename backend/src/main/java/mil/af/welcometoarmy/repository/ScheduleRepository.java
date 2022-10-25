package mil.af.welcometoarmy.repository;

import mil.af.welcometoarmy.domain.Schedule;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByGeneration(int generation, Sort sort);
}
