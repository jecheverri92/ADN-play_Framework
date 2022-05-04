package dominio.comandos;

import com.fasterxml.jackson.databind.JsonNode;
import dominio.respuestas.ErrorValidacion;
import dominio.servicios.ServicioPagarComparendo;
import infraestructura.acl.DTO;
import infraestructura.acl.adaptadores.PagoComparendoAdaptador;
import infraestructura.acl.dto.ComparendoDTO;
import infraestructura.acl.dto.PagoComparendoDTO;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;

import javax.inject.Inject;
import java.math.BigDecimal;

public class PagarComparendo implements Comando{

    private final PagoComparendoAdaptador pagoComparendoAdaptador;
    private final ServicioPagarComparendo servicioPagarComparendo;

    @Inject
    public PagarComparendo(PagoComparendoAdaptador pagoComparendoAdaptador, ServicioPagarComparendo servicioPagarComparendo) {
        this.pagoComparendoAdaptador = pagoComparendoAdaptador;
        this.servicioPagarComparendo = servicioPagarComparendo;
    }


    @Override
    public Future<Consecuencia> ejecutar(JsonNode json) {
        return pagoComparendoAdaptador.transformar(json).fold(
                e -> Future.successful(obtenerConsecuenciaFallida("Errores en los datos.")),
                this::pagarComparendo)
                ;
    }

    private Future<Consecuencia> pagarComparendo(PagoComparendoDTO pagoComparendoDTO){
        return (servicioPagarComparendo.pagarComparendo(pagoComparendoDTO))
                .map(either -> either.fold(
                        e -> obtenerConsecuenciaFallida(e.getMensaje()),
                        this::obtenerConsecuenciaExitosa

                ));
    }

    private Consecuencia obtenerConsecuenciaFallida(String mensaje) {
        ErrorValidacion error = new ErrorValidacion(mensaje);
        return new Consecuencia(Either.left(error));
    }

    private Consecuencia obtenerConsecuenciaExitosa(Long id) {
        return new Consecuencia(Either.right(new PagoComparendoDTO("111", BigDecimal.TEN)));
    }
}
