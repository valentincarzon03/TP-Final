package Gestora;

import Excepciones.ElementoNuloExcepcion;
import Modelo.Habitacion;
import Modelo.Ocupacion;
import Modelo.Pasajero;
import Modelo.Reserva;
import Enum.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class GestorHotel{

   private GestoraGenerica<Integer,Reserva> reservas;
   private GestoraGenerica<Integer, Habitacion> habitaciones;
   private GestoraGenerica<Integer,Ocupacion> ocupaciones;
   private GestoraGenerica<Integer,Pasajero> pasajeros;


    public GestorHotel() {
        this.reservas = new GestoraGenerica<>();
        this.habitaciones = new GestoraGenerica<>();
        this.ocupaciones = new GestoraGenerica<>();
        this.pasajeros = new GestoraGenerica<>();


    }

    public void realizarReserva() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== NUEVA RESERVA ===");

        // Registrar el pasajero
        Pasajero pasajero = new Pasajero();
        pasajero.registrarPasajero(scanner);

        boolean existe = pasajeros.obtenerLista().containsKey(pasajero.getDni());

        if(!existe) {
            pasajeros.agregar(pasajero.getDni(), pasajero);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            // Solicitar fecha de la reserva
            System.out.print("\nIngrese la fecha de inicio de la reserva (dd-MM-yyyy): ");
            String fechaInicio = scanner.nextLine();
            System.out.print("Ingrese la fecha de fin de la reserva (dd-MM-yyyy): ");
            String fechaFin = scanner.nextLine();

            LocalDate fechaInicioL = LocalDate.parse(fechaInicio, formatter);
            LocalDate fechaFinL = LocalDate.parse(fechaFin, formatter);

            // Mostrar habitaciones disponibles
            System.out.println("\nHabitaciones disponibles:");

            List<Habitacion> habitacionesDisponibles = habitacionesDisponibles(fechaInicioL, fechaFinL);
            if(!habitacionesDisponibles.isEmpty()) {
                for (Habitacion hab : habitacionesDisponibles) {
                    System.out.println("Número: " + hab.getNumero() +
                            " - Tipo: " + hab.getTipoHabitacion() +
                            " - Precio: $" + hab.getTarifaPorDia());
                }

                // Seleccionar habitación

                System.out.print("\nIngrese el número de habitación deseada: ");
                int numeroHabitacion = scanner.nextInt();
                Habitacion habitacionSeleccionada = habitaciones.obtenerElemento(numeroHabitacion);
                Reserva nuevaReserva = new Reserva(fechaInicio, fechaFin, pasajero, EstadoReserva.CONFIRMADA, habitacionSeleccionada);

                reservas.agregar(nuevaReserva.getNroReserva(), nuevaReserva);

                System.out.println("\n=== Reserva realizada exitosamente ===");
                System.out.println("Número de reserva: " + nuevaReserva.getNroReserva());
                System.out.println("Pasajero: " + pasajero.getNombre());
                System.out.println("Habitación: " + habitacionSeleccionada.getNumero());
                System.out.println("Fecha: " + fechaInicio + " - " + fechaFin);
            }

            else{
                System.out.println("No hay habitaciones disponibles");

            }

        }
    }


    public void nuevaHabitacion() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Ingreso de Nueva Habitación ===");

        System.out.print("Ingrese el número de habitación: ");
        int numero = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        System.out.print("Ingrese el tipo de habitación (SIMPLE/DOBLE/TRIPLE/CUADRUPLE): ");
        TipoHabitacion tipo = TipoHabitacion.valueOf(scanner.nextLine().toUpperCase());

        System.out.print("Ingrese el precio por noche: ");
        double precio = scanner.nextDouble();

        Habitacion nuevaHabitacion = new Habitacion(numero, tipo, precio, EstadoHabitacion.DISPONIBLE);
        habitaciones.agregar(nuevaHabitacion.getNumero(), nuevaHabitacion);

        System.out.println("¡Habitación agregada exitosamente!");
    }

    public void realizarCheckIn() {
        // Validaciones
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Realizar Check-In ===");

        System.out.println("Ingrese numero de reserva");
        int numeroReserva = scanner.nextInt();
        Reserva reserva = reservas.obtenerElemento(numeroReserva);

        if (reserva == null) {
            throw new IllegalArgumentException("La reserva no puede ser nula");
        }

        if (reserva.getEstadoReserva() != EstadoReserva.CONFIRMADA) {
            throw new IllegalStateException("Solo se puede hacer check-in de reservas confirmadas. " +
                    "Estado actual: " + reserva.getEstadoReserva());
        }

        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fechaInicio = LocalDate.parse(reserva.getFechaInicio(), formatter);
        LocalDate fechafin = LocalDate.parse(reserva.getFechaFin(), formatter);


        // Verificar que el check-in se realiza en la fecha correcta
        if (hoy.isBefore(fechaInicio)) {
            throw new IllegalStateException("No se puede realizar check-in antes de la fecha de inicio. " +
                    "Fecha de inicio: " + reserva.getFechaInicio());
        }

        if (hoy.isAfter(fechaInicio.plusDays(1))) {
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
            ocupacion.setFechaCheckIn(fechaInicio.atStartOfDay());
            ocupacion.setFechaCheckOut(fechafin.atStartOfDay());
            ocupacion.setEstadoOcupacion(EstadoOcupacion.ACTIVA);
            ocupacion.setNroOcupacion(reserva.getNroReserva());
            double tarifatotal = ocupacion.calcularPrecioEstadia(fechaInicio,fechafin,habitacion);
            ocupacion.setTarifaTotal(tarifatotal);
            ocupacion.getPasajero().agregarOcupacion(ocupacion);
            ocupaciones.agregar(ocupacion.getNroOcupacion(), ocupacion);
            habitacion.setEstadoHabitacion(EstadoHabitacion.OCUPADA);
            reserva.setEstadoReserva(EstadoReserva.TERMINADA);
        } catch (Exception e) {
            throw new RuntimeException("Error al realizar el check-in: " + e.getMessage(), e);// HACER EXCEPCION PERSONALIZADA
        }
    }

    public List<Habitacion> habitacionesDisponibles(LocalDate fechaInicio, LocalDate fechaFin) {
        // Validar fechas
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas");
        }

        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha fin no puede ser anterior a la fecha inicio");
        }

        List<Habitacion> disponibles = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


        for (Habitacion habitacion : habitaciones.obtenerLista().values()) {
            boolean estaDisponible = true;

            // Verificar si la habitación tiene reservas que se superpongan con las fechas
            for (Reserva reserva : reservas.obtenerLista().values()) {
                LocalDate reservaInicio = LocalDate.parse(reserva.getFechaInicio(), formatter);
                LocalDate reservaFin = LocalDate.parse(reserva.getFechaFin(),formatter);

                // Una habitación no está disponible si:
                // - La fecha inicio está entre el inicio y fin de una reserva existente
                // - La fecha fin está entre el inicio y fin de una reserva existente
                // - Las fechas solicitadas contienen completamente a una reserva existente
                if (reserva.getHabitacion().getNumero() == habitacion.getNumero() &&
                        fechaInicio.isBefore(reservaFin) &&
                        fechaFin.isAfter(reservaInicio)) {
                    estaDisponible = false;
                    break;
                }

            }

            if (estaDisponible && habitacion.getEstadoHabitacion() == EstadoHabitacion.DISPONIBLE) {
                disponibles.add(habitacion);
            }
        }

        return disponibles;
    }
    public void realizarCheckOut(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Realizar Check-Out ===");
        System.out.println("Ingrese numero de habitacion: ");
        int numeroHabitacion = scanner.nextInt();

        Habitacion habitacion = habitaciones.obtenerElemento(numeroHabitacion);
        if(habitacion==null){
            throw new ElementoNuloExcepcion();
        }

        for(Ocupacion ocupacion : ocupaciones.obtenerLista().values()){
            if(ocupacion.getHabitacion()==habitacion)
            {
                ocupacion.setEstadoOcupacion(EstadoOcupacion.FINALIZADA);
                habitacion.setEstadoHabitacion(EstadoHabitacion.DISPONIBLE);
                ocupaciones.eliminar(ocupacion.getNroOcupacion());
                System.out.println("Check-out realizado exitosamente");
                return;
            }
            else
            {
                System.out.println("No se ha encontrado la habitacion");
            }
        }

    }

    public void cancelarReserva(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Cancelar Reserva ===");
        System.out.println("Ingrese numero de reserva: ");
        int numeroReserva = scanner.nextInt();
        Reserva reserva = reservas.obtenerElemento(numeroReserva);
        if(reserva==null){
            throw new ElementoNuloExcepcion();
        }

        reserva.setEstadoReserva(EstadoReserva.CANCELADA);
        System.out.println("Reserva cancelada exitosamente");
        System.out.println("Pasajero: " + reserva.getPasajero().getNombre());

    }

    public String habitacionesOcupadas() {
        StringBuilder sb = new StringBuilder();
        for (Habitacion ocupacion : habitaciones.obtenerLista().values()) {
            if (ocupacion.getEstadoHabitacion() == EstadoHabitacion.OCUPADA) {
                sb.append(ocupacion.getNumero()).append("\n");
            }

        }
        return sb.toString();
    }

    public String datosOcupantes(){
        StringBuilder sb = new StringBuilder();

        for(Ocupacion ocupacion : ocupaciones.obtenerLista().values()){
            sb.append(ocupacion.getPasajero()).append("\n");
        }
        return sb.toString();
    }

    public String habitacionesNoDisponibles(){
        StringBuilder sb = new StringBuilder();

        for(Habitacion habitacion : habitaciones.obtenerLista().values()){
            if(habitacion.getEstadoHabitacion() == EstadoHabitacion.EN_MANTENIMIENTO || habitacion.getEstadoHabitacion() == EstadoHabitacion.EN_DESINFECCION){
                sb.append(habitacion.getNumero()).append(habitacion.getEstadoHabitacion());
            }
        }
        return sb.toString();
    }










}
