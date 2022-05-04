package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dominio.comandos.RegistrarCurso;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class CursoController extends Controller implements IControlador{

    private final RegistrarCurso registrarCurso;

    @Inject
    public CursoController(RegistrarCurso registrarCurso) {
        this.registrarCurso = registrarCurso;
    }

    public CompletionStage<Result> registrarCurso(final Http.Request request) {
        JsonNode json = request.body().asJson();
        return ejecutar(registrarCurso,json);

    }
}
