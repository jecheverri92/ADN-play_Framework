package it.controladores;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import fabrica.dto.ComparendoDTOFabrica;
import infraestructura.acl.dto.ComparendoDTO;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.modulos.JacksonModulo;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

public class ComparendoControladorTest extends WithApplication {

    private Config config;

    @Override
    protected Application provideApplication() {
        new JacksonModulo();
        config = ConfigFactory.load("application");
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void consultarComparendosPorInfractor() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/RegistrarPagoComparendos/comparendos/idInfractor/1111");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void registrarComparendo() {
        ComparendoDTO dto = new ComparendoDTOFabrica().get();
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .bodyJson(Json.toJson(dto))
                .uri("/RegistrarPagoComparendos/comparendos");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }
}
