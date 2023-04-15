package es.mdef.gestionpedidos.repositories;

import es.mdef.gestionpedidos.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    List<User> findDistinctByQuestionsFamilyId(Long familyId);
}
