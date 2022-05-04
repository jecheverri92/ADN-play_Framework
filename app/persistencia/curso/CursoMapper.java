package persistencia.curso;

import dominio.modelo.Comparendo;
import dominio.modelo.Curso;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CursoMapper implements ResultSetMapper<Curso> {
    @Override
    public Curso map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Curso(
                r.getLong("id_asistencia_curso"),
                r.getString("identificacion_infractor"),
                r.getString("numero_comparendo"),
                r.getObject("fecha_asistencia", LocalDateTime.class)
        );
    }
}
