package Modelo;


import Excepciones.ElementoRepetido;

import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class Pasajero {
    private String nombre;
    private int dni;
    private String origen;
    private String domicilio;
    private Set<Ocupacion> historialOcupaciones;

    public Pasajero(String nombre, int dni, String origen, String domicilio) {
        this.nombre = nombre;
        this.dni = dni;
        this.origen = origen;
        this.domicilio = domicilio;
        this.historialOcupaciones = new HashSet<>();
    }

    public Pasajero() {
        this.historialOcupaciones = new HashSet<>();
        }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public Set<Ocupacion> gethistorialOcupaciones() {
        return historialOcupaciones;
    }

    public void agregarOcupacion(Ocupacion ocupacion) {
        historialOcupaciones.add(ocupacion);
    }


    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pasajero pasajero = (Pasajero) o;
        return dni == pasajero.dni;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dni);
    }

    @Override
    public String toString() {
        return "Pasajero{" +
                "nombre='" + nombre + '\'' +
                ", dni=" + dni +
                ", origen='" + origen + '\'' +
                ", domicilio='" + domicilio + '\'' +
                ", historialOcupaciones=" + historialOcupaciones.size() +
                '}';
    }

    public Pasajero registrarPasajero(Scanner sc) throws ElementoRepetido{
         System.out.println("Ingrese el nombre del pasajero");
         this.nombre = sc.nextLine();
         System.out.println("Ingrese el DNI del pasajero");

         this.dni = sc.nextInt();
         sc.nextLine();
         System.out.println("Ingrese el origen del pasajero");
         this.origen = sc.nextLine();
         System.out.println("Ingrese el domicilio del pasajero");
         this.domicilio = sc.nextLine();

         return this;
    }
}
