package es.iesjandula.credenciales.rest;

import es.iesjandula.credenciales.services.CredencialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/credentials")
public class CredencialController
{
    @Autowired
    private CredencialService credencialService;
}
