package fabrica.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.javafaker.Faker;
import infraestructura.acl.dto.ComparendoDTO;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ComparendoDTOFabrica {

    private static final ZoneId BOGOTA = ZoneId.of("America/Bogota");
    private static Faker faker = new Faker();

    private Long id;
    private String numeroComparendo;
    private Integer tipoInfraccion;
    private String identificacionInfractor;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaComparendo;
    private BigDecimal valorComparendo;

    public ComparendoDTOFabrica() {
        this.numeroComparendo = String.valueOf(faker.random().nextInt(100));
        this.tipoInfraccion = 1;
        this.identificacionInfractor = String.valueOf(faker.random().nextInt(100));
        this.fechaComparendo = LocalDateTime.from(ZonedDateTime.now(BOGOTA));
        this.valorComparendo = BigDecimal.valueOf(faker.number().numberBetween(1000, 10000));;
    }

    public ComparendoDTO get() {
        return new ComparendoDTO(this.numeroComparendo,
                this.tipoInfraccion,
                this.identificacionInfractor,
                this.fechaComparendo,
                this.valorComparendo);
    }
}
