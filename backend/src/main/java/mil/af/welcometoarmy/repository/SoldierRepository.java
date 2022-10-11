package mil.af.welcometoarmy.repository;

import mil.af.welcometoarmy.domain.Soldier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoldierRepository extends JpaRepository<Soldier, Long> {
}
