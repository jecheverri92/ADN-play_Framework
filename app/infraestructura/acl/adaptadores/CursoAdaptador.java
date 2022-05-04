package infraestructura.acl.adaptadores;

import com.fasterxml.jackson.databind.JsonNode;
import dominio.modelo.Comparendo;
import dominio.modelo.Curso;
import dominio.modelo.validadores.CursoValidador;
import infraestructura.acl.dto.ComparendoDTO;
import infraestructura.acl.dto.CursoDTO;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Either;
import io.vavr.control.Try;
import play.libs.Json;

import javax.inject.Inject;

public class CursoAdaptador {

    private final CursoValidador cursoValidador;

    @Inject
    public CursoAdaptador(CursoValidador cursoValidador) {
        this.cursoValidador = cursoValidador;
    }


    public Either<Seq<String>, CursoDTO> transformar(JsonNode json) {
        return validar(json).flatMap(this::validar);
    }

    public Curso transformar(CursoDTO dto) {
        return new Curso(
                dto.getId(),
                dto.getIdentificacionInfractor(),
                dto.getNumeroComparendo(),
                dto.getFechaAsistencia());
    }

    public CursoDTO transformar(Curso curso) {
        return new CursoDTO(curso);
    }

    private Either<Seq<String>, CursoDTO> validar(CursoDTO dto) {
        return cursoValidador.validarAsistenciaCurso(
                        dto.getIdentificacionInfractor(),
                        dto.getNumeroComparendo(),
                        dto.getFechaAsistencia())
                .toEither();
    }

    private Either<Seq<String>, CursoDTO> validar(JsonNode json) {
        Either<Seq<String>, CursoDTO> cursoDTO = Try.of(() -> Json.fromJson(json, CursoDTO.class))
                .toEither()
                .mapLeft(error -> {
                    String err = error.toString();
                    return List.of("Json invalido " + error.toString());
                });
        return cursoDTO;
    }
}
