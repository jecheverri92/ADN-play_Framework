package infraestructura.acl.dto;

import dominio.modelo.Persona;
import infraestructura.acl.DTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonaDTO implements DTO {

    private Long id;
    private String nombre;

    public PersonaDTO(Persona persona){
        this.id = persona.getId();
        this.nombre = persona.getNombre();
    }

    public PersonaDTO(String nombre){
        this.nombre = nombre;
    }

}
