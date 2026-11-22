package es.iesjandula.credenciales.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Jasypt para cifrar las contraseñas de las credenciales
 * antes de guardarlas en la base de datos.
 */
@Configuration
public class JasyptConfig
{
    // La clave maestra no va en el código, se lee de una variable de entorno
    @Value("${JASYPT_ENCRYPTOR_PASSWORD}")
    private String claveMaestra;

    /**
     * Crea el encryptor que se usa para cifrar y descifrar las contraseñas.
     * Al ser un @Bean, Spring lo crea una vez y lo podemos usar con @Autowired.
     */
    @Bean
    public StandardPBEStringEncryptor encryptor()
    {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(this.claveMaestra);
        encryptor.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        encryptor.setIvGenerator(new RandomIvGenerator());
        return encryptor;
    }
}