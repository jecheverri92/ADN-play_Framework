package persistencia.curso;

import dominio.modelo.Comparendo;
import dominio.modelo.Curso;

import java.time.ZoneId;

public class CursoDAOAdaptador {


    private static final ZoneId BOGOTA = ZoneId.of("America/Bogota");

    public static CursoRecord transformar(Curso curso){
        return new CursoRecord(
                curso.getIdAsistenciaCurso(),
                curso.getNumeroComparendo(),
                curso.getIdentificacionInfractor(),
                curso.getFechaAsistencia()
        );
    }

    public static Curso transformar(CursoRecord record) {
        return new Curso(
                record.getIdAsistenciaCurso(),
                record.getIdentificacionInfractor(),
                record.getNumeroComparendo(),
                record.getFechaAsistencia()
        );
    }

}
