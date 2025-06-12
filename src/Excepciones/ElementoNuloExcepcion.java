package Excepciones;

public class ElementoNuloExcepcion extends RuntimeException {
    public ElementoNuloExcepcion() {
        System.out.println("ERROR: elemento nulo");
    }
}
