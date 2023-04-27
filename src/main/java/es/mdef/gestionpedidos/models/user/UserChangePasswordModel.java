package es.mdef.gestionpedidos.models.user;

import org.springframework.hateoas.RepresentationModel;

public class UserChangePasswordModel extends RepresentationModel<UserChangePasswordModel> {
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
