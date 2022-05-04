package dominio.comandos;

import com.fasterxml.jackson.databind.JsonNode;
import dominio.modelo.Curso;
import dominio.respuestas.ErrorValidacion;
import dominio.servicios.ServicioCurso;
import infraestructura.acl.adaptadores.CursoAdaptador;
import infraestructura.acl.dto.CursoDTO;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;
import play.Logger;

import javax.inject.Inject;

public class RegistrarCurso implements Comando{
    private final Logger.ALogger logger = Logger.of(this.getClass());

    private final CursoAdaptador cursoAdaptador;
    private final ServicioCurso servicioCurso;


    @Inject
    public RegistrarCurso(CursoAdaptador cursoAdaptador, ServicioCurso servicioCurso) {
        this.cursoAdaptador = cursoAdaptador;
        this.servicioCurso = servicioCurso;
    }

    @Override
    public Future<Consecuencia> ejecutar(JsonNode json) {
        return cursoAdaptador.transformar(json).fold(
                e -> Future.successful(obtenerConsecuenciaFallida("Errores en los datos.")),
                this::registrarCurso)
                ;
    }

    private Future<Consecuencia> registrarCurso(CursoDTO cursoDTO){
        return (servicioCurso.registrarCurso(cursoAdaptador.transformar(cursoDTO)))
                .map(either -> either.fold(
                        e -> obtenerConsecuenciaFallida(e.getMensaje()),
                        this::obtenerConsecuenciaExitosa)
                );
    }

    private Consecuencia obtenerConsecuenciaFallida(String mensaje) {
        ErrorValidacion error = new ErrorValidacion(mensaje);
        return new Consecuencia(Either.left(error));
    }

    private Consecuencia obtenerConsecuenciaExitosa(Curso curso) {
        return new Consecuencia(Either.right(cursoAdaptador.transformar(curso)));
    }
}
