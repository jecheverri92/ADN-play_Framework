package dominio.modelo.validadores;

import dominio.modelo.Comparendo;
import fabrica.dominio.ComparendoFabrica;
import infraestructura.acl.dto.ComparendoDTO;
import io.vavr.collection.Seq;
import io.vavr.control.Either;
import io.vavr.control.Validation;
import org.junit.Test;
import play.libs.Json;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertTrue;

public class ComparendoValidadorTest {

    private static final ZoneId BOGOTA = ZoneId.of("America/Bogota");

    @Test
    public void testComparendoValido() {
        Comparendo c = new ComparendoFabrica().getComparendo();
        System.out.println("COMPARENDO: " + Json.prettyPrint(Json.toJson(c)));
        Validation<Seq<String>, ComparendoDTO> comparendoValidado = new ComparendoValidador().validarComparendo(
              c.getNumeroComparendo(),
              c.getTipoInfraccion(),
              c.getIdentificacionInfractor(),
              c.getFechaComparendo(),
              c.getValorComparendo()
        );
        assertTrue("La validacion de un Comparendo con todos sus atributos correctos debe ser un comparendo valido.",  comparendoValidado.isValid());
    }

    @Test
    public void testComparendoInvalidoValidoFechaPosteriorALaActual() {
        Comparendo c = new ComparendoFabrica().getComparendo();
        c.setFechaComparendo(LocalDateTime.from(ZonedDateTime.now(BOGOTA).plusDays(1)));
        System.out.println("COMPARENDO: " + Json.prettyPrint(Json.toJson(c)));
        Validation<Seq<String>, ComparendoDTO> comparendoValidado = new ComparendoValidador().validarComparendo(
                c.getNumeroComparendo(),
                c.getTipoInfraccion(),
                c.getIdentificacionInfractor(),
                c.getFechaComparendo(),
                c.getValorComparendo()
        );
        assertTrue("La validacion de un Comparendo con fecha posterior a la actual no es un comparendo valido.",  comparendoValidado.isInvalid());
    }

}
