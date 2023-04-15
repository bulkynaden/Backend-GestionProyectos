package es.mdef.gestionpedidos.models.user;

import es.mdef.gestionpedidos.constants.UserEnums;

public class NotAdminModel extends UserModel {
    private UserEnums.Department department;
    private UserEnums.Type type;

    public UserEnums.Department getDepartment() {
        return department;
    }

    public void setDepartment(UserEnums.Department department) {
        this.department = department;
    }

    public UserEnums.Type getType() {
        return type;
    }
    
    public void setType(UserEnums.Type type) {
        this.type = type;
    }
}
