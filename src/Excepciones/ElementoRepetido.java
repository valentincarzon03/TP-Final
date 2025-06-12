package Excepciones;

import org.w3c.dom.ls.LSOutput;

public class ElementoRepetido extends RuntimeException {
    public ElementoRepetido() {

        System.out.println("ERROR: elemento repetido");
    }
}
