package es.iesjandula.credenciales.repositories;

import es.iesjandula.credenciales.models.Credencial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredencialRepository extends JpaRepository<Credencial,Long>
{
}
