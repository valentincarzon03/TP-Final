package Gestora;

import Modelo.Reserva;

import java.util.ArrayList;
import java.util.List;

public class GestorReserva {

    private List<Reserva> listaReservas;

    public GestorReserva() {

        this.listaReservas= new ArrayList<>();

    }
    public List<Reserva> obtenerReservas() {
        return listaReservas;
    }
    public boolean agregarReserva(Reserva reserva) {//Verificar Disponibilidad

        return listaReservas.add(reserva);
    }
    public boolean cancelarReserva(Reserva reserva) {
        return listaReservas.remove(reserva);



    }


}
