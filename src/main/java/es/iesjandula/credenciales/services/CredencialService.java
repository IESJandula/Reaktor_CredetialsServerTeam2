package es.iesjandula.credenciales.services;

import es.iesjandula.credenciales.dto.CredencialDto;
import es.iesjandula.credenciales.dto.CredencialPassDto;
import es.iesjandula.credenciales.models.Credencial;
import es.iesjandula.credenciales.repositories.CredencialRepository;
import es.iesjandula.credenciales.utils.Constants;
import es.iesjandula.credenciales.utils.CredencialException;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CredencialService
{
    /******************************************************/
    /************** Service de Credenciales ***************/
    /******************************************************/

    @Autowired
    private CredencialRepository credencialRepository;

    @Autowired
    private StandardPBEStringEncryptor encryptor;

    public void crearCredencial(CredencialPassDto credencialPassDto) throws CredencialException
    {
        // Si no llega nada en el body
        if (credencialPassDto == null)
        {
            String errorString = "La credencial no puede ser nula";
            log.error(errorString);
            throw new CredencialException(Constants.ERROR_POST_CREDENCIAL_NULA, errorString);
        }

        // Primero miro el null y luego si está vacío, porque si es null el isBlank() peta
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

        // Puede venir solo la ubicación o solo la url, pero las dos vacías no
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

        // Miro que la contraseña sea segura
        this.validarPassword(password);

        //Creo la credencial con los datos del DTO
        Credencial credencial = new Credencial();
        credencial.setUsuario(credencialPassDto.getUsuario());
        credencial.setDescripcion(credencialPassDto.getDescripcion());
        credencial.setUbicacion(credencialPassDto.getUbicacion());
        credencial.setUrl(credencialPassDto.getUrl());
        credencial.setPassword(this.encryptor.encrypt(password));
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

        // Recorro la contraseña letra a letra y voy marcando qué tipos tiene
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
                // Si no es letra ni número, es un símbolo
                tieneEspecial = true;
            }
        }

        String errorString = null;

        // Compruebo las reglas una a una y me quedo con la primera que falle
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

        // Si ha fallado alguna, lanza el error
        if (errorString != null)
        {
            log.error(errorString);
            throw new CredencialException(Constants.ERROR_POST_PASSWORD_FORMATO, errorString);
        }
    }

    public List<CredencialDto> listarCredenciales ()
    {
        //Recojo todas las credenciales en una lista
        List<Credencial> credenciales = this.credencialRepository.findAll();
        //Creo una lista del dto (lo que debo devolver según el Swagger)
        List<CredencialDto> credencialesDtos = new ArrayList<>();

        for(int i = 0; i < credenciales.size(); i++)
        {
            //Guardo los datos de la lista de todas las credenciales y lo guardo en la lista de DTO
            CredencialDto dto = new CredencialDto();

            dto.setUsuario(credenciales.get(i).getUsuario());
            dto.setDescripcion(credenciales.get(i).getDescripcion());
            dto.setUbicacion(credenciales.get(i).getUbicacion());
            dto.setUrl(credenciales.get(i).getUrl());

            credencialesDtos.add(dto);
        }

        //Devolvemos la lista de dto
        return credencialesDtos;
    }

    public void actualizarFuncionamiento(CredencialDto credencialDto, Boolean funciona) throws CredencialException
    {
        //Llamo el metodo para comprobar si todo es correcto
        this.validarCredencial(credencialDto);

        //Busco desde la Query creada por Usuario y ubicacion/url
        List <Credencial> credenciales = this.credencialRepository.buscarCredencial(credencialDto.getUsuario(), credencialDto.getUbicacion(),  credencialDto.getUrl());
        for (int i = 0; i < credenciales.size(); i++)
        {
            //Cambio el boolean por el valor dado
            credenciales.get(i).setFunciona(funciona);
        }

        //Actuaalizamos en el repositorio
        this.credencialRepository.saveAll(credenciales);
    }

    public String obtenerCredencialService(CredencialDto credencialDto) throws CredencialException
    {
        //Comprobacion del usuario nulo o vacio.
        if( credencialDto.getUsuario() == null || credencialDto.getUsuario().isEmpty())
        {
            String error= "El usuario es nulo o vacio";
            log.error(error);
            throw new CredencialException(Constants.ERROR_USUARIO_NULO_O_VACIO_OBTENER, error);
        }

        //Comprobacion si la url o la ubicacionVacia es nula o vacia.
        boolean urlVacia = credencialDto.getUrl() == null  || credencialDto.getUrl().isEmpty() ;
        boolean ubicacionVacia =  credencialDto.getUbicacion() == null || credencialDto.getUbicacion().isEmpty();

        Credencial credencial = null;
        //Comprobamos si hay primero una url, si no hay comprobamos si hay ubicacion
        if(urlVacia)
        {
            if(ubicacionVacia)
            {
                String error = "La credencial tiene que tener una ubicacion o url";
                log.error(error);
                throw new CredencialException(Constants.ERROR_UBICACION_O_URL_NULO_O_VACIO_OBTENER, error);
            }
        }

        //Si hay url buscamos por usuario y url
        if(!urlVacia)
        {
            credencial = this.credencialRepository.buscarCredencialPorUsuarioYUrl(credencialDto.getUsuario(), credencialDto.getUrl());
        }

        //Si hay ubicacion buscamos por usuario y ubicacion
        if(!ubicacionVacia)
        {
            List<Credencial> ubicacion1 = this.credencialRepository.buscarCredencialPorUbicacion(credencialDto.getUbicacion());
            if(ubicacion1.isEmpty())
            {
                String error="Ubicacion no existe.";
                log.error(error);
                throw new CredencialException(Constants.ERROR_UBICACION_NO_EXISTE_OBTENER, error);
            }
            credencial = this.credencialRepository.buscarCredencialPorUsuarioYUbicacion(credencialDto.getUsuario(), credencialDto.getUbicacion());
        }

        //return this.encryptor.decrypt(credencial.getPassword());
        return credencial.getPassword();
    }

    public void eliminarCredencial(CredencialDto credencialDto) throws CredencialException
    {
        //Comprobacion del usuario nulo o vacio.
        if( credencialDto.getUsuario() == null || credencialDto.getUsuario().isEmpty())
        {
            String error= "El usuario es nulo o vacio";
            log.error(error);
            throw new CredencialException(Constants.ERROR_USUARIO_NULO_O_VACIO_DELETE , error);
        }

        //Comprobacion si la url o la ubicacionVacia es nula o vacia.
        boolean urlVacia = credencialDto.getUrl() == null  || credencialDto.getUrl().isEmpty() ;
        boolean ubicacionVacia =  credencialDto.getUbicacion() == null || credencialDto.getUbicacion().isEmpty();

        Credencial credencial = null;
        //Comprobamos si hay primero una url, si no hay comprobamos si hay ubicacion
        if(urlVacia)
        {
            if(ubicacionVacia)
            {
                String error = "La credencial tiene que tener una ubicacion o url";
                log.error(error);
                throw new CredencialException(Constants.ERROR_UBICACION_O_URL_NULO_O_VACIO_DELETE, error);
            }
        }

        //Si hay url buscamos por usuario y url
        if(!urlVacia)
        {
            credencial = this.credencialRepository.buscarCredencialPorUsuarioYUrl(credencialDto.getUsuario(), credencialDto.getUrl());
        }

        //Si hay ubicacion buscamos por usuario y ubicacion
        if(!ubicacionVacia)
        {
            List<Credencial> ubicacion1 = this.credencialRepository.buscarCredencialPorUbicacion(credencialDto.getUbicacion());
            if(ubicacion1 == null)
            {
                String error="Ubicacion no existe.";
                log.error(error);
                throw new CredencialException(Constants.ERROR_UBICACION_NO_EXISTE_DELETE, error);
            }
            credencial = this.credencialRepository.buscarCredencialPorUsuarioYUbicacion(credencialDto.getUsuario(), credencialDto.getUbicacion());
        }

        this.credencialRepository.delete(credencial);
        System.out.println("credenciales eliminadas");
    }

    public void validarCredencial(CredencialDto credencialDto) throws CredencialException
    {
        //Error si el usuario es nulo o vacio
        if(credencialDto.getUsuario() == null || credencialDto.getUsuario().isEmpty())
        {
            String msgError = "Usuario nulo o vacío al actualizar el funcionamiento";

            log.error(msgError);

            throw new CredencialException(Constants.ERROR_USUARIO_VACIO_MODIFICAR_CREDENCIAL, msgError);
        }

        //Error si la ubicacion es nula o vacia
        if((credencialDto.getUbicacion() == null || credencialDto.getUbicacion().isEmpty()) && (credencialDto.getUrl() == null || credencialDto.getUrl().isEmpty()))
        {
            String msgError = "La ubicación y la url son nulas o vacías. Una de las dos debe estar rellena";

            log.error(msgError);

            throw new CredencialException(Constants.ERROR_UBICACION_O_URL_VACIAS_MODIFICAR_CREDENCIAL, msgError);
        }

        //Error si no existe la ubicacion dada (a través de una consulta Query creada)
        if(this.credencialRepository.buscarCredencialPorUbicacion(credencialDto.getUbicacion()).isEmpty())
        {
            String msgError = "La ubicación no existe";

            log.error(msgError);

            throw new CredencialException(Constants.ERROR_UBICACION_NO_EXISTE_MODIFICAR_CREDENCIAL, msgError);
        }
    }
}
