package persistencia.persona;

import dominio.modelo.Persona;

public class PersonaDAOAdaptador {
    public static PersonaRecord transformar(Persona persona) {
        return new PersonaRecord(
                persona.getNombre());
    }
}
