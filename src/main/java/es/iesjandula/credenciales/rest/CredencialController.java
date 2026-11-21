package es.iesjandula.credenciales.rest;

import es.iesjandula.credenciales.dto.CredencialPassDto;
import es.iesjandula.credenciales.services.CredencialService;
import es.iesjandula.credenciales.utils.Constants;
import es.iesjandula.credenciales.utils.CredencialException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/credentials")
public class CredencialController
{
    @Autowired
    private CredencialService credencialService;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
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

    private ResponseEntity<?> errorGenerico(String errorString, Exception exception)
    {
        CredencialException credencialException = new CredencialException(Constants.ERROR_SERVIDOR, errorString, exception);

        log.error(errorString, exception);
        return ResponseEntity.status(Constants.ERROR_SERVIDOR).body(credencialException.getBodyExceptionMessage());
    }

}
