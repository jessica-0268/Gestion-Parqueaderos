package co.edu.autonoma.gestionParqueaderos.exception;

/**
 * Excepción utilizada cuando una operación
 * incumple una regla de negocio del sistema.
 */
public class ReglaNegocioException extends RuntimeException {

    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
