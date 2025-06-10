package Gestora;
import Enum.*;
import Modelo.Habitacion;
import Modelo.Ocupacion;
import Modelo.Reserva;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GestorHabitaciones {

    private List<Habitacion> habitaciones;

    public GestorHabitaciones() {
        this.habitaciones = new ArrayList<>();
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
