package Modelo;
import Enum.TipoHabitacion;

import java.util.Objects;
import Enum.*;

public class Habitacion {
    private int numero; //POSIBLE NUMERO RANDOM
    private TipoHabitacion tipoHabitacion;
    private double tarifaPorDia;
    private EstadoHabitacion estadoHabitacion;

    public Habitacion(int numero, TipoHabitacion tipoHabitacion, double tarifaPorDia, EstadoHabitacion estadoHabitacion) {
        this.numero = numero;
        this.tipoHabitacion = tipoHabitacion;
        this.tarifaPorDia = tarifaPorDia;
        this.estadoHabitacion = estadoHabitacion;
    }
    public Habitacion() {}

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public TipoHabitacion getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public double getTarifaPorDia() {
        return tarifaPorDia;
    }

    public void setTarifaPorDia(double tarifaPorDia) {
        this.tarifaPorDia = tarifaPorDia;
    }

    public EstadoHabitacion getEstadoHabitacion() {
        return estadoHabitacion;
    }

    public void setEstadoHabitacion(EstadoHabitacion estadoHabitacion) {
        this.estadoHabitacion = estadoHabitacion;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habitacion that = (Habitacion) o;
        return numero == that.numero;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numero);
    }


    @Override
    public String toString() {
        return "Habitacion{" +
                "numero=" + numero +
                ", tipoHabitacion=" + tipoHabitacion +
                ", tarifaPorDia=" + tarifaPorDia +
                ", estadoHabitacion=" + estadoHabitacion +
                '}';
    }
}
