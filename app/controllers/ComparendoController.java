package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dominio.comandos.RegistrarComparendo;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class ComparendoController extends Controller implements IControlador{

    private final RegistrarComparendo registrarComparendo;

    @Inject
    public ComparendoController(RegistrarComparendo registrarComparendo) {
        this.registrarComparendo = registrarComparendo;
    }

    public CompletionStage<Result> registrarComparendo(final Http.Request request) {
        JsonNode json = request.body().asJson();
        return ejecutar(registrarComparendo,json);

    }
}
