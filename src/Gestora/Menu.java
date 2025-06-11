package Gestora;

import Modelo.Administrador;
import Modelo.Conserje;
import Modelo.Usuario;

    import java.util.*;

    public class Menu {
        private Scanner scanner = new Scanner(System.in);
        private List<Usuario> usuarios = new ArrayList<>();
        private Usuario usuarioLogueado;

        public Menu() {
            // Crear usuarios iniciales de prueba
            usuarios.add(new Administrador("admin", "admin123"));
            usuarios.add(new Conserje("recep", "recep123"));
        }

        public void iniciarSistema() {
            if (login()) {
                mostrarMenu();
            } else {
                System.out.println("Demasiados intentos fallidos. Saliendo del sistema.");
            }
        }

        private boolean login() {
            System.out.println("╔═══════════════════════════════╗");
            System.out.println("║      SISTEMA DE LOGIN         ║");
            System.out.println("╚═══════════════════════════════╝");
            int intentos = 3;
            while (intentos > 0) {
                System.out.print("Usuario: ");
                String usuarioIngresado = scanner.nextLine();

                System.out.print("Contraseña: ");
                String contrasenaIngresada = scanner.nextLine();

                // Verificar credenciales
                for (Usuario usuario : usuarios) {
                    if (usuario.getUsername().equals(usuarioIngresado)
                            && usuario.getPassword().equals(contrasenaIngresada)) {
                        usuarioLogueado = usuario;
                        System.out.println("¡Bienvenido, " + usuarioLogueado.getUsername() + "!");
                        return true;
                    }
                }
                intentos--;
                System.out.println("Credenciales incorrectas. Intentos restantes: " + intentos);
            }
            return false;
        }

        public void mostrarMenu() {
            int opcion;
            do {
                System.out.println("╔════════════════════════════════╗");
                System.out.println("║    Sistema de Administración   ║");
                System.out.println("║          de Hotel              ║");
                System.out.println("╠════════════════════════════════╣");
                System.out.println("║ 1. Check-in                    ║");
                System.out.println("║ 2. Check-out                   ║");
                System.out.println("║ 3. Realizar reserva            ║");
                System.out.println("║ 4. Listar habitaciones ocupadas║");
                System.out.println("║ 5. Listar habitaciones libres  ║");
                System.out.println("║ 6. Listar habitaciones no disp.║");
                System.out.println("║ 7. Ver informe de pasajero     ║");

                // Opciones solo para ADMIN
                if (usuarioLogueado instanceof Administrador) {
                    System.out.println("║ 8. Crear nuevo usuario Conserje║");
                    System.out.println("║ 9. Hacer backup                ║");

                }

                System.out.println("║ 10. Salir                      ║");
                System.out.println("╚════════════════════════════════╝");
                System.out.print("Seleccione una opción: ");

                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer

                switch (opcion) {
                    case 1:
                       // realizarCheckIn();
                        break;
                    case 2:
                        //realizarCheckOut();
                        break;
                    case 3:
                        //realizarReserva();
                        break;
                    case 4:
                        listarHabitacionesOcupadas();
                        break;
                    case 5:
                        listarHabitacionesLibres();
                        break;
                    case 6:
                        listarHabitacionesNoDisponibles();
                        break;
                    case 7:
                        verInformePasajero();
                        break;
                    case 8:
                        if (usuarioLogueado instanceof Administrador) {
                            crearNuevoConserje();
                        }
                        break;
                    case 10:
                        System.out.println("¡Gracias por usar el sistema!");
                        break;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
                System.out.println();
            } while (opcion != 10);
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


