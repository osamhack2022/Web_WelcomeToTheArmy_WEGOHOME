package mil.af.welcometoarmy.repository;

import mil.af.welcometoarmy.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
