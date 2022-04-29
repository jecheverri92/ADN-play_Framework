package dominio.modelo.validadores;
import infraestructura.acl.dto.PersonaDTO;
import io.vavr.collection.Seq;
import org.apache.commons.lang3.StringUtils;
import io.vavr.control.Validation;

public class PersonaValidador {

    private static final String NOMBRES = "^[a-zA-Z0-9\\.\\- ]*$";

    public Validation<String, PersonaDTO> validarPersona(String nombre) {
        return validarNombres(nombre).map(PersonaDTO::new);
    }

    public Validation<String, String> validarNombres(String nombres) {
        return !StringUtils.isBlank(nombres) && nombres.matches(NOMBRES) ? Validation.valid(nombres) : Validation.invalid("registro.persona.nombre");
    }
}
