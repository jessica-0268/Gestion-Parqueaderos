package co.edu.autonoma.gestionParqueaderos.exception;

/**
 * Excepción utilizada cuando no se encuentra
 * un recurso solicitado en el sistema.
 */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
