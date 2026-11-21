package es.iesjandula.credenciales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CredencialPassDto extends CredencialDto
{
    private String password;
}
