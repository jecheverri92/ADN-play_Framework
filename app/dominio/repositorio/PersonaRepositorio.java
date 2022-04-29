package dominio.repositorio;

import dominio.modelo.Persona;
import io.vavr.concurrent.Future;
import io.vavr.control.Option;
import org.skife.jdbi.v2.DBI;
import persistencia.persona.PersonaDAO;
import persistencia.persona.PersonaDAOAdaptador;
import persistencia.persona.PersonaRecord;
import play.db.Database;

import javax.inject.Inject;
import java.util.Collection;

public class PersonaRepositorio {

    private Database db;

    @Inject
    public PersonaRepositorio(Database db) {
        this.db = db;
    }

    public Future<Collection<Persona>> listar() {
        return Future.of(() -> new DBI(db.getDataSource()).onDemand(PersonaDAO.class).listar());
    }

    public Future<Option<Persona>> buscarId(String id) {
        return Future.of(() -> Option.of(new DBI(db.getDataSource()).onDemand(PersonaDAO.class).buscarId(id)));
    }

    public Future<Option<Persona>> buscar(String id) {
        return Future.of(() ->
                Option.of(new DBI(db.getDataSource()).onDemand(PersonaDAO.class).buscarId(id))
        );
    }

    public Future<Persona> insertar(Persona persona) {
        return Future.of(() -> {
                    PersonaRecord record = PersonaDAOAdaptador.transformar(persona);
                    Long idGenerado = new DBI(db.getDataSource()).onDemand(PersonaDAO.class).insertar(
                            record.getNombre());
                    persona.setId(idGenerado);
                    return persona;
                }
        );
    }
}
