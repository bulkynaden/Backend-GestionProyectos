package es.mdef.gestionpedidos.models.family;

import org.springframework.hateoas.server.core.Relation;

@Relation(itemRelation = "family")
public class FamilyPostModel {
    private String statement;

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }
}
