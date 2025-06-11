package Gestora;

import Interfaces.IGestoras;
import Modelo.Habitacion;
import Modelo.Ocupacion;
import Modelo.Reserva;
import Enum.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GestorOcupacion implements IGestoras<Ocupacion> {

    private Set<Ocupacion> listaOcupaciones;

    public GestorOcupacion() {
        this.listaOcupaciones = new HashSet<>();

    }

    @Override
    public String mostrarLista() {
        StringBuilder sb = new StringBuilder();
        for (Ocupacion ocupacion : listaOcupaciones) {
            sb = sb.append(ocupacion).append("\n");
        }
        return sb.toString();
    }

    @Override
    public void agregar(Ocupacion elemento) {
        listaOcupaciones.add(elemento);
    }

    @Override
    public void eliminar(Ocupacion elemento) {
        listaOcupaciones.remove(elemento);
    }

    public void realizarCheckIn(Reserva reserva) {
        // Validaciones
        if (reserva == null) {
            throw new IllegalArgumentException("La reserva no puede ser nula");
        }

        if (reserva.getEstadoReserva() != EstadoReserva.CONFIRMADA) {
            throw new IllegalStateException("Solo se puede hacer check-in de reservas confirmadas. " +
                    "Estado actual: " + reserva.getEstadoReserva());
        }

        LocalDate hoy = LocalDate.now();

        // Verificar que el check-in se realiza en la fecha correcta
        if (hoy.isBefore(reserva.getFechaInicio())) {
            throw new IllegalStateException("No se puede realizar check-in antes de la fecha de inicio. " +
                    "Fecha de inicio: " + reserva.getFechaInicio());
        }

        if (hoy.isAfter(reserva.getFechaInicio().plusDays(1))) {
            throw new IllegalStateException("La reserva ha expirado. Fecha límite de check-in superada");
        }

        // Verificar que la habitación esté disponible
        Habitacion habitacion = reserva.getHabitacion();
        if (habitacion.getEstadoHabitacion() != EstadoHabitacion.DISPONIBLE) {
            throw new IllegalStateException("La habitación no está disponible para check-in. " +
                    "Estado actual: " + habitacion.getEstadoHabitacion());
        }

        try {
            // Crear la ocupación
            Ocupacion ocupacion = new Ocupacion();
                  ocupacion.setHabitacion(habitacion);
                  ocupacion.setPasajero(reserva.getPasajero());
                  ocupacion.setFechaCheckIn(reserva.getFechaInicio().atStartOfDay());
                  ocupacion.setFechaCheckOut(reserva.getFechaFin().atStartOfDay());
                  ocupacion.setEstadoOcupacion(EstadoOcupacion.ACTIVA);
                  //AGREGAR TARIFA
           // ocupacion.getPasajero().//AGREGAR OCUPACION AL HISTORIAL DEL PASAJERO
                  agregar(ocupacion);


        } catch (Exception e) {
            throw new RuntimeException("Error al realizar el check-in: " + e.getMessage(), e);// HACER EXCEPCION PERSONALIZADA
        }
    }
    public Set<Ocupacion> obtenerOcupaciones() {
        return listaOcupaciones;
    }
    public void realizarCheckOut(Ocupacion ocupacion)
    {
        ocupacion.getHabitacion().setEstadoHabitacion(EstadoHabitacion.DISPONIBLE);
        eliminar(ocupacion);
    }


}
