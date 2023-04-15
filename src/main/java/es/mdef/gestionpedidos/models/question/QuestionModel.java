package es.mdef.gestionpedidos.models.question;

import org.springframework.hateoas.server.core.Relation;

@Relation(itemRelation = "question")
public class QuestionModel {
    private String statement;

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }
}
