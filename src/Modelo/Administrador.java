package Modelo;

public class Administrador extends Usuario{

    private static Administrador administrador;
    private static final String USUARIO = "admin";
    private static final String PASSWORD = "admin";

    public Administrador()
    {
        super(USUARIO, PASSWORD);
    }
    public static Administrador getAdministrador()
    {
        if(administrador == null)
        {
            administrador = new Administrador();
        }
        return administrador;
    }
    public boolean validarCredenciales(String username, String password) {

        return username.equals(USUARIO) && password.equals(PASSWORD);

    }

}
