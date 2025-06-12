package JsonPersistencia;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonPersistencia {
    private static final String RUTA = "src/JsonPersistencia/";

    public static void guardarPasajeros(JSONArray pasajeros) {
        guardarJson("pasajeros.json", pasajeros);
    }

    public static void guardarOcupaciones(JSONArray ocupaciones) {
        guardarJson("ocupaciones.json", ocupaciones);
    }

    public static void guardarReservas(JSONArray reservas) {
        guardarJson("reservas.json", reservas);
    }

    public static void guardarHabitaciones(JSONArray habitaciones) {
        guardarJson("habitaciones.json", habitaciones);
    }

    private static void guardarJson(String archivo, JSONArray datos) {
        try {
            Files.write(Paths.get(RUTA + archivo), datos.toString(2).getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar archivo " + archivo, e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONArray cargarJson(String archivo) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(RUTA + archivo)));
            return new JSONArray(contenido);

        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);

        }

    }
}


