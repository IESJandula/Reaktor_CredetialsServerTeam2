package es.iesjandula.credenciales.rest;

import es.iesjandula.credenciales.dto.CredencialDto;
import es.iesjandula.credenciales.dto.CredencialPassDto;
import es.iesjandula.credenciales.services.CredencialService;
import es.iesjandula.credenciales.utils.Constants;
import es.iesjandula.credenciales.utils.CredencialException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/credentials")
public class CredencialController
{

    /******************************************************/
    /************** Controller de Credenciales ************/
    /******************************************************/


    @Autowired
    private CredencialService credencialService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> crearCredencial(@RequestBody(required = false) CredencialPassDto credencialPassDto)
    {
        try
        {
            this.credencialService.crearCredencial(credencialPassDto);
            return ResponseEntity.ok().build();
        }
        catch (CredencialException exception)
        {
            if (exception.getCodigo().equals(Constants.ERROR_POST_CREDENCIAL_NULA))
            {
                return ResponseEntity.status(400).body(exception.getBodyExceptionMessage());
            }
            else if (exception.getCodigo().equals(Constants.ERROR_POST_USUARIO_VACIO))
            {
                return ResponseEntity.status(401).body(exception.getBodyExceptionMessage());
            }
            else if (exception.getCodigo().equals(Constants.ERROR_POST_DESCRIPCION_VACIA))
            {
                return ResponseEntity.status(402).body(exception.getBodyExceptionMessage());
            }
            else if (exception.getCodigo().equals(Constants.ERROR_POST_UBICACION_URL_VACIAS))
            {
                return ResponseEntity.status(403).body(exception.getBodyExceptionMessage());
            }
            else if (exception.getCodigo().equals(Constants.ERROR_POST_UBICACION_NO_EXISTE))
            {
                return ResponseEntity.status(404).body(exception.getBodyExceptionMessage());
            }
            else if (exception.getCodigo().equals(Constants.ERROR_POST_PASSWORD_VACIA))
            {
                return ResponseEntity.status(405).body(exception.getBodyExceptionMessage());
            }

            return ResponseEntity.status(406).body(exception.getBodyExceptionMessage());
        }
        catch (Exception exception)
        {
            return this.errorGenerico("Error al crear la credencial", exception);
        }
    }

    /**
     * Endpoint de listar todas las credenciales sin password
     * Devuelve los datos de las credenciales. Si no hay registros, el array se devuelve vacío
     * */

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> listarCredenciales()
    {
        try
        {
            return ResponseEntity.status(200).body(this.credencialService.listarCredenciales());
        }
        catch (Exception exception)
        {
            return this.errorGenerico("Error al crear la listar las credenciales", exception);
        }
    }

    /**
     * ENDPOINT DELETE TO DO
     * */

    /**
     * Endpoint de actualizar el funcionamiento de una credencial
     * Debe ir relleno el usuario y al menos una de las cabeceras ubicación o URL.
     * Actualiza el campo "funciona" de todos los registros encontrados
     * */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> actualizarFuncionamiento(@RequestBody(required = true) CredencialDto credencialDto, @RequestHeader(required = true) Boolean funciona)
    {
        try
        {
            this.credencialService.actualizarFuncionamiento(credencialDto, funciona);
            return ResponseEntity.status(200).build();
        }
        catch (CredencialException exception)
        {
            if(exception.getCodigo().equals(Constants.ERROR_USUARIO_VACIO_MODIFICAR_CREDENCIAL))
            {
                return ResponseEntity.status(400).body(exception.getBodyExceptionMessage());
            }
            else if (exception.getCodigo().equals(Constants.ERROR_UBICACION_O_URL_VACIAS_MODIFICAR_CREDENCIAL))
            {
                return ResponseEntity.status(401).body(exception.getBodyExceptionMessage());
            }
            return ResponseEntity.status(403).body(exception.getBodyExceptionMessage());

        }
        catch (Exception exception)
        {
            return this.errorGenerico("Error al actualizar la credencial", exception);
        }
    }

    /**
     *
     * @param credencialDto
     * @return
     */
    @RequestMapping(value = "/password", method = RequestMethod.GET)
    public ResponseEntity<?> obtenerPassword(@RequestBody CredencialDto credencialDto)
    {
        try
        {
            return ResponseEntity.status(200).body(this.credencialService.obtenerCredencialService(credencialDto));
        }
        catch (CredencialException errorException)
        {
            if(errorException.getCodigo().equals(Constants.ERROR_USUARIO_NULO_O_VACIO_OBTENER))
            {
                return ResponseEntity.status(400).body(errorException.getBodyExceptionMessage());
            }
            else if (errorException.getCodigo().equals(Constants.ERROR_UBICACION_O_URL_NULO_O_VACIO_OBTENER))
            {
                return ResponseEntity.status(401).body(errorException.getBodyExceptionMessage());
            }
            else if (errorException.getCodigo().equals(Constants.ERROR_UBICACION_NO_EXISTE_OBTENER))
            {
                return ResponseEntity.status(402).body(errorException.getBodyExceptionMessage());
            }
            else if (errorException.getCodigo().equals(Constants.ERROR_CREDENCIAL_DUPLICADA_OBTENER))
            {
                return ResponseEntity.status(403).body(errorException.getBodyExceptionMessage());
            }
            else
            {
                return ResponseEntity.status(404).body(errorException.getBodyExceptionMessage());
            }
        }
        catch (Exception e)
        {
            String error = "Error interno de servidor.";
            log.error(error);
            return this.errorGenerico(error, e);
        }
    }
    /**
     * Metodo para los errores del servidor
     * */
    private ResponseEntity<?> errorGenerico(String errorString, Exception exception)
    {
        CredencialException credencialException = new CredencialException(Constants.ERROR_SERVIDOR, errorString, exception);

        log.error(errorString, exception);

        return ResponseEntity.status(500).body(credencialException.getBodyExceptionMessage());
    }

}
