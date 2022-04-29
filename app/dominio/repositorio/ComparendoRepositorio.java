package dominio.repositorio;

import dominio.modelo.Comparendo;
import dominio.modelo.Persona;
import io.vavr.concurrent.Future;
import io.vavr.control.Option;
import org.skife.jdbi.v2.DBI;
import persistencia.comparendo.ComparendoDAO;
import persistencia.comparendo.ComparendoDAOAdaptador;
import persistencia.comparendo.ComparendoRecord;
import persistencia.comparendo.TarifaComparendoDAO;
import persistencia.persona.PersonaDAO;
import persistencia.persona.PersonaDAOAdaptador;
import persistencia.persona.PersonaRecord;
import play.db.Database;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Collection;

public class ComparendoRepositorio {
    private Database db;

    @Inject
    public ComparendoRepositorio(Database db) {
        this.db = db;
    }



    public Future<Long> insertar(Comparendo comparendo) {
        return Future.of(() -> {
                    ComparendoRecord record = ComparendoDAOAdaptador.transformar(comparendo);
                   return new DBI(db.getDataSource()).onDemand(ComparendoDAO.class).insertar(
                            record.getNumeroComparendo(),
                            record.getTipoInfraccion(),
                           record.getIdentificacionInfractor(),
                           record.getFechaComparendo(),
                           BigDecimal.ONE);
                }
        );
    }

    public Future<Option<BigDecimal>> consultarTarifaInfraccion(Integer tipoInfraccion) {
        return Future.of(() ->
                Option.of(new DBI(db.getDataSource()).onDemand(TarifaComparendoDAO.class).obtenerTarifaComparendo(tipoInfraccion))
        );
    }

}
