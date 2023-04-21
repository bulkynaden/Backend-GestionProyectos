package es.mdef.gestionpedidos.models.user;

import es.mdef.gestionpedidos.constants.UserEnums;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "users")
public class UserListModel extends RepresentationModel<UserListModel> {
    private String name;
    private UserEnums.Role role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserEnums.Role getRole() {
        return role;
    }

    public void setRole(UserEnums.Role role) {
        this.role = role;
    }
}