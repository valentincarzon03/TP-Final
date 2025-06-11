package Gestora;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestoraGenerica <K,V> {
    private Map<K,V> lista;

    public GestoraGenerica() {
        this.lista = new HashMap();
    }

    public Map<K,V> obtenerLista() {
        return lista;
    }

    public void agregar(K clave, V valor) {
        lista.put(clave,valor);
    }

    public void eliminar(K clave) {
       lista.remove(clave);
    }



    public V obtenerElemento(K clave) {
        return lista.get(clave);

    }

    public int obtenerTamanio() {
        return lista.size();
    }



    public boolean contiene(K elemento) {
        return lista.containsKey(elemento);
    }

}
