package infraestructura.acl.adaptadores;

import com.fasterxml.jackson.databind.JsonNode;
import dominio.modelo.Comparendo;
import dominio.modelo.Persona;
import dominio.modelo.validadores.ComparendoValidador;
import infraestructura.acl.dto.ComparendoDTO;
import infraestructura.acl.dto.PersonaDTO;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Either;
import io.vavr.control.Try;
import play.libs.Json;

import javax.inject.Inject;
import java.time.ZoneId;

public class ComparendoAdaptador {

    private final ComparendoValidador comparendoValidador;

    @Inject
    public ComparendoAdaptador(ComparendoValidador comparendoValidador) {
        this.comparendoValidador = comparendoValidador;
    }

    private Either<Seq<String>, ComparendoDTO> validar(ComparendoDTO co) {
        return comparendoValidador.validarComparendo(
                co.getNumeroComparendo(),
                co.getTipoInfraccion(),
                co.getIdentificacionInfractor(),
                co.getFechaComparendo(),
                co.getValorComparendo())
                .toEither();
    }

    public Either<Seq<String>, ComparendoDTO> transformar(JsonNode json) {
        return validar(json).flatMap(this::validar);
    }

    public Comparendo transformar(ComparendoDTO dto) {
        return new Comparendo(
                dto.getId(),
                dto.getNumeroComparendo(),
                dto.getTipoInfraccion(),
                dto.getIdentificacionInfractor(),
                dto.getFechaComparendo(),
                dto.getValorComparendo());
    }

    public ComparendoDTO transformar(Comparendo comparendo) {
        return new ComparendoDTO(comparendo);
    }

    public ComparendoDTO transformar(Long idComparendo) {
        return new ComparendoDTO(idComparendo);
    }

    private Either<Seq<String>, ComparendoDTO> validar(JsonNode json) {
        Either<Seq<String>, ComparendoDTO> comparendoDTOS = Try.of(() -> Json.fromJson(json, ComparendoDTO.class))
                .toEither()
                .mapLeft(error -> {
                    String err = error.toString();
                    return List.of("Json invalido " + error.toString());

                });
        return comparendoDTOS;
    }

}
