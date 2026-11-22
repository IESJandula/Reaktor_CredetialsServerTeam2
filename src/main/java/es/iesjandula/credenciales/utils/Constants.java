package es.iesjandula.credenciales.utils;

public class Constants
{
    /******************************************************/
    /****************** Códigos de error ******************/
    /******************************************************/

    /** Error Code - No llega el body al crear la credencial */
    public static final String ERROR_POST_CREDENCIAL_NULA = "ERROR_POST_CREDENCIAL_NULA";

    /** Error Code - Usuario nulo o vacío al crear */
    public static final String ERROR_POST_USUARIO_VACIO = "ERROR_POST_USUARIO_VACIO";

    /** Error Code - Descripción nula o vacía al crear */
    public static final String ERROR_POST_DESCRIPCION_VACIA = "ERROR_POST_DESCRIPCION_VACIA";

    /** Error Code - Ubicación y url vacías a la vez al crear */
    public static final String ERROR_POST_UBICACION_URL_VACIAS = "ERROR_POST_UBICACION_URL_VACIAS";

    /** Error Code - La ubicación no existe al crear */
    public static final String ERROR_POST_UBICACION_NO_EXISTE = "ERROR_POST_UBICACION_NO_EXISTE";

    /** Error Code - Contraseña nula o vacía al crear */
    public static final String ERROR_POST_PASSWORD_VACIA = "ERROR_POST_PASSWORD_VACIA";

    /** Error Code - La contraseña no cumple el formato al crear */
    public static final String ERROR_POST_PASSWORD_FORMATO = "ERROR_POST_PASSWORD_FORMATO";

    /** Error Code - Error del servidor */
    public static final String ERROR_SERVIDOR = "ERROR_GENERICO_SERVIDOR";

    /** Mínimo de caracteres de la contraseña */
    public static final int PASSWORD_MIN_LENGTH = 8;

    /** Error Code - Relacionado con un valor de usuario vacio al modificar la credencial */
    public static final String ERROR_USUARIO_VACIO_MODIFICAR_CREDENCIAL = "ERROR_USUARIO_VACIO_MODIFICAR_CREDENCIAL";

    /** Error Code - Relacionado con un valor de ubicacion y/o url vacías al modificar la credencial */
    public static final String ERROR_UBICACION_O_URL_VACIAS_MODIFICAR_CREDENCIAL = "ERROR_UBICACION_O_URL_VACIAS_MODIFICAR_CREDENCIAL";

    /** Error Code - Relacionado con un valor de la innexistencia de una ubicación al modificar la credencial */
    public static final String ERROR_UBICACION_NO_EXISTE_MODIFICAR_CREDENCIAL = "ERROR_UBICACION_NO_EXISTE_MODIFICAR_CREDENCIAL";

    /** Error Code - Relacionado con un valor de ubicacion y/o url nulo o vacio a la hora de obtener contraseña */
    public static final String ERROR_UBICACION_O_URL_NULO_O_VACIO_OBTENER = "ERROR_UBICACION_O_URL_NULO_O_VACIO_OBTENER";

    /** Error code - Relacionado con un valor de usuario nulo o vacio a la hora de obtener contraseña */
    public static final String ERROR_USUARIO_NULO_O_VACIO_OBTENER = "ERROR_USUARIO_NULO_O_VACIO_OBTENER";

    /** Error Code - Relacionado con un valor de la innexistencia de una credencial al obtener */
    public static final String ERROR_CREDENCIAL_NO_EXISTE_OBTENER = "ERROR_CREDENCIAL_NO_EXISTE_OBTENER";

    /** Error Code - Relacionado con un valor de la innexistencia de una ubicacion al obtener */
    public static final String ERROR_UBICACION_NO_EXISTE_OBTENER = "ERROR_UBICACION_NO_EXISTE_OBTENER";

    public static final String ERROR_CREDENCIAL_DUPLICADA_OBTENER = "ERROR_CREDENCIAL_DUPLICADA_OBTENER";

    /** Error Code - Relacionado con un valor de ubicacion y/o url nulo o vacio a la hora de borrar contraseña */
    public static final String ERROR_UBICACION_O_URL_NULO_O_VACIO_DELETE = "ERROR_UBICACION_O_URL_NULO_O_VACIO_DELETE";

    /** Error code - Relacionado con un valor de usuario nulo o vacio a la hora de obtener contraseña */
    public static final String ERROR_USUARIO_NULO_O_VACIO_DELETE = "ERROR_USUARIO_NULO_O_VACIO_OBTENER";

    /** Error Code - Relacionado con un valor de la innexistencia de una ubicacion al obtener */
    public static final String ERROR_UBICACION_NO_EXISTE_DELETE = "ERROR_UBICACION_NO_EXISTE_DELETE";

}
