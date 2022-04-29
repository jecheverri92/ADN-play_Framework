package dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class Comparendo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroComparendo;
    private Integer tipoInfraccion;
    private String identificacionInfractor;
    private LocalDateTime fechaComparendo;
    @Setter
    private BigDecimal valorComparendo;
}
