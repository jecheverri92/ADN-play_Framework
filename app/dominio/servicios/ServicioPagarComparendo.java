package dominio.servicios;

import dominio.repositorio.ComparendoRepositorio;
import dominio.respuestas.Error;
import dominio.respuestas.ErrorDominio;
import infraestructura.acl.dto.PagoComparendoDTO;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;
import persistencia.comparendo.ComparendoRecord;

import javax.inject.Inject;


public class ServicioPagarComparendo {

    private final ComparendoRepositorio comparendoRepositorio;

    @Inject
    public ServicioPagarComparendo(ComparendoRepositorio comparendoRepositorio) {
        this.comparendoRepositorio = comparendoRepositorio;
    }


    public Future<Either<Error, Long>> pagarComparendo(PagoComparendoDTO pagoComparendoDTO) {
        return this.validarExistenciaPrevia(pagoComparendoDTO).flatMap(
                either -> either.fold(
                        error -> Future.successful(Either.left(error)),
                        existe -> this.borrarComparendo(existe.getIdComparendo())));
    }



    private Future<Either<Error, ComparendoRecord>> validarExistenciaPrevia(PagoComparendoDTO pagoComparendo) {
        return comparendoRepositorio.consultarComparendoRecord(pagoComparendo.getNumeroComparendo()).map(
                option -> option.toEither(new ErrorDominio("El comparendo no existe", "comparendo.numeroComparendo"))
        );
    }

    private Future<Either<Error,Long>> borrarComparendo(Long idComparendo){
        comparendoRepositorio.borrarComparendo(idComparendo);
        return Future.of(()->{
                    return true ? Either.right(idComparendo) : Either.left(new ErrorDominio("El comparendo ya existe", "comparendo.numeroComparendo"));
        });
    }


}
