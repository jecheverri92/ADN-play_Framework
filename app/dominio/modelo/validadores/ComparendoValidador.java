package dominio.modelo.validadores;

import dominio.modelo.Comparendo;
import infraestructura.acl.dto.ComparendoDTO;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ComparendoValidador {

    private static final ZoneId BOGOTA = ZoneId.of("America/Bogota");


    private static final String MENSAJE_NUMERO_COMPARENDO_OBLIGATORIO = "El numero del comparendo es obligatorio";
    private static final String MENSAJE_IDENTIFICACION_INFRACTOR_OBLIGATORIA = "La identificacion del infractor es obligatoria";
    private static final String MENSAJE_TIPO_INFRACCION_OBLIGATORIO = "El tipo de infraccion cometida es obligatorio";
    private static final String MENSAJE_FECHA_COMPARENDO_OBLIGATORIA = "La fecha del comparendo es obligatoria";

    private static final String NUMERICO = "^[0-9 ]*$";

    public Validation<Seq<String>, ComparendoDTO> validarComparendo(String numeroComparendo,
                                                                    Integer tipoInfraccion,
                                                                    String identificacionInfractor,
                                                                    LocalDateTime fechaComparendo,
                                                                    BigDecimal valorComparendo) {
        return Validation.combine(
                validarNumeroComparendo(numeroComparendo),
                validarTipoInfraccion(tipoInfraccion),
                validarIdInfractor(identificacionInfractor),
                validarFechaComparendo(fechaComparendo),
                validarValorComparendo(valorComparendo)
        ).ap(ComparendoDTO::new);

    }

    private Validation<String, String> validarNumeroComparendo(String numeroComparendo) {
        return StringUtils.isNotBlank(numeroComparendo) ? Validation.valid(numeroComparendo) : Validation.invalid("comparendo.numeroComparendo");
    }

    private Validation<String, String> validarIdInfractor(String identificacionInfractor) {
        return StringUtils.isNotBlank(identificacionInfractor) ? Validation.valid(identificacionInfractor) : Validation.invalid("comparendo.numeroComparendo");
    }

    private Validation<String, Integer>  validarTipoInfraccion(Integer tipoInfraccion) {
        return tipoInfraccion != null && tipoInfraccion > 0  ? Validation.valid(tipoInfraccion) : Validation.invalid("comparendo.tipoInfraccion");
    }

    private Validation<String, BigDecimal>  validarValorComparendo(BigDecimal valorComparendo) {
        return valorComparendo != null && valorComparendo.compareTo(BigDecimal.ZERO) > 0
                ? Validation.valid(valorComparendo) : Validation.invalid("comparendo.valorInfraccion");
    }

    private Validation<String, LocalDateTime> validarFechaComparendo(LocalDateTime fechaComparendo) {
        return fechaComparendo != null && fechaComparendo.atZone(BOGOTA).isAfter(ZonedDateTime.now(BOGOTA)) ? Validation.valid(fechaComparendo) : Validation.invalid("comparendo.fechaComparendo");
    }
}
