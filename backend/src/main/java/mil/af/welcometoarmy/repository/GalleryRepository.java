package mil.af.welcometoarmy.repository;

import mil.af.welcometoarmy.domain.Gallery;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    List<Gallery> findAllByGeneration(int generation, Sort sort);
}
