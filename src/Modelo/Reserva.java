package Modelo;
import Enum.*;
import java.time.LocalDate;
import java.util.Objects;

public class Reserva {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Pasajero pasajero;
    private EstadoReserva estadoReserva;
    private Habitacion habitacion;

    public Reserva(LocalDate fechaInicio, LocalDate fechaFin, Pasajero pasajero,EstadoReserva estadoReserva , Habitacion habitacion) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.pasajero = pasajero;
        this.estadoReserva = estadoReserva;
        this.habitacion = habitacion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    public EstadoReserva getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(EstadoReserva estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(fechaInicio, reserva.fechaInicio) && Objects.equals(fechaFin, reserva.fechaFin) && Objects.equals(pasajero, reserva.pasajero) && Objects.equals(habitacion, reserva.habitacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fechaInicio, fechaFin, pasajero, habitacion);
    }
    @Override
    public String toString() {
        return "Reserva{" +
                "fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", pasajero=" + pasajero +
                ", estadoReserva=" + estadoReserva +
                ", habitacion=" + habitacion +
                '}';
    }
}
