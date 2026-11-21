package es.iesjandula.credenciales.dto;

import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class CredencialDto
{
    private String usuario;

    private String descripcion;

    private String ubicacion;

    private String url;
}
