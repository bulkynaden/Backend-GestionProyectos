package es.mdef.gestionpedidos.models.user;

public class AdminModel extends UserModel {
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
