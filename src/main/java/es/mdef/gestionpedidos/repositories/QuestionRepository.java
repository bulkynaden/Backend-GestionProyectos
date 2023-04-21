package es.mdef.gestionpedidos.repositories;

import es.mdef.gestionpedidos.entities.Question;
import es.mdef.gestionpedidos.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findQuestionByUser(User user);
}