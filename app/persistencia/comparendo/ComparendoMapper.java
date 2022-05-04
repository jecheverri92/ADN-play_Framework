package persistencia.comparendo;

import dominio.modelo.Comparendo;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ComparendoMapper implements ResultSetMapper<ComparendoRecord> {
    @Override
    public ComparendoRecord map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new ComparendoRecord(
                r.getLong("id_comparendo"),
                r.getString("numero_comparendo"),
                r.getInt("tipo_infraccion"),
                r.getString("identificacion_infractor"),
                r.getObject("fecha_comparendo", LocalDateTime.class),
                r.getBigDecimal("valor_comparendo")
        );
    }
}
