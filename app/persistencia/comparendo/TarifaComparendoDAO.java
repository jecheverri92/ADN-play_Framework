package persistencia.comparendo;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

import java.math.BigDecimal;

public interface TarifaComparendoDAO {

    @SqlQuery("SELECT tt.valor FROM infraccion_tarifa it\n" +
            "INNER JOIN tipo_tarifa tt ON it.id_tipo_tarifa = tt.id_tipo_tarifa\n" +
            "WHERE it.id_tipo_infraccion = :tipoInfraccion")
    BigDecimal obtenerTarifaComparendo(@Bind("tipoInfraccion") Integer tipoInfraccion);

}
