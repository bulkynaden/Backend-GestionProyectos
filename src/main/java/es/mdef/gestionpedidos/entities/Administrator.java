package es.mdef.gestionpedidos.entities;

import es.mdef.gestionpedidos.constants.UserEnums;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Pattern;

@Entity
@DiscriminatorValue("Admin")
public class Administrator extends User {
    @Pattern(regexp = "^((\\+34|0034|34)\\s?)?[6-9][0-9]{2}\\s?(([0-9]{3}\\s?[0-9]{3})|([0-9]{2}\\s[0-9]{2}\\s[0-9]{2}))$",
            message = "El teléfono no tiene el formato correcto")
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public UserEnums.Role getRole() {
        return UserEnums.Role.Admin;
    }
}
