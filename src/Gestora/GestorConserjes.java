package Gestora;

import Excepciones.ElementoRepetido;
import Interfaces.IGestoras;
import JsonPersistencia.JsonPersistencia;
import Modelo.Conserje;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Scanner;

public class GestorConserjes implements IGestoras {
    private GestoraGenerica<String, Conserje> conserjes;

    private static final String NOMBRE_ARCHIVO = "Conserjes.json";

    public GestorConserjes() {
        this.conserjes = new GestoraGenerica<>();
        cargar();
    }
    public void agregarConserje(Conserje conserje) {
        conserjes.agregar(conserje.getUsername(), conserje);
        guardar();
    }
    @Override
    public void cargar() {
        JSONArray jsonConserjes = JsonPersistencia.cargarJson("conserjes.json");

        for (int i = 0; i < jsonConserjes.length(); i++) {
            try {
                JSONObject jsonConserje = jsonConserjes.getJSONObject(i);
                String usuario = jsonConserje.getString("username");
                String password = jsonConserje.getString("password");

                Conserje conserje = new Conserje(usuario, password);
                conserjes.agregar(usuario, conserje);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void guardar() {
        JSONArray jsonConserjes = new JSONArray();
        for (Conserje conserje : conserjes.obtenerLista().values()) {
            try {
                JSONObject jsonConserje = new JSONObject();
                jsonConserje.put("username", conserje.getUsername());
                jsonConserje.put("password", conserje.getPassword());

                jsonConserjes.put(jsonConserje);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        JsonPersistencia.guardarJson(NOMBRE_ARCHIVO, jsonConserjes);
    }

    public boolean validarCredenciales(String username, String password) {
        Conserje conserje = conserjes.obtenerElemento(username);
        return conserje != null && conserje.getPassword().equals(password);
    }

    public boolean existeConserje(String username) {
        return conserjes.contiene(username);
    }
    public void crearNuevoConserje() throws ElementoRepetido {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Ingrese nombre de usuario: ");
            String nuevoUsuario = scanner.nextLine();

            // Validar si ya existe el usuario
            if (conserjes.contiene(nuevoUsuario)) {
                System.out.println("Error: El nombre de usuario ya existe");
                return;
            }

            System.out.print("Ingrese contraseña: ");
            String nuevaContrasena = scanner.nextLine();

            // Crear y agregar el nuevo conserje
            Conserje nuevoConserje = new Conserje(nuevoUsuario, nuevaContrasena);
            agregarConserje(nuevoConserje);

            System.out.println("Conserje creado exitosamente.");

        } catch (ElementoRepetido e) {
            System.out.println(e.getMessage());
        }


    }



    @Override
    public String mostrar() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Conserje conserje : conserjes.obtenerLista().values()) {
            stringBuilder.append(conserje.toString()).append("\n");
        }
        return stringBuilder.toString();


    }
}



