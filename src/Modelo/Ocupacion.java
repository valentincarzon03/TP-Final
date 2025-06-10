package Modelo;
import Enum.*;
import java.time.LocalDateTime;
import java.util.Objects;

public class Ocupacion {
    private Habitacion habitacion;
    private Pasajero pasajero;
    private LocalDateTime fechaCheckIn;
    private LocalDateTime fechaCheckOut;
    private double tarifaTotal;
    private EstadoOcupacion estadoOcupacion;

    public Ocupacion(Habitacion habitacion, Pasajero pasajero, LocalDateTime fechaCheckIn, LocalDateTime fechaCheckOut, double tarifaTotal, EstadoOcupacion estadoOcupacion) {
        this.habitacion = habitacion;
        this.pasajero = pasajero;
        this.fechaCheckIn = fechaCheckIn;
        this.fechaCheckOut = fechaCheckOut;
        this.tarifaTotal = tarifaTotal;
        this.estadoOcupacion = estadoOcupacion;
    }

    public Ocupacion() {

    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    public LocalDateTime getFechaCheckIn() {
        return fechaCheckIn;
    }

    public void setFechaCheckIn(LocalDateTime fechaCheckIn) {
        this.fechaCheckIn = fechaCheckIn;
    }

    public LocalDateTime getFechaCheckOut() {
        return fechaCheckOut;
    }

    public void setFechaCheckOut(LocalDateTime fechaCheckOut) {
        this.fechaCheckOut = fechaCheckOut;
    }

    public double getTarifaTotal() {
        return tarifaTotal;
    }

    public void setTarifaTotal(double tarifaTotal) {
        this.tarifaTotal = tarifaTotal;
    }

    public EstadoOcupacion getEstadoOcupacion() {
        return estadoOcupacion;
    }

    public void setEstadoOcupacion(EstadoOcupacion estadoOcupacion) {
        this.estadoOcupacion = estadoOcupacion;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ocupacion ocupacion = (Ocupacion) o;
        return Objects.equals(habitacion, ocupacion.habitacion) && Objects.equals(pasajero, ocupacion.pasajero) && Objects.equals(fechaCheckIn, ocupacion.fechaCheckIn) && Objects.equals(fechaCheckOut, ocupacion.fechaCheckOut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(habitacion, pasajero, fechaCheckIn, fechaCheckOut);
    }

    @Override
    public String toString() {
        return "Ocupacion{" +
                "habitacion=" + habitacion +
                ", pasajero=" + pasajero +
                ", fechaCheckIn=" + fechaCheckIn +
                ", fechaCheckOut=" + fechaCheckOut +
                ", tarifaTotal=" + tarifaTotal +
                ", estadoOcupacion=" + estadoOcupacion +
                '}';
    }
}
