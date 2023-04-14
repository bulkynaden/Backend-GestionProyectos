package es.mdef.gestionpedidos.models;

import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "families")
public class FamilyListModel {
    private String statement;
    private int length;


    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }
}
