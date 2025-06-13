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

    public Pasajero registrarPasajero(Scanner sc)  {
        System.out.println("Ingrese el nombre del pasajero");
        while (true) {
            String nombre = sc.nextLine().trim();

            if (nombre.isEmpty()) {
                System.out.println("Error: El nombre no puede estar vacío");
                continue;
            }

            if (nombre.matches(".*\\d.*")) {
                System.out.println("Error: El nombre solo debe contener letras");
                continue;
            }

            this.nombre = nombre;
            break;
        }
        this.dni = leerEnteroValidado("Ingrese el DNI del pasajero", 7, 8, sc);

         System.out.println("Ingrese el origen del pasajero");
         this.origen = sc.nextLine();
         System.out.println("Ingrese el domicilio del pasajero");
         this.domicilio = sc.nextLine();

         return this;
    }
    private int leerEnteroValidado(String mensaje, int minDigitos, int maxDigitos, Scanner sc) {
        while (true) {
            System.out.println(mensaje);
            try {
                String input = sc.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println("Error: El valor no puede estar vacío");
                    continue;
                }

                if (!input.matches("\\d+")) {
                    System.out.println("Error: Debe ingresar solo números");
                    continue;
                }

                if (input.length() < minDigitos || input.length() > maxDigitos) {
                    System.out.println("Error: Debe tener entre " + minDigitos + " y " + maxDigitos + " dígitos");
                    continue;
                }

                return Integer.parseInt(input);

            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido");
            }
        }
    }

}
