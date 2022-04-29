package dominio.comandos;

import com.fasterxml.jackson.databind.JsonNode;
import dominio.respuestas.ErrorValidacion;
import dominio.servicios.ServicioComparendo;
import infraestructura.acl.adaptadores.ComparendoAdaptador;
import infraestructura.acl.dto.ComparendoDTO;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;
import play.Logger;
import util.FutureE;

import javax.inject.Inject;

public class RegistrarComparendo implements Comando{

    private final Logger.ALogger logger = Logger.of(this.getClass());

    private final ComparendoAdaptador comparendoAdaptador;
    private final ServicioComparendo servicioComparendo;

    @Inject
    public RegistrarComparendo(ComparendoAdaptador comparendoAdaptador, ServicioComparendo servicioComparendo) {
        this.comparendoAdaptador = comparendoAdaptador;
        this.servicioComparendo = servicioComparendo;
    }

    @Override
    public Future<Consecuencia> ejecutar(JsonNode json) {
        return comparendoAdaptador.transformar(json).fold(
                e -> Future.successful(obtenerConsecuenciaFallida("Errores en los datos.")),
                this::registrarComparendo)
                ;
    }

    private Future<Consecuencia> registrarComparendo(ComparendoDTO comparendoDTO){
       return (servicioComparendo.registrarComparendo(comparendoAdaptador.transformar(comparendoDTO)))
               .map(either -> either.fold(
                       e -> obtenerConsecuenciaFallida(e.getMensaje()),
                       this::obtenerConsecuenciaExitosa)
               );
    }

    private Consecuencia obtenerConsecuenciaFallida(String mensaje) {
        ErrorValidacion error = new ErrorValidacion(mensaje);
        return new Consecuencia(Either.left(error));
    }

    private Consecuencia obtenerConsecuenciaExitosa(Long idComparendo) {
        return new Consecuencia(Either.right(comparendoAdaptador.transformar(idComparendo)));
    }
}
