package es.mdef.gestionpedidos.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "families")
public class FamilyImpl implements Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String statement;
    
    @OneToMany(mappedBy = "family")
    private List<Question> questions = new ArrayList<>();

    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    public int getLength() {
        return questions.size();
    }

    @Override
    public String getStatement() {
        return statement;
    }

    @Override
    public void setStatement(String statement) {
        this.statement = statement;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "FamilyImpl{" +
                "id=" + id +
                ", statement='" + statement + '\'' +
                '}';
    }
}
