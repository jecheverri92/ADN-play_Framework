package infraestructura.acl.dto;

import infraestructura.acl.DTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PagoComparendoDTO implements DTO {

    private String numeroComparendo;
    private BigDecimal valorPagado;
}
