package es.mdef.gestionpedidos.entities;

import es.mdef.support.Familia;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "families")
public class FamilyImpl extends Familia implements Family {
    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL)
    private final List<Question> questions = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public int getLength() {
        return questions.size();
    }


    @Override
    public String getStatement() {
        return super.getEnunciado();
    }

    @Override
    public void setStatement(String statement) {
        super.setEnunciado(statement);
    }

    @Override
    public Long getId() {
        return id;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}

