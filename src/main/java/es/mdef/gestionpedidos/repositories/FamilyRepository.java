package es.mdef.gestionpedidos.repositories;

import es.mdef.gestionpedidos.entities.FamilyImpl;
import es.mdef.gestionpedidos.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FamilyRepository extends JpaRepository<FamilyImpl, Long> {
    List<FamilyImpl> findDistinctByQuestionsUser(User user);
}
