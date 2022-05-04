package fabrica.dominio;

import com.github.javafaker.Faker;
import dominio.modelo.Comparendo;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Setter
public class ComparendoFabrica {

    private static final ZoneId BOGOTA = ZoneId.of("America/Bogota");

    private static Faker faker = new Faker();
    private Long idComparendo;
    private String numeroComparendo;
    private Integer tipoInfraccion;
    private String identificacionInfractor;
    private LocalDateTime fechaComparendo;
    private BigDecimal valorComparendo;

    public ComparendoFabrica() {
        this.idComparendo = faker.random().nextLong(100);
        this.numeroComparendo = String.valueOf(faker.random().nextInt(100));
        this.tipoInfraccion = 1;
        this.identificacionInfractor = String.valueOf(faker.random().nextInt(100));
        this.fechaComparendo = LocalDateTime.from(ZonedDateTime.now(BOGOTA));
        this.valorComparendo = BigDecimal.valueOf(faker.number().numberBetween(1000, 10000));;
    }

    public Comparendo getComparendo() {
        return new Comparendo(this.idComparendo, this.numeroComparendo, this.tipoInfraccion, this.identificacionInfractor, this.fechaComparendo, this.valorComparendo);
    }
}
