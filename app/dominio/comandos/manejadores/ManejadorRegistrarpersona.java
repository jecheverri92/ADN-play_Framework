package dominio.comandos.manejadores;

import com.fasterxml.jackson.databind.JsonNode;
import dominio.comandos.*;
import dominio.comandos.fabrica.FabricaPersona;
import dominio.modelo.Persona;
import dominio.respuestas.ErrorValidacion;
import dominio.servicios.ServicioPersona;
import infraestructura.acl.adaptadores.PersonaAdaptador;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;

import javax.inject.Inject;

public class ManejadorRegistrarpersona implements Comando {

    private final ServicioPersona servicioPersona;
    private final PersonaAdaptador personaAdaptador;

    @Inject
    public ManejadorRegistrarpersona(ServicioPersona servicioPersona, FabricaPersona fabricaPersona, PersonaAdaptador personaAdaptador) {
        this.servicioPersona = servicioPersona;
        this.personaAdaptador = personaAdaptador;
    }


    @Override
    public Future<Consecuencia> ejecutar(JsonNode json) {
        return personaAdaptador.transformar(json).fold(
                e -> Future.successful(obtenerConsecuenciaFallida("Errores en los datos.")),
                dto -> servicioPersona.registrarPersona(personaAdaptador.transformar(dto))
                        .map(this::obtenerConsecuenciaExitosa));
    }
    private Consecuencia obtenerConsecuenciaFallida(String mensaje) {
        ErrorValidacion error = new ErrorValidacion(mensaje);
        return new Consecuencia(Either.left(error));
    }

    private Consecuencia obtenerConsecuenciaExitosa(Persona persona) {
        return new Consecuencia(Either.right(personaAdaptador.transformar(persona)));
    }
}
