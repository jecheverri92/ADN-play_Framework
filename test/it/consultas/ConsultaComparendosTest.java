package it.consultas;

import dominio.modelo.Comparendo;
import dominio.repositorio.ComparendoRepositorio;
import dominio.servicios.ServicioComparendo;
import it.persistencia.PruebaConBaseDeDatos;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static junit.framework.TestCase.assertEquals;


public class ConsultaComparendosTest extends PruebaConBaseDeDatos {

    private ComparendoRepositorio comparendoRepositorio;
    private ServicioComparendo servicioComparendo;

    @Before
    public void iniciarDatos() {
        comparendoRepositorio = new ComparendoRepositorio(getDb());
        servicioComparendo = new ServicioComparendo(comparendoRepositorio);
    }

    @Test
    public void consultaExitosa() {

        List<Comparendo> comparedosPorInFractor = consultarComparendosPorInfractor();
        assertEquals("No existen comparendos para el infractor",0,comparedosPorInFractor.size());
    }


    private List<Comparendo> consultarComparendosPorInfractor() {
        return comparendoRepositorio.obtenerComparendosPorInfractor("1111").get();
    }
}