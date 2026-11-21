package es.iesjandula.credenciales.services;

import es.iesjandula.credenciales.repositories.CredencialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredencialService
{
    @Autowired
    private CredencialRepository credencialRepository;
}
