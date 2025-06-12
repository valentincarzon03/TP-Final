package Menu;

import Gestora.GestorHotel;
import Modelo.Administrador;
import Modelo.Conserje;
import Modelo.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private List<Usuario> usuarios = new ArrayList<>();
    private Usuario usuariologueado;
    GestorHotel gestorHotel = new GestorHotel();



    public Menu() {
        // Crear usuarios iniciales de prueba
        usuarios.add(new Administrador("admin", "admin123"));
        usuarios.add(new Conserje("recep", "recep123"));
    }



    public void iniciarSistema() {
        int opcion;
        do {
            System.out.println("╔═══════════════════════════════╗");
            System.out.println("║      SISTEMA HOTELERO         ║");
            System.out.println("╠═══════════════════════════════╣");
            System.out.println("║ 1. Iniciar Sesión             ║");
            System.out.println("║ 2. Salir                      ║");
            System.out.println("╚═══════════════════════════════╝");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

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

        int opcion = scanner.nextInt();
        scanner.nextLine();

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
            System.out.println("║ 1. Crear Conserje            ║");
            System.out.println("║ 2. Crear Habitación          ║");
            System.out.println("║ 3. Cerrar Sesión             ║");
            System.out.println("╚═══════════════════════════════╝");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    crearNuevoConserje();
                    break;
                case 2:
                    gestorHotel.nuevaHabitacion();
                    break;
                case 3:
                    System.out.println("Cerrando sesión...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 3);
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
            System.out.println("║ 5. Listar Habitaciones        ║");
            System.out.println("║ 6. Consultar Historial        ║");
            System.out.println("║ 7. Cerrar Sesión              ║");
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
                    //gestorHotel.habitacionesDisponibles()
                    break;
                case 5:
                    // gestorHotel.
                    break;
                case 6:
                    //consultarHistorialPasajero();
                    break;
                case 7:
                    System.out.println("Cerrando sesión...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 7);
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



