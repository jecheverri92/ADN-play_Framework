package persistencia.comparendo;


import dominio.modelo.Comparendo;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


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


    @SqlQuery("SELECT count(1) from maestro_comparendo\n" +
            "WHERE numero_comparendo = :numeroComparendo")
    Integer obtenerComparendo( @Bind("numeroComparendo") String numeroComparendo);

    @SqlQuery("SELECT * from maestro_comparendo\n" +
            "WHERE numero_comparendo = :numeroComparendo")
    ComparendoRecord obtenerComparendoRecord( @Bind("numeroComparendo") String numeroComparendo);

    @SqlQuery("SELECT * FROM MAESTRO_COMPARENDO WHERE  identificacion_infractor = :identificacionInfractor")
    List<ComparendoRecord> obtenerComparendosPorInfractor(@Bind("identificacionInfractor") String identificacionInfractor);

    @SqlUpdate("DELETE FROM maestro_comparendo WHERE id_comparendo = :idComparendo")
    void borrarComparendo(@Bind("idComparendo") Long idComparendo);
}
