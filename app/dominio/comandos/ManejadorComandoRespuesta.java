package dominio.comandos;

import javax.transaction.Transactional;

public interface ManejadorComandoRespuesta<C, R> {

    @Transactional
    R ejecutar(C comando);

}