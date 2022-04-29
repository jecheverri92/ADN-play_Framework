package persistencia.persona;

import dominio.modelo.Persona;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Collection;

@RegisterMapper(PersonaMapper.class)
public interface PersonaDAO {

    @SqlQuery("SELECT * FROM persona")
    Collection<Persona> listar();

    @SqlQuery("SELECT * FROM persona WHERE id = :id")
    Persona buscarId(@Bind("id") String id);

    @SqlUpdate("INSERT INTO persona (nombre) VALUES (:nombre)")
    @GetGeneratedKeys
    Long insertar(
                  @Bind("nombre") String nombre);
}
