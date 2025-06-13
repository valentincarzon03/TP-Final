package Menu;

import Gestora.GestorHotel;
import Modelo.Administrador;
import Modelo.Conserje;
import Modelo.Usuario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private List<Usuario> usuarios = new ArrayList<>();
    private Usuario usuariologueado;
    GestorHotel gestorHotel ;



    public Menu() {
        gestorHotel = new GestorHotel();
        // Crear usuarios iniciales de prueba
        usuarios.add(new Administrador("admin", "admin123"));
        usuarios.add(new Conserje("recep", "recep123"));
    }



    public void iniciarSistema() {
        try {
            gestorHotel.cargarDatos();
            System.out.println("Datos cargados exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al cargar datos: " + e.getMessage());
        }

        int opcion;
        do {
            System.out.println("╔═══════════════════════════════╗");
            System.out.println("║      SISTEMA HOTELERO         ║");
            System.out.println("╠═══════════════════════════════╣");
            System.out.println("║ 1. Iniciar Sesión             ║");
            System.out.println("║ 2. Salir                      ║");
            System.out.println("╚═══════════════════════════════╝");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número.");
                opcion = 0; // valor inválido para continuar el bucle
                continue;
            }


            switch (opcion) {
                case 1:
                    seleccionarRol();
                    break;
                case 2:
                    System.out.println("¡Gracias por usar el sistema!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 2);
        try {
            gestorHotel.guardarDatos();
            System.out.println("Datos guardados exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al guardar datos: " + e.getMessage());
        }

    }
    private boolean login(String tipoUsuario) {
        System.out.println("╔═══════════════════════════════╗");
        System.out.println("║      SISTEMA DE LOGIN         ║");
        System.out.println("╚═══════════════════════════════╝");
        int intentos = 3;

        while (intentos > 0) {
            System.out.println("\nIntentos restantes: " + intentos);
            System.out.print("Usuario: ");
            String usuarioIngresado = scanner.nextLine().trim();

            System.out.print("Contraseña: ");
            String contrasenaIngresada = scanner.nextLine().trim();

            // Verificar credenciales
            for (Usuario usuario : usuarios) {
                if (usuario.getUsername().equals(usuarioIngresado) &&
                        usuario.getPassword().equals(contrasenaIngresada)) {

                    // Verificar el tipo de usuario
                    boolean esUsuarioCorrecto = (tipoUsuario.equals("ADMIN") && usuario instanceof Administrador) ||
                            (tipoUsuario.equals("CONSERJE") && usuario instanceof Conserje);

                    if (esUsuarioCorrecto) {
                        usuariologueado = usuario;
                        System.out.println("\n¡Bienvenido, " + usuariologueado.getUsername() + "!");
                        return true;
                    } else {
                        System.out.println("Error: No tienes permisos para acceder como " + tipoUsuario);
                        intentos--;
                        break;
                    }
                }
            }

            if (usuariologueado == null) {
                System.out.println("Usuario o contraseña incorrectos");
                intentos--;
            }
        }

        if (intentos == 0) {
            System.out.println("\nDemasiados intentos fallidos. Acceso bloqueado.");
        }

        return false;
    }

    private void seleccionarRol() {
        System.out.println("╔═══════════════════════════════╗");
        System.out.println("║    SELECCIONE SU ROL          ║");
        System.out.println("╠═══════════════════════════════╣");
        System.out.println("║ 1. Administrador              ║");
        System.out.println("║ 2. Recepcionista              ║");
        System.out.println("║ 3. Volver                     ║");
        System.out.println("╚═══════════════════════════════╝");
        int opcion;
        try {
            opcion = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido.");
            opcion = 0; // valor inválido que será manejado en el switch
        }


        switch (opcion) {
            case 1:
                if (login("ADMIN")) {
                    mostrarMenuAdministrador();
                }
                break;
            case 2:
                if (login("CONSERJE")) {
                    mostrarMenuRecepcionista();
                }
                break;
            case 3:
                return;
            default:
                System.out.println("Opción inválida");
        }
    }

    private void mostrarMenuAdministrador() {
        int opcion;
        do {
            System.out.println("╔═══════════════════════════════╗");
            System.out.println("║    MENÚ ADMINISTRADOR         ║");
            System.out.println("╠═══════════════════════════════╣");
            System.out.println("║ 1. Crear Conserje             ║");
            System.out.println("║ 2. Mostrar Conserjes          ║");
            System.out.println("║ 3. Crear Habitación           ║");
            System.out.println("║ 4. Hacer Back Up              ║");
            System.out.println("║ 5. Cerrar Sesión              ║");
            System.out.println("╚═══════════════════════════════╝");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    crearNuevoConserje();
                    break;
                case 2:
                    //mostrar lista conserjes
                    break;
                case 3:
                    gestorHotel.nuevaHabitacion();
                    break;
                case 4:
                    gestorHotel.guardarDatos();
                case 5:
                    System.out.println("Cerrando sesión...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 5);
    }

    private void mostrarMenuRecepcionista() {
        int opcion;
        do {
            System.out.println("╔═══════════════════════════════╗");
            System.out.println("║    MENÚ RECEPCIONISTA         ║");
            System.out.println("╠═══════════════════════════════╣");
            System.out.println("║ 1. Hacer Reserva              ║");
            System.out.println("║ 2. Hacer Check-in             ║");
            System.out.println("║ 3. Hacer Check-out            ║");
            System.out.println("║ 4. Consultar Disponibilidad   ║");
            System.out.println("║ 5. Todas Las Habitaciones     ║");
            System.out.println("║ 6. Habitaciones Ocupadas      ║");
            System.out.println("║ 7. Habitaciones No Disponibles║");
            System.out.println("║ 8. Mostrar Historial Pasajeros║");
            System.out.println("║ 9. Mostrar Pasajeros Actuales ║");
            System.out.println("║ 10. Cancelar Reserva          ║");
            System.out.println("║ 11. Mostrar Reservas Actuales ║");
            System.out.println("║ 12. Cerrar Sesión              ║");
            System.out.println("╚═══════════════════════════════╝");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    gestorHotel.realizarReserva();
                    break;
                case 2:
                    gestorHotel.realizarCheckIn();
                    break;
                case 3:
                    gestorHotel.realizarCheckOut();
                    break;
                case 4:

                    System.out.println("Ingrese la fecha de inicio de la reserva (dd-MM-yyyy): ");
                    String fechaInicio = scanner.nextLine();
                    System.out.print("Ingrese la fecha de fin de la reserva (dd-MM-yyyy): ");
                    String fechaFin = scanner.nextLine();
                    LocalDate fechaInicioL = LocalDate.parse(fechaInicio, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    LocalDate fechaFinL = LocalDate.parse(fechaFin, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    System.out.println(gestorHotel.habitacionesDisponibles(fechaInicioL, fechaFinL));
                    break;
                case 5:
                    System.out.println(gestorHotel.mostrarDatosHabitaciones());
                    break;
                case 6:
                    System.out.println(gestorHotel.mostrarDatosOcupaciones());
                    break;
                case 7:
                    System.out.println(gestorHotel.habitacionesNoDisponibles());
                    break;
                case 8:
                    System.out.println(gestorHotel.mostrarDatosPasajeros());
                    break;
                    case 9:
                        System.out.println(gestorHotel.datosOcupantes());
                        break;
                case 10:
                        gestorHotel.cancelarReserva();
                        break;
                case 11:
                    System.out.println(gestorHotel.mostrarDatosReservas());
                case 12:
                    System.out.println("Cerrando sesión...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 12);
    }





    private void crearNuevoConserje() {
        System.out.print("Ingrese nombre de usuario: ");
        String nuevoUsuario = scanner.nextLine();

        System.out.print("Ingrese contraseña: ");
        String nuevaContrasena = scanner.nextLine();

        usuarios.add(new Conserje(nuevoUsuario, nuevaContrasena));
        System.out.println("Conserje creado exitosamente.");
    }
}



