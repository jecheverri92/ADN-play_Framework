package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import consultas.ConsultaComparendos;
import dominio.comandos.PagarComparendo;
import dominio.comandos.RegistrarComparendo;
import dominio.respuestas.ErrorDominio;
import io.vavr.collection.List;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class ComparendoController extends Controller implements IControlador{

    private final RegistrarComparendo registrarComparendo;
    private final PagarComparendo pagarComparendo;
    private final ConsultaComparendos consultaComparendos;

    @Inject
    public ComparendoController(RegistrarComparendo registrarComparendo, PagarComparendo pagarComparendo, ConsultaComparendos consultaComparendos) {
        this.registrarComparendo = registrarComparendo;
        this.pagarComparendo = pagarComparendo;
        this.consultaComparendos = consultaComparendos;
    }

    public CompletionStage<Result> registrarComparendo(final Http.Request request) {
        JsonNode json = request.body().asJson();
        return ejecutar(registrarComparendo,json);

    }

    public CompletionStage<Result> pagarComparendo(final Http.Request request) {
        JsonNode json = request.body().asJson();
        return ejecutar(pagarComparendo,json);

    }

    public CompletionStage<Result> consultarComparendosPorInfractor(String identificacionInfractor) {
        return consultaComparendos.obtenerComparendosPorInfractor(identificacionInfractor).map(
                res -> ok(Json.toJson(res))
        ).recover(this::respuestaRecover).toCompletableFuture();
    }


    private Result respuestaRecover(Throwable e) {
        return internalServerError(Json.toJson(new ErrorDominio("Error interno", List.empty())));
    }
}
