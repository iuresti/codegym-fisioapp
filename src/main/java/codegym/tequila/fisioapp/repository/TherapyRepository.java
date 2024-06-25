package codegym.tequila.fisioapp.repository;

import codegym.tequila.fisioapp.model.Therapy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TherapyRepository extends JpaRepository<Therapy, String> {

    List<Therapy> findAllByActive(boolean active, Pageable pageable);

}
