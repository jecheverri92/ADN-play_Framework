package dominio.comandos.fabrica;

import dominio.comandos.ComandoPersona;
import dominio.modelo.Persona;

public class FabricaPersona {

    public Persona crear(ComandoPersona comandoPersona){
        return new Persona(comandoPersona.getId(),
                           comandoPersona.getNombre());
    }
}
