package Gestora;
import Enum.*;
import Interfaces.IGestoras;
import Modelo.Habitacion;
import Modelo.Ocupacion;
import Modelo.Reserva;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GestorHabitaciones implements IGestoras<Habitacion> {

    private List<Habitacion> habitaciones;

    public GestorHabitaciones() {
        this.habitaciones = new ArrayList<>();
    }
    @Override
    public String mostrarLista(){
        StringBuilder sb = new StringBuilder();

        for(Habitacion habitacion : habitaciones){
            sb = sb.append(habitacion).append("\n");
        }
        return sb.toString();
    }

    @Override
    public void eliminar(Habitacion habitacion) {
        habitaciones.remove(habitacion);

    }

    @Override
    public void agregar(Habitacion habitacion) {
        habitaciones.add(habitacion);

    }

    public List<Habitacion> obtenerHabitacionesDisponibles() {
        List<Habitacion> habitacionesDisponibles = new ArrayList<>();
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getEstadoHabitacion() == EstadoHabitacion.DISPONIBLE) {
                habitacionesDisponibles.add(habitacion);
            }
        }
        return habitacionesDisponibles;

    }


        public void cambiarEstado(Habitacion habitacion, EstadoHabitacion estado) {

            habitacion.setEstadoHabitacion(estado);

    }
}
