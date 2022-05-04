package persistencia.curso;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@RegisterMapper(CursoMapper.class)
public interface CursoDAO {

    @SqlUpdate("INSERT INTO asistencia_curso(identificacion_infractor, numero_comparendo,fecha_asistencia) " +
            "values (:identificacionInfractor,:numeroComparendo, :fechaAsistencia)")
    @GetGeneratedKeys
    Long insertar(
            @Bind("identificacionInfractor") String identificacionInfractor,
            @Bind("numeroComparendo") String numeroComparendo,
            @Bind("fechaAsistencia") LocalDateTime fechaAsistencia
    );


    @SqlQuery("select count(1) from asistencia_curso\n" +
            "where numero_comparendo = :numeroComparendo")
    Integer existePorNumeroComparendo( @Bind("numeroComparendo") String numeroComparendo);


    @SqlQuery("SELECT COUNT(fecha_asistencia) FROM asistencia_curso " +
            "WHERE identificacion_infractor = :identificacionInfractor " +
            "AND fecha_asistencia = :fechaAsistencia")
    Integer numeroAsistenciasInfractorPorFecha(
            @Bind("identificacionInfractor") String identificacionInfractor,
            @Bind("fechaAsistencia") String fechaAsistencia
    );

}
