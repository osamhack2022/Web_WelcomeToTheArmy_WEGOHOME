package mil.af.welcometoarmy.repository;

import mil.af.welcometoarmy.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Optional<Manager> findByManagerId(String managerId);
}
