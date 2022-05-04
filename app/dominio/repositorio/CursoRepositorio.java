package dominio.repositorio;

import dominio.modelo.Curso;
import io.vavr.concurrent.Future;
import org.skife.jdbi.v2.DBI;
import persistencia.curso.CursoDAO;
import persistencia.curso.CursoDAOAdaptador;
import persistencia.curso.CursoRecord;
import play.db.Database;

import javax.inject.Inject;

public class CursoRepositorio {

    private Database db;

    @Inject
    public CursoRepositorio(Database db) {
        this.db = db;
    }

    public Future<Curso> insertar(Curso curso) {
        return Future.of(() -> {
                    CursoRecord record = CursoDAOAdaptador.transformar(curso);
                    Long idCreado = new DBI(db.getDataSource()).onDemand(CursoDAO.class).insertar(
                            record.getIdentificacionInfractor(),
                            record.getNumeroComparendo(),
                            record.getFechaAsistencia());
                    curso.setIdAsistenciaCurso(idCreado);
                    return curso;
                }
        );
    }
}
