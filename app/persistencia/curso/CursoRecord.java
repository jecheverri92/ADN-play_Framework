package persistencia.curso;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class CursoRecord {

    private Long idAsistenciaCurso;
    private String numeroComparendo;
    private String identificacionInfractor;
    private LocalDateTime fechaAsistencia;
}
