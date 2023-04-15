package es.mdef.gestionpedidos.entities;

import es.mdef.gestionpedidos.constants.UserEnums;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("NotAdmin")
public class NotAdministrator extends User {
    private UserEnums.Type type;
    private UserEnums.Department department;

    public UserEnums.Type getType() {
        return type;
    }

    public void setType(UserEnums.Type type) {
        this.type = type;
    }

    public UserEnums.Department getDepartment() {
        return department;
    }

    public void setDepartment(UserEnums.Department department) {
        this.department = department;
    }

    @Override
    public UserEnums.Role getRole() {
        return UserEnums.Role.NotAdmin;
    }
}
