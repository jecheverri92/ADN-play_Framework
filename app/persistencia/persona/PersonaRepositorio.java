package persistencia.persona;

import com.google.inject.ImplementedBy;
import dominio.modelo.Persona;
import io.vavr.concurrent.Future;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;


/**
 * This interface provides a non-blocking API for possibly blocking operations.
 */
@ImplementedBy(JPAPersonaRepositorio.class)
public interface PersonaRepositorio  {

    CompletionStage<Stream<Persona>> list();

    Future<Persona> add(Persona persona);

    CompletionStage<Optional<Persona>> update(Long id, Persona persona);

    CompletionStage<Optional<Persona>> delete(Long id);
}
