package es.iesjandula.credenciales.services;

import es.iesjandula.credenciales.dto.CredencialPassDto;
import es.iesjandula.credenciales.models.Credencial;
import es.iesjandula.credenciales.repositories.CredencialRepository;
import es.iesjandula.credenciales.utils.Constants;
import es.iesjandula.credenciales.utils.CredencialException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CredencialService
{
    @Autowired
    private CredencialRepository credencialRepository;

    public void crearCredencial(CredencialPassDto credencialPassDto) throws CredencialException
    {
        if (credencialPassDto == null)
        {
            String errorString = "La credencial no puede ser nula";
            log.error(errorString);
            throw new CredencialException(Constants.ERROR_POST_CREDENCIAL_NULA, errorString);
        }

        if (credencialPassDto.getUsuario() == null || credencialPassDto.getUsuario().isBlank())
        {
            String errorString = "El usuario no puede ser nulo o vacío";
            log.error(errorString);
            throw new CredencialException(Constants.ERROR_POST_USUARIO_VACIO, errorString);
        }

        if (credencialPassDto.getDescripcion() == null || credencialPassDto.getDescripcion().isBlank())
        {
            String errorString = "La descripción no puede ser nula o vacía";
            log.error(errorString);
            throw new CredencialException(Constants.ERROR_POST_DESCRIPCION_VACIA, errorString);
        }

        boolean ubicacionVacia = credencialPassDto.getUbicacion() == null || credencialPassDto.getUbicacion().isBlank();
        boolean urlVacia = credencialPassDto.getUrl() == null || credencialPassDto.getUrl().isBlank();

        if (ubicacionVacia && urlVacia)
        {
            String errorString = "La ubicación y la url no pueden estar vacías a la vez";
            log.error(errorString);
            throw new CredencialException(Constants.ERROR_POST_UBICACION_URL_VACIAS, errorString);
        }

        String password = credencialPassDto.getPassword();

        if (password == null || password.isEmpty())
        {
            String errorString = "La contraseña no puede ser nula o vacía";
            log.error(errorString);
            throw new CredencialException(Constants.ERROR_POST_PASSWORD_VACIA, errorString);
        }

        this.validarPassword(password);

        Credencial credencial = new Credencial();
        credencial.setUsuario(credencialPassDto.getUsuario());
        credencial.setDescripcion(credencialPassDto.getDescripcion());
        credencial.setUbicacion(credencialPassDto.getUbicacion());
        credencial.setUrl(credencialPassDto.getUrl());
        credencial.setPassword(password);
        credencial.setFunciona(true);

        this.credencialRepository.save(credencial);

        log.info("Credencial del usuario registrada");
    }

    private void validarPassword(String password) throws CredencialException
    {
        boolean tieneMayuscula = false;
        boolean tieneMinuscula = false;
        boolean tieneDigito = false;
        boolean tieneEspecial = false;

        for (int i = 0; i < password.length(); i++)
        {
            char caracter = password.charAt(i);

            if (Character.isUpperCase(caracter))
            {
                tieneMayuscula = true;
            }
            else if (Character.isLowerCase(caracter))
            {
                tieneMinuscula = true;
            }
            else if (Character.isDigit(caracter))
            {
                tieneDigito = true;
            }
            else
            {
                tieneEspecial = true;
            }
        }

        String errorString = null;

        if (password.length() < Constants.PASSWORD_MIN_LENGTH)
        {
            errorString = "La contraseña debe tener al menos " + Constants.PASSWORD_MIN_LENGTH + " caracteres";
        }
        else if (!tieneMayuscula)
        {
            errorString = "La contraseña debe contener al menos una letra mayúscula";
        }
        else if (!tieneMinuscula)
        {
            errorString = "La contraseña debe contener al menos una letra minúscula";
        }
        else if (!tieneDigito)
        {
            errorString = "La contraseña debe contener al menos un dígito";
        }
        else if (!tieneEspecial)
        {
            errorString = "La contraseña debe contener al menos un signo de puntuación o símbolo";
        }

        if (errorString != null)
        {
            log.error(errorString);
            throw new CredencialException(Constants.ERROR_POST_PASSWORD_FORMATO, errorString);
        }
    }
}
