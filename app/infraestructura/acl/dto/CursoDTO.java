package infraestructura.acl.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dominio.modelo.Comparendo;
import dominio.modelo.Curso;
import infraestructura.acl.DTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CursoDTO implements DTO {

    private Long id;
    private String identificacionInfractor;
    private String numeroComparendo;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaAsistencia;

    public CursoDTO(String identificacionInfractor, String numeroComparendo, LocalDateTime fechaAsistencia) {
        this.identificacionInfractor = identificacionInfractor;
        this.numeroComparendo = numeroComparendo;
        this.fechaAsistencia = fechaAsistencia;
    }

    public CursoDTO(Curso curso){
        this.identificacionInfractor = curso.getIdentificacionInfractor();
        this.numeroComparendo = curso.getNumeroComparendo();
        this.fechaAsistencia = curso.getFechaAsistencia();
    }
}
