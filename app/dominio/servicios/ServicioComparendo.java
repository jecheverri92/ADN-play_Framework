package dominio.servicios;

import dominio.modelo.Comparendo;
import dominio.repositorio.ComparendoRepositorio;
import dominio.respuestas.Error;
import dominio.respuestas.ErrorValidacion;
import io.vavr.collection.List;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;

import javax.inject.Inject;
import java.math.BigDecimal;

public class ServicioComparendo {

    private final ComparendoRepositorio repositorio;

    @Inject
    public ServicioComparendo(ComparendoRepositorio comparendoRepositorio) {
        this.repositorio = comparendoRepositorio;
    }

    public Future<Either<Error, Long>> registrarComparendo(Comparendo comparendo) {
        return this.consultarTarifa(comparendo.getTipoInfraccion()).flatMap(
                either -> either.fold(
                        error -> Future.successful(Either.left(error)),
                        valorComparendo -> repositorio.insertar(comparendo).map(Either::right)
        ));
    }



    private Future<Either<Error, BigDecimal>> consultarTarifa(Integer tipoInfaccion) {
        return repositorio.consultarTarifaInfraccion(tipoInfaccion)
                .map(option -> option.toEither(new ErrorValidacion("No existe la Tarifa", List.of("tarifa.noexiste"))));
    }

}
