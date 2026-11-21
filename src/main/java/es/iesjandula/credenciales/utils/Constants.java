package es.iesjandula.credenciales.utils;

public class Constants
{
    /******************************************************/
    /****************** Códigos de error ******************/
    /******************************************************/

    public static final String ERROR_POST_CREDENCIAL_NULA = "ERROR_POST_CREDENCIAL_NULA";

    public static final String ERROR_POST_USUARIO_VACIO = "ERROR_POST_USUARIO_VACIO";

    public static final String ERROR_POST_DESCRIPCION_VACIA = "ERROR_POST_DESCRIPCION_VACIA";

    public static final String ERROR_POST_UBICACION_URL_VACIAS = "ERROR_POST_UBICACION_URL_VACIAS";

    public static final String ERROR_POST_UBICACION_NO_EXISTE = "ERROR_POST_UBICACION_NO_EXISTE";

    public static final String ERROR_POST_PASSWORD_VACIA = "ERROR_POST_PASSWORD_VACIA";

    public static final String ERROR_POST_PASSWORD_FORMATO = "ERROR_POST_PASSWORD_FORMATO";

    public static final String ERROR_SERVIDOR = "ERROR_GENERICO_SERVIDOR";

    public static final int PASSWORD_MIN_LENGTH = 8;

    /** Error Code - Relacionado con un valor de usuario vacio al modificar la credencial */
    public static final String ERROR_USUARIO_VACIO_MODIFICAR_CREDENCIAL = "ERROR_USUARIO_VACIO_MODIFICAR_CREDENCIAL";

    /** Error Code - Relacionado con un valor de ubicacion y/o url vacías al modificar la credencial */
    public static final String ERROR_UBICACION_O_URL_VACIAS_MODIFICAR_CREDENCIAL = "ERROR_UBICACION_O_URL_VACIAS_MODIFICAR_CREDENCIAL";

    /** Error Code - Relacionado con un valor de la innexistencia de una ubicación al modificar la credencial */
    public static final String ERROR_UBICACION_NO_EXISTE_MODIFICAR_CREDENCIAL = "ERROR_UBICACION_NO_EXISTE_MODIFICAR_CREDENCIAL";
}
