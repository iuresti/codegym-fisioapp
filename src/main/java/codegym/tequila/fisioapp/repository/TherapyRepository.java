package codegym.tequila.fisioapp.repository;

import codegym.tequila.fisioapp.model.Therapy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TherapyRepository extends JpaRepository<Therapy, String > {

}
