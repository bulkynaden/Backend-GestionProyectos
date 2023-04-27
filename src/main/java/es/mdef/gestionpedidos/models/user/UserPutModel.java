package es.mdef.gestionpedidos.models.user;

import es.mdef.gestionpedidos.constants.UserEnums;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(itemRelation = "user")
public class UserPutModel extends RepresentationModel<UserPutModel> {
    private String name;
    private String username;
    private UserEnums.Department department;
    private UserEnums.Type type;
    private UserEnums.Role role;
    private String phone;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

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

    public UserEnums.Role getRole() {
        return role;
    }

    public void setRole(UserEnums.Role role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}