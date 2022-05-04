package dominio.repositorio;

import dominio.modelo.Comparendo;
import io.vavr.concurrent.Future;
import io.vavr.control.Option;
import org.skife.jdbi.v2.DBI;
import persistencia.comparendo.ComparendoDAO;
import persistencia.comparendo.ComparendoDAOAdaptador;
import persistencia.comparendo.ComparendoRecord;
import persistencia.comparendo.TarifaComparendoDAO;
import play.db.Database;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;


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
                           record.getValorComparendo());
                }
        );
    }

    public Future<Option<BigDecimal>> consultarTarifaInfraccion(Integer tipoInfraccion) {
        return Future.of(() ->
                Option.of(new DBI(db.getDataSource()).onDemand(TarifaComparendoDAO.class).obtenerTarifaComparendo(tipoInfraccion))
        );
    }

    public Future<Integer> consultarComparendo(String comparendo) {
        Future<Integer> o =  Future.of(() ->
               new DBI(db.getDataSource()).onDemand(ComparendoDAO.class).obtenerComparendo(comparendo)
        );
        Integer l = o.get();
        return o;
    }

    public Future<Option<ComparendoRecord>> consultarComparendoRecord(String comparendo) {
        Future<Option<ComparendoRecord>> o =  Future.of(() ->
                Option.of(new DBI(db.getDataSource()).onDemand(ComparendoDAO.class).obtenerComparendoRecord(comparendo))
        );
        return o;
    }

    public Future<List<Comparendo>> obtenerComparendosPorInfractor(String idenficacionInfractor) {
        return Future.of(() ->
                io.vavr.collection.List.ofAll(new DBI(db.getDataSource()).onDemand(ComparendoDAO.class).obtenerComparendosPorInfractor(idenficacionInfractor))
                        .map(ComparendoDAOAdaptador::transformar).toJavaList()
        );
    }

    public void borrarComparendo(Long idComparendo){

        new DBI(db.getDataSource()).onDemand(ComparendoDAO.class).borrarComparendo(idComparendo);


    }

}
