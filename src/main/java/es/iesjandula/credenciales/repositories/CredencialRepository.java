package es.iesjandula.credenciales.repositories;

import es.iesjandula.credenciales.models.Credencial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CredencialRepository extends JpaRepository<Credencial,Long>
{
    //Busqueda de Credencial por usuario y ubicacion y/o url
    @Query("""
            SELECT c FROM Credencial c WHERE c.usuario = :usuario
            AND (:ubicacion IS NULL OR c.ubicacion = :ubicacion)
            AND (:url IS NULL OR c.url = :url)
           """)
    List<Credencial> buscarCredencial(String usuario, String ubicacion, String url);

    //Busqueda de Credencial por ubicacion
    @Query("""
            SELECT c 
            FROM Credencial c
            WHERE c.ubicacion = :ubicacion
            """)
    List<Credencial> buscarCredencialPorUbicacion(String ubicacion);

    @Query("""
            SELECT c
            FROM Credencial c
            WHERE c.usuario = :usuario
            AND c.url = :url
            """)
    List<Credencial> buscarCredencialPorUsuarioYUrl(String usuario, String url);

    @Query("""
            SELECT c
            FROM Credencial c
            WHERE c.usuario = :usuario
            AND c.ubicacion = :ubicacion
            """)
    List<Credencial> buscarCredencialPorUsuarioYUbicacion(String usuario, String ubicacion);
}
