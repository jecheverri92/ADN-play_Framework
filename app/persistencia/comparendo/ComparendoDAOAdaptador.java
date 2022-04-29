package persistencia.comparendo;

import dominio.modelo.Comparendo;
import io.vavr.control.Try;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ComparendoDAOAdaptador {


    private static final ZoneId BOGOTA = ZoneId.of("America/Bogota");

    public static ComparendoRecord transformar(Comparendo comparendo){
        return new ComparendoRecord(
                comparendo.getId(),
                comparendo.getNumeroComparendo(),
                comparendo.getTipoInfraccion(),
                comparendo.getIdentificacionInfractor(),
                comparendo.getFechaComparendo(),
                comparendo.getValorComparendo()
        );
    }

    public static Comparendo transformar(ComparendoRecord record) {
        return new Comparendo(

                record.getIdComparendo(),
                record.getNumeroComparendo(),
                record.getTipoInfraccion(),
                record.getIdentificacionInfractor(),
                record.getFechaComparendo(),
                record.getValorComparendo()
        );
    }

}
