package dominio.modelo.validadores;

import infraestructura.acl.dto.PagoComparendoDTO;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class PagoComparendoValidador {

    public Validation<Seq<String>, PagoComparendoDTO> validarPagoComparendo(String numeroComparendo,
                                                                    BigDecimal valorComparendo) {
        return Validation.combine(
                validarNumeroComparendo(numeroComparendo),
                validarValorComparendo(valorComparendo)
        ).ap(PagoComparendoDTO::new);

    }

    private Validation<String, String> validarNumeroComparendo(String numeroComparendo) {
        return StringUtils.isNotBlank(numeroComparendo) ? Validation.valid(numeroComparendo) : Validation.invalid("pagoComparendo.numeroComparendo");
    }

    private Validation<String, BigDecimal>  validarValorComparendo(BigDecimal valorComparendo) {
        return valorComparendo != null && valorComparendo.compareTo(BigDecimal.ZERO) > 0
                ? Validation.valid(valorComparendo) : Validation.invalid("pagoComparendo.valorInfraccion");
    }
}
