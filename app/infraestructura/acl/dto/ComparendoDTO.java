package infraestructura.acl.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dominio.modelo.Comparendo;
import infraestructura.acl.DTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ComparendoDTO implements DTO {

    private Long id;
    private String numeroComparendo;
    private Integer tipoInfraccion;
    private String identificacionInfractor;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaComparendo;
    private BigDecimal valorComparendo;

    public ComparendoDTO(String numeroComparendo, Integer tipoInfraccion, String identificacionInfractor, LocalDateTime fechaComparendo, BigDecimal valorComparendo) {
        this.numeroComparendo = numeroComparendo;
        this.tipoInfraccion = tipoInfraccion;
        this.identificacionInfractor = identificacionInfractor;
        this.fechaComparendo = fechaComparendo;
        this.valorComparendo = valorComparendo;
    }

    public ComparendoDTO(Comparendo comparendo){

    }

    public ComparendoDTO(Long idComparendo){
        this.id = idComparendo;
    }
}
