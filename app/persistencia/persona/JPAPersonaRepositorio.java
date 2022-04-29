package persistencia.persona;


import dominio.modelo.Persona;
import io.vavr.concurrent.Future;
import net.jodah.failsafe.CircuitBreaker;
import net.jodah.failsafe.Failsafe;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

/**
 * Provide JPA operations running inside of a thread pool sized to the connection pool
 */
@Singleton
public class JPAPersonaRepositorio implements PersonaRepositorio{


    private final JPAApi jpaApi;
    private final DatabaseExecutionContext executionContext;
    private final CircuitBreaker circuitBreaker = new CircuitBreaker().withFailureThreshold(1).withSuccessThreshold(3);

    @Inject
    public JPAPersonaRepositorio(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    @Override
    public CompletionStage<Stream<Persona>> list() {
        return supplyAsync(() -> wrap(em -> list(em)), executionContext);
    }

    @Override
    public Future<Persona> add(Persona persona) {
        return Future.of(() -> wrap(em -> insert(em, persona)));
    }

    @Override
    public CompletionStage<Optional<Persona>> update(Long id, Persona persona) {
        return supplyAsync(() -> wrap(em -> Failsafe.with(circuitBreaker).get(() -> modify(em, id, persona))), executionContext);
    }

    @Override
    public CompletionStage<Optional<Persona>> delete(Long id) {
        return supplyAsync(() -> wrap(em -> delete(em,id)), executionContext);
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Persona insert(EntityManager em, Persona persona) {
        em.persist(persona);
        return persona;
    }

    private Optional<Persona> delete(EntityManager em, Long id) {
        final Persona data = em.find(Persona.class, id);
        if (data != null) {
            em.remove(data);
        }
        return Optional.ofNullable(data);
    }

    private Stream<Persona> list(EntityManager em) {
        List<Persona> personas = em.createQuery("SELECT p FROM Persona p", Persona.class).getResultList();
        return personas.stream();
    }

    private Optional<Persona> modify(EntityManager em, Long id, Persona persona) throws InterruptedException {
        final Persona data = em.find(Persona.class, id);
        if (data != null) {
            data.setNombre(persona.getNombre());
        }
        Thread.sleep(10000L);
        return Optional.ofNullable(data);
    }


}
