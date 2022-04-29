package dominio.respuestas;

public class ErrorTecnico implements  Error{
    private String mensaje;
    private Boolean mostrar = true;

    public ErrorTecnico(String mensaje) {
        this.mensaje = mensaje;
    }

    public ErrorTecnico(String mensaje, Boolean mostrar) {
        this.mensaje = mensaje;
        this.mostrar = mostrar;
    }

    @Override
    public String getMensaje() {
        return mensaje;
    }

    public Boolean getMostrar() {
        return mostrar;
    }
}
