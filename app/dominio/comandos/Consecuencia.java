package dominio.comandos;

import infraestructura.acl.DTO;
import io.vavr.control.Either;
import dominio.respuestas.Error;

public class Consecuencia {

    private final Either<Error, DTO> respuesta;

    public Consecuencia(Either<Error, DTO> respuesta) {
        this.respuesta = respuesta;
    }


    public Either<Error, DTO> getRespuesta() {
        return respuesta;
    }
}
