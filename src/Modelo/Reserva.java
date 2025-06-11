package Modelo;
import Enum.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;

public class Reserva {
    private String fechaInicio;
    private String fechaFin;
    private Pasajero pasajero;
    private EstadoReserva estadoReserva;
    private Habitacion habitacion;
    private int nroReserva;
    private static int contadorReservas = 1;

    public Reserva(String fechaInicio, String fechaFin, Pasajero pasajero, EstadoReserva estadoReserva , Habitacion habitacion) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.pasajero = pasajero;
        this.estadoReserva = estadoReserva;
        this.habitacion = habitacion;
        this.nroReserva = contadorReservas;
        contadorReservas++;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
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

    public int getNroReserva() {
        return nroReserva;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return nroReserva == reserva.nroReserva;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nroReserva);
    }

    @Override
    public String toString() {
        return "Reserva{" + "nroReserva=" + nroReserva +
                "fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", pasajero=" + pasajero +
                ", estadoReserva=" + estadoReserva +
                ", habitacion=" + habitacion +
                '}';
    }
}
