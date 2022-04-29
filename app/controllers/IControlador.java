package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dominio.comandos.Comando;
import dominio.respuestas.*;
import dominio.respuestas.Error;
import infraestructura.acl.DTO;
import io.vavr.Function1;
import io.vavr.control.Either;
import play.libs.Json;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import static io.vavr.API.*;
import static io.vavr.Predicates.anyOf;
import static io.vavr.Predicates.instanceOf;
import static play.mvc.Results.*;

public interface IControlador {

    default CompletionStage<Result> ejecutar(Comando comando, JsonNode json) {
        return comando.ejecutar(json).map(consecuencia -> {
            return consecuencia.getRespuesta().fold(
                    this::obtenerRespuestaConsecuenciaError,
                    this::obtenerRespuestaConsecuenciaExitosa
            );
        }).recover(recuperarEjecucion()).toCompletableFuture();
    }

    default Function<Throwable, Result> recuperarEjecucion() {
        return t -> obtenerErrorInterno(t, new ErrorDominio("Error interno", CodigosError.ERROR_INTERNO.getCodigo()));
    }

    default Result obtenerRespuestaConsecuenciaError(Error error) {
        return Match(error).of(
                Case($(instanceOf(ErrorValidacion.class)), e -> badRequest(Json.toJson(e))),
                Case($(), e -> internalServerError(Json.toJson(e))));
    }

    default Result obtenerRespuestaConsecuenciaExitosa(DTO dto) {
        return ok(Json.toJson(dto));
    }

    default CompletionStage<Result> ejecutar(Comando comando, JsonNode json, Function1<Either<Error, DTO>, Result> manejarRespuesta) {
        return comando.ejecutar(json).map(consecuencia -> {
                    return manejarRespuesta.apply(consecuencia.getRespuesta());
                }).recover(throwable -> obtenerErrorInterno(throwable, new ErrorTecnico("Ocurrió un error interno procesando la petición")))
                .toCompletableFuture();
    }

    default Result obtenerErrorInterno(Throwable t, Error error) {
        return internalServerError(Json.toJson(error));
    }

    default Result manejarRespuesta(Either<Error, DTO> respuesta) {
        return respuesta.fold(
                error -> {
                    return Match(error).of(
                            Case($(anyOf(instanceOf(ErrorValidacion.class), instanceOf(ErrorDominio.class))), e -> badRequest(Json.toJson(e))),
                            Case($(), e -> internalServerError(Json.toJson(e))));
                },
                this::obtenerRespuestaConsecuenciaExitosa
        );
    }
}
