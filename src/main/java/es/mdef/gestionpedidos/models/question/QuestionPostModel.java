package es.mdef.gestionpedidos.models.question;

import es.mdef.gestionpedidos.entities.FamilyImpl;
import es.mdef.gestionpedidos.entities.User;
import org.springframework.hateoas.server.core.Relation;

@Relation(itemRelation = "question")
public class QuestionPostModel {
    private String statement;
    private User user;
    private FamilyImpl family;

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FamilyImpl getFamily() {
        return family;
    }

    public void setFamily(FamilyImpl family) {
        this.family = family;
    }
}
