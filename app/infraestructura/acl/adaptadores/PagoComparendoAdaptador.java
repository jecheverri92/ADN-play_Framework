package infraestructura.acl.adaptadores;

import com.fasterxml.jackson.databind.JsonNode;
import dominio.modelo.Comparendo;
import dominio.modelo.validadores.ComparendoValidador;
import dominio.modelo.validadores.PagoComparendoValidador;
import infraestructura.acl.dto.ComparendoDTO;
import infraestructura.acl.dto.PagoComparendoDTO;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Either;
import io.vavr.control.Try;
import play.libs.Json;

import javax.inject.Inject;

public class PagoComparendoAdaptador {

    private final PagoComparendoValidador pagoComparendoValidador;

    @Inject
    public PagoComparendoAdaptador(PagoComparendoValidador pagoComparendoValidador) {
        this.pagoComparendoValidador = pagoComparendoValidador;
    }

    private Either<Seq<String>, PagoComparendoDTO> validar(PagoComparendoDTO dto) {
        return pagoComparendoValidador.validarPagoComparendo(
                        dto.getNumeroComparendo(),
                        dto.getValorPagado())
                .toEither();
    }

    public Either<Seq<String>, PagoComparendoDTO> transformar(JsonNode json) {
        return validar(json).flatMap(this::validar);
    }



    public ComparendoDTO transformar(Comparendo comparendo) {
        return new ComparendoDTO(comparendo);
    }

    public ComparendoDTO transformar(Long idComparendo) {
        return new ComparendoDTO(idComparendo);
    }

    private Either<Seq<String>, PagoComparendoDTO> validar(JsonNode json) {
        Either<Seq<String>, PagoComparendoDTO> pagoComparendoDTO = Try.of(() -> Json.fromJson(json, PagoComparendoDTO.class))
                .toEither()
                .mapLeft(error -> {
                    String err = error.toString();
                    return List.of("Json invalido " + error.toString());

                });
        return pagoComparendoDTO;
    }
}
