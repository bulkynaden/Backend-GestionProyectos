package es.mdef.gestionpedidos.repositories;

import es.mdef.gestionpedidos.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
