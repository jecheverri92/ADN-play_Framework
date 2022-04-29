package dominio.servicios;

import dominio.modelo.Persona;
import dominio.repositorio.PersonaRepositorio;
import io.vavr.concurrent.Future;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class ServicioPersona {

    private PersonaRepositorio repositorio;


    @Inject
    public ServicioPersona(PersonaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Future<Persona> registrarPersona(Persona persona) {
        return repositorio.insertar(persona);
    }

}
