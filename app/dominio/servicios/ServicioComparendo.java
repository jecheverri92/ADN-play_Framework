package dominio.servicios;

import dominio.modelo.Comparendo;
import dominio.repositorio.ComparendoRepositorio;
import dominio.respuestas.Error;
import dominio.respuestas.ErrorDominio;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;
import util.FutureE;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

public class ServicioComparendo {

    private final ComparendoRepositorio repositorio;

    @Inject
    public ServicioComparendo(ComparendoRepositorio comparendoRepositorio) {
        this.repositorio = comparendoRepositorio;
    }

    public Future<Either<Error, Long>> registrarComparendo(Comparendo comparendo) {
        return this.consultarComparendo(comparendo.getNumeroComparendo()).flatMap(
            either -> either.fold(
                    error -> Future.successful(Either.left(error)),
                    comparendoNoExiste -> this.consultarTarifa(comparendo.getTipoInfraccion()).flatMap(
                ei -> ei.fold(
                        error -> Future.successful(Either.left(error)),
                        valorComparendo -> repositorio.insertar(comparendo).map(Either::right)

        ))));
    }

    private Future<Either<Error, BigDecimal>> consultarTarifa(Integer tipoInfaccion) {
        return repositorio.consultarTarifaInfraccion(tipoInfaccion)
                .map(option -> option.toEither(new ErrorDominio("No existe la Tarifa", io.vavr.collection.List.of("tarifa.noexiste"))));
    }

    public Future<Either<Error, Boolean>> consultarComparendo(String numeroComparendo) {
        return (repositorio.consultarComparendo(numeroComparendo))
                .map(cont -> cont==0 ? Either.right(true) : Either.left(new ErrorDominio("El comparendo ya existe", "comparendo.numeroComparendo")));
    }


    public Future<List<Comparendo>> obtenerComparendosPorInfractor(String identificacionInfractor) {
        return repositorio.obtenerComparendosPorInfractor(identificacionInfractor);
    }

}
