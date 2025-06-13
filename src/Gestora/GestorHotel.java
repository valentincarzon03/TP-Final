package Gestora;

import Excepciones.ElementoNuloExcepcion;
import Excepciones.FechaInvalidaExcepcion;
import JsonPersistencia.JsonPersistencia;
import Modelo.*;
import Enum.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class GestorHotel{

   private GestoraGenerica<Integer,Reserva> reservas;
   private GestoraGenerica<Integer, Habitacion> habitaciones;
   private GestoraGenerica<Integer,Ocupacion> ocupaciones;
   private GestoraGenerica<Integer,Pasajero> pasajeros;
   private GestoraGenerica<String, Conserje> conserje;


    public GestorHotel() {
        this.reservas = new GestoraGenerica<>();
        this.habitaciones = new GestoraGenerica<>();
        this.ocupaciones = new GestoraGenerica<>();
        this.pasajeros = new GestoraGenerica<>();
        this.conserje = new GestoraGenerica<>();

    }
    public int obtenerUltimoNumeroReserva() {
        if (reservas.obtenerLista().isEmpty()) {
            return 0;
        }
        return reservas.obtenerLista().keySet().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);
    }


    public void realizarReserva()throws FechaInvalidaExcepcion {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== NUEVA RESERVA ===");

        // Registrar el pasajero
        Pasajero pasajero = new Pasajero();
        pasajero.registrarPasajero(scanner);

        boolean existe = pasajeros.obtenerLista().containsKey(pasajero.getDni());

        if(!existe) {
            pasajeros.agregar(pasajero.getDni(), pasajero);
        }


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate fechaInicioL = null;
            LocalDate fechaFinL = null;
            String fechaInicio = "";
            String fechaFin = "";

        // Solicitar fecha de la reserva
        while (true) {
            try {

                System.out.print("\nIngrese la fecha de inicio de la reserva (dd-MM-yyyy): ");
             fechaInicio = scanner.nextLine();
            System.out.print("Ingrese la fecha de fin de la reserva (dd-MM-yyyy): ");
             fechaFin = scanner.nextLine();

             fechaInicioL = LocalDate.parse(fechaInicio, formatter);
             fechaFinL = LocalDate.parse(fechaFin, formatter);
            LocalDate hoy = LocalDate.now();
                if (fechaInicioL.isBefore(hoy)) {
                    System.out.println("Error: La fecha de inicio no puede ser en el pasado");
                    continue;
                }

                if (fechaFinL.isBefore(fechaInicioL)) {
                    System.out.println("Error: La fecha de fin no puede ser anterior a la fecha de inicio");
                    continue;
                }

                break;

            } catch (FechaInvalidaExcepcion e) {
                System.out.println("Error: Formato de fecha inválido. Use dd-MM-yyyy");
            }
        }


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
                int nuevoNumeroReserva = obtenerUltimoNumeroReserva() + 1;

                Reserva nuevaReserva = new Reserva(fechaInicio, fechaFin, pasajero, EstadoReserva.CONFIRMADA, habitacionSeleccionada, nuevoNumeroReserva);

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
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Realizar Check-In ===");

        try {
            System.out.println("Ingrese número de reserva:");
            String input = scanner.nextLine();
            int numeroReserva = Integer.parseInt(input);

            Reserva reserva = reservas.obtenerElemento(numeroReserva);
            if (reserva == null) {
                System.out.println("Error: El número de reserva " + numeroReserva + " no existe");
                return; // Vuelve al menú anterior
            }

            if (reserva.getEstadoReserva() != EstadoReserva.CONFIRMADA) {
                System.out.println("Error: Solo se puede hacer check-in de reservas confirmadas. " +
                        "Estado actual: " + reserva.getEstadoReserva());
                return; // Vuelve al menú anterior
            }

            LocalDate hoy = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate fechaInicio = LocalDate.parse(reserva.getFechaInicio(), formatter);
            LocalDate fechafin = LocalDate.parse(reserva.getFechaFin(), formatter);

            // Verificar que el check-in se realiza en la fecha correcta
            if (hoy.isBefore(fechaInicio)) {
                System.out.println("Error: No se puede realizar check-in antes de la fecha de inicio. " +
                        "Fecha de inicio: " + reserva.getFechaInicio());
                return; // Vuelve al menú anterior
            }

            if (hoy.isAfter(fechaInicio.plusDays(1))) {
                System.out.println("Error: La reserva ha expirado. Fecha límite de check-in superada");
                return; // Vuelve al menú anterior
            }

            // Verificar que la habitación esté disponible
            Habitacion habitacion = reserva.getHabitacion();
            if (habitacion.getEstadoHabitacion() != EstadoHabitacion.DISPONIBLE) {
                System.out.println("Error: La habitación no está disponible para check-in. " +
                        "Estado actual: " + habitacion.getEstadoHabitacion());
                return; // Vuelve al menú anterior
            }

            Pasajero pasajero = reserva.getPasajero();
            // Crear la ocupación
            Ocupacion ocupacion = new Ocupacion();
            ocupacion.setHabitacion(habitacion);
            ocupacion.setPasajero(pasajero);
            ocupacion.setFechaCheckIn(fechaInicio.atStartOfDay());
            ocupacion.setFechaCheckOut(fechafin.atStartOfDay());
            ocupacion.setEstadoOcupacion(EstadoOcupacion.ACTIVA);
            ocupacion.setNroOcupacion(reserva.getNroReserva());
            double tarifatotal = ocupacion.calcularPrecioEstadia(fechaInicio, fechafin, habitacion);
            ocupacion.setTarifaTotal(tarifatotal);

            pasajero.agregarOcupacion(ocupacion);
            ocupaciones.agregar(ocupacion.getNroOcupacion(), ocupacion);
            habitacion.setEstadoHabitacion(EstadoHabitacion.OCUPADA);
            reserva.setEstadoReserva(EstadoReserva.TERMINADA);
            System.out.println("Check-in realizado exitosamente");

        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido");
        } catch (Exception e) {
            System.out.println("Error al realizar el check-in: " + e.getMessage());
        }

    }


    public List<Habitacion> habitacionesDisponibles(LocalDate fechaInicio, LocalDate fechaFin) throws FechaInvalidaExcepcion {
        // Validar fechas
        if (fechaInicio == null || fechaFin == null) {
            throw new FechaInvalidaExcepcion("Las fechas no pueden ser nulas");
        }

        if (fechaFin.isBefore(fechaInicio)) {
            throw new FechaInvalidaExcepcion("La fecha fin no puede ser anterior a la fecha inicio");
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
                Reserva reserva = reservas.obtenerElemento(ocupacion.getNroOcupacion());
                reserva.setFechaFin(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                ocupacion.setFechaCheckOut(LocalDateTime.now());
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

    public void cancelarReserva() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Cancelar Reserva ===");

        try {
            System.out.println("Ingrese numero de reserva: ");
            String input = scanner.nextLine();
            int numeroReserva = Integer.parseInt(input);

            Reserva reserva = reservas.obtenerElemento(numeroReserva);
            if (reserva == null) {
                System.out.println("Error: La reserva no existe");
                return; // Vuelve al menú anterior
            }

            reserva.setEstadoReserva(EstadoReserva.CANCELADA);
            System.out.println("Reserva cancelada exitosamente");
            System.out.println("Pasajero: " + reserva.getPasajero().getNombre());

        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido");
        } catch (ElementoNuloExcepcion e) {
            System.out.println("Error: " + e.getMessage());
        }
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
    public void cargarDatos() {
        cargarHabitaciones();
        cargarPasajeros();
        cargarReservas();
        cargarOcupaciones();
    }

    public void guardarDatos() {
        guardarHabitaciones();
        guardarPasajeros();
        guardarReservas();
        guardarOcupaciones();

    }

    private void cargarPasajeros() {
        JSONArray jsonPasajeros = JsonPersistencia.cargarJson("pasajeros.json");
        for (int i = 0; i < jsonPasajeros.length(); i++) {
            JSONObject jsonPasajero = null;
            try {
                jsonPasajero = jsonPasajeros.getJSONObject(i);
                Pasajero pasajero = new Pasajero();
                pasajero.setDni(jsonPasajero.getInt("dni"));
                pasajero.setNombre(jsonPasajero.getString("nombre"));
                pasajero.setOrigen(jsonPasajero.getString("origen"));
                pasajero.setDomicilio(jsonPasajero.getString("domicilio"));
                // Procesar el Set de ocupaciones
                Set<Ocupacion> ocupaciones = new HashSet<>();
                if (jsonPasajero.has("ocupaciones")) {
                    JSONArray jsonOcupaciones = jsonPasajero.getJSONArray("ocupaciones");
                    for (int j = 0; j < jsonOcupaciones.length(); j++) {
                        JSONObject jsonOcupacion = jsonOcupaciones.getJSONObject(j);

                        Ocupacion ocupacion = new Ocupacion();
                        ocupacion.setNroOcupacion(jsonOcupacion.getInt("nroOcupacion"));

                        // Convertir fechas de String a LocalDateTime
                        String checkIn = jsonOcupacion.getString("fechaCheckIn");
                        String checkOut = jsonOcupacion.getString("fechaCheckOut");
                        ocupacion.setFechaCheckIn(LocalDateTime.parse(checkIn));
                        ocupacion.setFechaCheckOut(LocalDateTime.parse(checkOut));

                        // Obtener la habitación referenciada
                        int numeroHabitacion = jsonOcupacion.getInt("numeroHabitacion");
                        Habitacion habitacion = habitaciones.obtenerElemento(numeroHabitacion);
                        ocupacion.setHabitacion(habitacion);

                        // Establecer el estado de la ocupación
                        String estadoStr = jsonOcupacion.getString("estadoOcupacion");
                        ocupacion.setEstadoOcupacion(EstadoOcupacion.valueOf(estadoStr));

                        ocupacion.setTarifaTotal(jsonOcupacion.getDouble("tarifaTotal"));

                        pasajero.agregarOcupacion(ocupacion);

                    }
                }



                    pasajeros.agregar(pasajero.getDni(), pasajero);

        } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
        }

    private void guardarPasajeros() {
        JSONArray jsonPasajeros = new JSONArray();
        for (Pasajero pasajero : pasajeros.obtenerLista().values()) {
            JSONObject jsonPasajero = new JSONObject();

            // Datos básicos del pasajero
            try {
                jsonPasajero.put("dni", pasajero.getDni());
                jsonPasajero.put("nombre", pasajero.getNombre());
                jsonPasajero.put("origen", pasajero.getOrigen());
                jsonPasajero.put("domicilio", pasajero.getDomicilio());

                // Convertir Set de ocupaciones a JSONArray
                JSONArray jsonOcupaciones = new JSONArray();
                Set<Ocupacion> ocupaciones = pasajero.gethistorialOcupaciones();
                if (ocupaciones != null) {
                    for (Ocupacion ocupacion : ocupaciones) {
                        JSONObject jsonOcupacion = new JSONObject();
                        jsonOcupacion.put("nroOcupacion", ocupacion.getNroOcupacion());
                        jsonOcupacion.put("fechaCheckIn", ocupacion.getFechaCheckIn().toString());
                        jsonOcupacion.put("fechaCheckOut", ocupacion.getFechaCheckOut().toString());
                        jsonOcupacion.put("numeroHabitacion", ocupacion.getHabitacion().getNumero());
                        jsonOcupacion.put("estadoOcupacion", ocupacion.getEstadoOcupacion().toString());
                        jsonOcupacion.put("tarifaTotal", ocupacion.getTarifaTotal());

                        jsonOcupaciones.put(jsonOcupacion);
                    }

                    jsonPasajero.put("ocupaciones", jsonOcupaciones);


                }
                jsonPasajeros.put(jsonPasajero);
                JsonPersistencia.guardarPasajeros(jsonPasajeros);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public String mostrarDatosPasajeros(){
        StringBuilder sb = new StringBuilder();
        for(Pasajero pasajero : pasajeros.obtenerLista().values()){
            sb.append(pasajero).append("\n");
        }
        return sb.toString();
    }
    private void cargarOcupaciones() {
        JSONArray jsonOcupaciones = JsonPersistencia.cargarJson("ocupaciones.json");
        for (int i = 0; i < jsonOcupaciones.length(); i++) {
            try {
                JSONObject jsonOcupacion = jsonOcupaciones.getJSONObject(i);
                Ocupacion ocupacion = new Ocupacion();

                ocupacion.setNroOcupacion(jsonOcupacion.getInt("nroOcupacion"));
                ocupacion.setFechaCheckIn(LocalDateTime.parse(jsonOcupacion.getString("fechaCheckIn")));
                ocupacion.setFechaCheckOut(LocalDateTime.parse(jsonOcupacion.getString("fechaCheckOut")));

                int numeroHabitacion = jsonOcupacion.getInt("numeroHabitacion");
                ocupacion.setHabitacion(habitaciones.obtenerElemento(numeroHabitacion));

                String pasajeroStr = jsonOcupacion.getString("pasajero");
                int dniPasajero = extraerDNI(pasajeroStr);
                Pasajero pasajero = pasajeros.obtenerElemento(dniPasajero);
                ocupacion.setPasajero(pasajero);


                ocupacion.setEstadoOcupacion(EstadoOcupacion.valueOf(jsonOcupacion.getString("estadoOcupacion")));
                ocupacion.setTarifaTotal(jsonOcupacion.getDouble("tarifaTotal"));

                ocupaciones.agregar(ocupacion.getNroOcupacion(), ocupacion);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void guardarOcupaciones() {
        JSONArray jsonOcupaciones = new JSONArray();
        for (Ocupacion ocupacion : ocupaciones.obtenerLista().values()) {
            try {
                JSONObject jsonOcupacion = new JSONObject();

                // Obtener y procesar la cadena del pasajero

                Pasajero pasajero =ocupacion.getPasajero();

                jsonOcupacion.put("pasajero", pasajero);

                jsonOcupacion.put("nroOcupacion", ocupacion.getNroOcupacion());
                jsonOcupacion.put("fechaCheckIn", ocupacion.getFechaCheckIn().toString());
                jsonOcupacion.put("fechaCheckOut", ocupacion.getFechaCheckOut().toString());
                jsonOcupacion.put("numeroHabitacion", ocupacion.getHabitacion().getNumero());
                jsonOcupacion.put("estadoOcupacion", ocupacion.getEstadoOcupacion().toString());
                jsonOcupacion.put("tarifaTotal", ocupacion.getTarifaTotal());

                jsonOcupaciones.put(jsonOcupacion);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        JsonPersistencia.guardarOcupaciones(jsonOcupaciones);
    }

    // Para Reservas
    private void cargarReservas() {
        JSONArray jsonReservas = JsonPersistencia.cargarJson("reservas.json");
        for (int i = 0; i < jsonReservas.length(); i++) {
            try {
                JSONObject jsonReserva = jsonReservas.getJSONObject(i);
                Reserva reserva = new Reserva();

                reserva.setNroReserva(jsonReserva.getInt("nroReserva"));
                reserva.setFechaInicio(jsonReserva.getString("fechaInicio"));
                reserva.setFechaFin(jsonReserva.getString("fechaFin"));

                int numeroHabitacion = jsonReserva.getInt("numeroHabitacion");
                reserva.setHabitacion(habitaciones.obtenerElemento(numeroHabitacion));

                // Obtener y procesar la cadena del pasajero
                String pasajeroStr = jsonReserva.getString("pasajero");
                int dni = extraerDNI(pasajeroStr);
                Pasajero pasajero = pasajeros.obtenerElemento(dni);
                reserva.setPasajero(pasajero);

                reserva.setEstadoReserva(EstadoReserva.valueOf(jsonReserva.getString("estadoReserva")));


                reservas.agregar(reserva.getNroReserva(), reserva);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private int extraerDNI(String pasajeroStr) {
        int inicio = pasajeroStr.indexOf("dni=") + 4;
        int fin = pasajeroStr.indexOf(",", inicio);
        if (fin == -1) {
            fin = pasajeroStr.indexOf("}", inicio);
        }

        if (inicio >= 4 && fin > inicio) {
            String dniStr = pasajeroStr.substring(inicio, fin);
            return Integer.parseInt(dniStr.trim());
        }
        throw new IllegalArgumentException("No se pudo extraer el DNI de la cadena del pasajero");
    }


    private void guardarReservas() {
        JSONArray jsonReservas = new JSONArray();


        for (Reserva reserva : reservas.obtenerLista().values()) {
            try {

                JSONObject jsonReserva = new JSONObject();



                jsonReserva.put("nroReserva", reserva.getNroReserva());
                jsonReserva.put("fechaInicio", reserva.getFechaInicio());
                jsonReserva.put("fechaFin", reserva.getFechaFin());
                jsonReserva.put("numeroHabitacion", reserva.getHabitacion().getNumero());
                jsonReserva.put("estadoReserva", reserva.getEstadoReserva().toString());
                jsonReserva.put("pasajero", reserva.getPasajero());

                jsonReservas.put(jsonReserva);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        JsonPersistencia.guardarReservas(jsonReservas);
    }

    // Para Habitaciones
    private void cargarHabitaciones() {
        JSONArray jsonHabitaciones = JsonPersistencia.cargarJson("habitaciones.json");
        for (int i = 0; i < jsonHabitaciones.length(); i++) {
            try {
                JSONObject jsonHabitacion = jsonHabitaciones.getJSONObject(i);
                Habitacion habitacion = new Habitacion();

                habitacion.setNumero(jsonHabitacion.getInt("numero"));
                habitacion.setTipoHabitacion(TipoHabitacion.valueOf(jsonHabitacion.getString("tipo")));
                habitacion.setTarifaPorDia(jsonHabitacion.getDouble("tarifaPorDia"));
                habitacion.setEstadoHabitacion(EstadoHabitacion.valueOf(jsonHabitacion.getString("estado")));

                habitaciones.agregar(habitacion.getNumero(), habitacion);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void guardarHabitaciones() {
        JSONArray jsonHabitaciones = new JSONArray();
        for (Habitacion habitacion : habitaciones.obtenerLista().values()) {
            try {
                JSONObject jsonHabitacion = new JSONObject();
                jsonHabitacion.put("numero", habitacion.getNumero());
                jsonHabitacion.put("tipo", habitacion.getTipoHabitacion().toString());
                jsonHabitacion.put("tarifaPorDia", habitacion.getTarifaPorDia());
                jsonHabitacion.put("estado", habitacion.getEstadoHabitacion().toString());

                jsonHabitaciones.put(jsonHabitacion);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        JsonPersistencia.guardarHabitaciones(jsonHabitaciones);
    }

    // Métodos para mostrar datos
    public String mostrarDatosOcupaciones() {
        StringBuilder sb = new StringBuilder();
        for (Ocupacion ocupacion : ocupaciones.obtenerLista().values()) {
            sb.append(ocupacion).append("\n");
        }
        return sb.toString();
    }

    public String mostrarDatosReservas() {
        StringBuilder sb = new StringBuilder();
        for (Reserva reserva : reservas.obtenerLista().values()) {
            if (reserva.getEstadoReserva() == EstadoReserva.CONFIRMADA) {
            sb.append(reserva).append("\n");
        }
        }
        return sb.toString();
    }

    public String mostrarDatosHabitaciones() {
        StringBuilder sb = new StringBuilder();
        for (Habitacion habitacion : habitaciones.obtenerLista().values()) {
            sb.append(habitacion).append("\n");
        }
        return sb.toString();
    }



}