package dominio.comandos;

import com.fasterxml.jackson.databind.JsonNode;

import javax.transaction.Transactional;

public interface ManejadorComando<C> {

    @Transactional
    void ejecutar(C comando);
}
