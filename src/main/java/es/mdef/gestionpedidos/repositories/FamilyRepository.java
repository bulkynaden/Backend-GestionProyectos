package es.mdef.gestionpedidos.repositories;

import es.mdef.gestionpedidos.entities.FamilyImpl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyRepository extends JpaRepository<FamilyImpl, Long> {
}
