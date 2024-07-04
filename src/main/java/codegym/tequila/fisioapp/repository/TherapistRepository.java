package codegym.tequila.fisioapp.repository;

import codegym.tequila.fisioapp.model.Therapist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TherapistRepository extends JpaRepository<Therapist, String> {

}
