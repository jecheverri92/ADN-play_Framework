package dominio.servicios;

import dominio.modelo.Comparendo;
import dominio.modelo.Curso;
import dominio.repositorio.ComparendoRepositorio;
import dominio.repositorio.CursoRepositorio;
import dominio.respuestas.Error;
import dominio.respuestas.ErrorDominio;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;

import javax.inject.Inject;

public class ServicioCurso {

    private final ComparendoRepositorio comparendoRepositorio;
    private final CursoRepositorio cursoRepositorio;

    @Inject
    public ServicioCurso(ComparendoRepositorio comparendoRepositorio, CursoRepositorio cursoRepositorio) {
        this.comparendoRepositorio = comparendoRepositorio;
        this.cursoRepositorio = cursoRepositorio;
    }

    public Future<Either<Error, Curso>> registrarCurso(Curso curso) {
        return this.consultarComparendo(curso.getNumeroComparendo()).flatMap(
                either -> either.fold(
                        error -> Future.successful(Either.left(error)),
                        comparendoNoExiste -> cursoRepositorio.insertar(curso).map(Either::right)));
    }


    private Future<Either<Error, Boolean>> consultarComparendo(String numeroComparendo) {
        return comparendoRepositorio.consultarComparendo(numeroComparendo)
                .map(cont -> cont>0 ? Either.right(true) : Either.left(new ErrorDominio("El comparendo No existe", "comparendo.numeroComparendo")));
    }


}
