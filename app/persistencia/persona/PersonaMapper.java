package persistencia.persona;

import dominio.modelo.Persona;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonaMapper implements ResultSetMapper<Persona> {
    @Override
    public Persona map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Persona(
                r.getLong("id"),
                r.getString("nombre")
        );
    }
}
