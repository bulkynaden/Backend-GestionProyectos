package es.mdef.gestionpedidos.errors;

import java.io.Serial;

public class RegisterNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public RegisterNotFoundException(Long id, String tipo) {
        super("No se ha encontrado el " + tipo + " " + id);
    }
}
