package es.iesjandula.credenciales.utils;

public class Constants
{
    /******************************************************/
    /****************** Códigos de error ******************/
    /******************************************************/

    public static final int ERROR_POST_CREDENCIAL_NULA = 400;

    public static final int ERROR_POST_USUARIO_VACIO = 401;

    public static final int ERROR_POST_DESCRIPCION_VACIA = 402;

    public static final int ERROR_POST_UBICACION_URL_VACIAS = 403;

    public static final int ERROR_POST_UBICACION_NO_EXISTE = 404;

    public static final int ERROR_POST_PASSWORD_VACIA = 405;

    public static final int ERROR_POST_PASSWORD_FORMATO = 406;

    public static final String ERROR_SERVIDOR = "ERROR_GENERICO_SERVIDOR";

    public static final int PASSWORD_MIN_LENGTH = 8;

    /** Error Code - Relacionado con un valor de usuario vacio al modificar la credencial */
    public static final String ERROR_USUARIO_VACIO_MODIFICAR_CREDENCIAL = "ERROR_USUARIO_VACIO_MODIFICAR_CREDENCIAL";

    /** Error Code - Relacionado con un valor de ubicacion y/o url vacías al modificar la credencial */
    public static final String ERROR_UBICACION_O_URL_VACIAS_MODIFICAR_CREDENCIAL = "ERROR_UBICACION_O_URL_VACIAS_MODIFICAR_CREDENCIAL";

    /** Error Code - Relacionado con un valor de la innexistencia de una ubicación al modificar la credencial */
    public static final String ERROR_UBICACION_NO_EXISTE_MODIFICAR_CREDENCIAL = "ERROR_UBICACION_NO_EXISTE_MODIFICAR_CREDENCIAL";
}
