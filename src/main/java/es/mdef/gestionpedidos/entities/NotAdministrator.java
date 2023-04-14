package es.mdef.gestionpedidos.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("NotAdministrator")
public class NotAdministrator extends User {
    private Type type;
    private Department department;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public enum Type {
        Alumno,
        Docente,
        Administracion
    }

    public enum Department {
        EMIES,
        CCESP
    }
}
