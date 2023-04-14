package es.mdef.gestionpedidos.models;

import org.springframework.hateoas.server.core.Relation;

@Relation(itemRelation = "user")
public class UserPutModel {
    private String name;
    private String username;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}