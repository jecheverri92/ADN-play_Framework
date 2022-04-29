package infraestructura.acl.adaptadores;

import com.fasterxml.jackson.databind.JsonNode;
import dominio.modelo.Persona;
import dominio.modelo.validadores.PersonaValidador;
import infraestructura.acl.dto.PersonaDTO;
import io.vavr.control.Either;
import io.vavr.control.Try;
import play.libs.Json;

import javax.inject.Inject;

public class PersonaAdaptador {


    private final PersonaValidador validador;

    @Inject
    public PersonaAdaptador(PersonaValidador validador) {
        this.validador = validador;
    }

    private Either<String, PersonaDTO> validar(PersonaDTO pr) {
        return validador.validarPersona(pr.getNombre())
                .toEither();
    }

    public Either<String, PersonaDTO> transformar(JsonNode json) {
        return validar(json).flatMap(this::validar);
    }

    public Persona transformar(PersonaDTO dto) {
        return new Persona(dto.getId(),
                dto.getNombre());
    }

    public PersonaDTO transformar(Persona persona) {
        return new PersonaDTO(persona);
    }

    private Either<String, PersonaDTO> validar(JsonNode json) {
        return Try.of(() -> Json.fromJson(json, PersonaDTO.class))
                .toEither()
                .mapLeft(error -> "Json invalido " + error.toString());
    }

}
