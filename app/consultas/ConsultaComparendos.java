package consultas;

import dominio.modelo.Comparendo;
import dominio.servicios.ServicioComparendo;
import io.vavr.concurrent.Future;

import javax.inject.Inject;
import java.util.List;


public class ConsultaComparendos {

    private final ServicioComparendo servicioComparendo;

    @Inject
    public ConsultaComparendos(ServicioComparendo servicioComparendo) {
        this.servicioComparendo = servicioComparendo;
    }

    public Future<List<Comparendo>> obtenerComparendosPorInfractor(String idenficacionInfractor) {
        return servicioComparendo.obtenerComparendosPorInfractor(idenficacionInfractor);
    }
}
