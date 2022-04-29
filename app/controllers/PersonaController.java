package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dominio.comandos.manejadores.ManejadorRegistrarpersona;
import dominio.modelo.Persona;
import persistencia.persona.PersonaRepositorio;
import play.libs.concurrent.HttpExecutionContext;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Http.Request;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static play.libs.Json.toJson;

public class PersonaController extends Controller implements IControlador{

    private final FormFactory formFactory;
    private final PersonaRepositorio personRepository;
    private final ManejadorRegistrarpersona manejadorRegistrarpersona;
    private final HttpExecutionContext ec;

    @Inject
    public PersonaController(FormFactory formFactory, PersonaRepositorio personRepository, ManejadorRegistrarpersona manejadorRegistrarpersona, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.personRepository = personRepository;
        this.manejadorRegistrarpersona = manejadorRegistrarpersona;
        this.ec = ec;
    }

    public CompletionStage<Result> getPersonas() {
        return personRepository.list().thenApplyAsync(personStream -> {
            return ok(toJson(personStream.collect(Collectors.toList())));
        }, ec.current());

    }

    public CompletionStage<Result> addPersona(final Request request) {
        JsonNode json = request.body().asJson();
        return ejecutar(manejadorRegistrarpersona,json);

    }

    public CompletionStage<Result> update(String id, final Http.Request request) {
        Persona persona = formFactory.form(Persona.class).bindFromRequest(request).get();
        return personRepository.update(Long.valueOf(id), persona).thenApplyAsync(optionalResource -> {
            return optionalResource.map(r ->
                    ok(toJson(r))
            ).orElseGet(() ->
                    notFound()
            );
        }, ec.current());
    }

    public CompletionStage<Result> delete(String id) {
        return personRepository.delete(Long.valueOf(id)).thenApplyAsync(optionalResource -> {
            return optionalResource.map(r ->
                    ok(toJson(r))
            ).orElseGet(() ->
                    notFound()
            );
        }, ec.current());
    }




}
