package mil.af.welcometoarmy.repository;

import mil.af.welcometoarmy.domain.Manager;
import mil.af.welcometoarmy.domain.enums.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Optional<Manager> findByManagerId(String managerId);
    Optional<Manager> findByAuthority(Authority authority);
}
