package mil.af.welcometoarmy.repository;

import mil.af.welcometoarmy.domain.Soldier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SoldierRepository extends JpaRepository<Soldier, Long> {

    Optional<Soldier> findByPlatoonNum(String platoonNum);
}
