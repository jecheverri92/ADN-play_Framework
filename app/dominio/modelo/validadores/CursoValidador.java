package dominio.modelo.validadores;

import infraestructura.acl.dto.CursoDTO;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class CursoValidador {

    private static final ZoneId BOGOTA = ZoneId.of("America/Bogota");

    public Validation<Seq<String>, CursoDTO> validarAsistenciaCurso(
                                                                    String identificacionInfractor,
                                                                    String numeroComparendo,
                                                                    LocalDateTime fechaAsistencia)
                                                                     {
        return Validation.combine(
                validarIdInfractor(identificacionInfractor),
                validarNumeroComparendo(numeroComparendo),
                validarFechaComparendo(fechaAsistencia)
        ).ap(CursoDTO::new);

    }

    private Validation<String, String> validarNumeroComparendo(String numeroComparendo) {
        return StringUtils.isNotBlank(numeroComparendo) ? Validation.valid(numeroComparendo) : Validation.invalid("comparendo.numeroComparendo");
    }

    private Validation<String, String> validarIdInfractor(String identificacionInfractor) {
        return StringUtils.isNotBlank(identificacionInfractor) ? Validation.valid(identificacionInfractor) : Validation.invalid("comparendo.numeroComparendo");
    }

    private Validation<String, LocalDateTime> validarFechaComparendo(LocalDateTime fechaComparendo) {
        return fechaComparendo != null && fechaComparendo.atZone(BOGOTA).isAfter(ZonedDateTime.now(BOGOTA)) ? Validation.valid(fechaComparendo) : Validation.invalid("comparendo.fechaComparendo");
    }

}
