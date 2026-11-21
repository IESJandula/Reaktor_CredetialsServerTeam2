package es.iesjandula.credenciales.utils;

import lombok.Getter;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CredencialException extends Exception {
    private final String codigo;

    public CredencialException(String codigo, String message) {
        super(message);
        this.codigo = codigo;
    }

    /**
     * Constructor con código, mensaje y causa original.
     */
    public CredencialException(String codigo, String message, Throwable excepcion) {
        super(message, excepcion);
        this.codigo = codigo;
    }

    /**
     * Devuelve un mapa con los detalles de la excepción:
     * código, mensaje y stacktrace de la causa si existe.
     */
    public Object getBodyExceptionMessage() {
        Map<String, Object> mapBodyException = new HashMap<>();
        mapBodyException.put("codigo", this.codigo);
        mapBodyException.put("mensaje", this.getMessage());

        if (this.getCause() != null) {
            String stackTrace = ExceptionUtils.getStackTrace(this.getCause());
            mapBodyException.put("excepcion", stackTrace);
        }

        return mapBodyException;
    }
}
