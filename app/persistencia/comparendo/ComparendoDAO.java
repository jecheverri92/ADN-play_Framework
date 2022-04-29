package persistencia.comparendo;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@RegisterMapper(ComparendoMapper.class)
public interface ComparendoDAO {

    @SqlUpdate("insert into maestro_comparendo (\n" +
            "    numero_comparendo,\n" +
            "    tipo_infraccion,\n" +
            "    identificacion_infractor,\n" +
            "    fecha_comparendo,\n" +
            "    valor_comparendo)" +
            "    values (:numeroComparendo, :tipoInfraccion, :identificacionInfractor,:fechaComparendo, :valorComparendo)")
    @GetGeneratedKeys
    Long insertar(
            @Bind("numeroComparendo") String numeroComparendo,
            @Bind("tipoInfraccion") Integer tipoInfraccion,
            @Bind("identificacionInfractor") String identificacionInfractor,
            @Bind("fechaComparendo") LocalDateTime fechaComparendo,
            @Bind("valorComparendo") BigDecimal valorComparendo
    );
}
