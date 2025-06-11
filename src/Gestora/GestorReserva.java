package Gestora;

import Interfaces.IGestoras;
import Modelo.Reserva;

import java.util.ArrayList;
import java.util.List;

public class GestorReserva implements IGestoras<Reserva> {

    private List<Reserva> listaReservas;

    public GestorReserva() {

        this.listaReservas= new ArrayList<>();

    }

    public void realizarReserva(Reserva reserva) {//VERIFICAR DISPONIBILIDAD

    }
    @Override
    public String mostrarLista() {
        StringBuilder sb = new StringBuilder();
        for (Reserva reserva : listaReservas) {
            sb = sb.append(reserva).append("\n");
        }
        return sb.toString();

    }

    @Override
    public void agregar(Reserva elemento) {
        listaReservas.add(elemento);

    }

    @Override
    public void eliminar(Reserva elemento) {
        listaReservas.remove(elemento);
    }
}
