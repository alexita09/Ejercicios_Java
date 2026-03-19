
package loginescolar;
import java.sql.*;
import java.util.Scanner;
public class LoginTerminal {
 
    // Colores ANSI
    private static final String RESET   = "\033[0m";
    private static final String BOLD    = "\033[1m";
    private static final String GREEN   = "\033[0;32m";
    private static final String RED     = "\033[0;31m";
    private static final String CYAN    = "\033[0;36m";
    private static final String YELLOW  = "\033[0;33m";
 
    private static final Scanner scanner = new Scanner(System.in);
 
    // ────────────────────────────────────────────────
    public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            String opcion = scanner.nextLine().trim();
            switch (opcion) {
                case "1" -> iniciarSesion();
                case "2" -> registrarse();
                case "3" -> salir = true;
                default  -> System.out.println(RED + "Opcion invalida." + RESET);
            }
        }
        System.out.println(CYAN + "\nHasta luego." + RESET);
    }
 
    // ────────────────────────────────────────────────
    private static void mostrarMenu() {
        System.out.println(CYAN + BOLD + "\n╔══════════════════════════════╗");
        System.out.println(       "║    SISTEMA ESCOLAR – LOGIN   ║");
        System.out.println(       "╠══════════════════════════════╣");
        System.out.println(RESET + "  1) Iniciar sesion");
        System.out.println(       "  2) Registrarse");
        System.out.println(       "  3) Salir");
        System.out.println(CYAN + BOLD + "╚══════════════════════════════╝" + RESET);
        System.out.print("Selecciona una opcion: ");
    }
 
    // ────────────────────────────────────────────────
    private static void iniciarSesion() {
        System.out.println(BOLD + "\n--- Iniciar Sesion ---" + RESET);
        System.out.print("Correo      : ");
        String correo = scanner.nextLine().trim();
        System.out.print("Contrasena  : ");
        String contrasena = scanner.nextLine().trim();
 
        String sql = "SELECT id, nombre, apellido FROM usuarios "
                   + "WHERE correo = ? AND contrasena = ?";
 
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setString(1, correo);
            ps.setString(2, contrasena);
 
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nombre   = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    System.out.println(GREEN + BOLD
                        + "\n✔ Bienvenido/a, " + nombre + " " + apellido + "!" + RESET);
                } else {
                    System.out.println(RED
                        + "\n✘ Correo o contrasena incorrectos." + RESET);
                }
            }
        } catch (SQLException e) {
            System.out.println(RED + "Error de base de datos: " + e.getMessage() + RESET);
        }
    }
 
    // ────────────────────────────────────────────────
    private static void registrarse() {
        System.out.println(BOLD + "\n--- Nuevo Registro ---" + RESET);
        System.out.print("Nombre      : ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Apellido    : ");
        String apellido = scanner.nextLine().trim();
        System.out.print("Correo      : ");
        String correo = scanner.nextLine().trim();
        System.out.print("Contrasena  : ");
        String contrasena = scanner.nextLine().trim();
 
        if (nombre.isEmpty() || apellido.isEmpty()
                || correo.isEmpty() || contrasena.isEmpty()) {
            System.out.println(YELLOW + "Todos los campos son obligatorios." + RESET);
            return;
        }
 
        String sql = "INSERT INTO usuarios (nombre, apellido, correo, contrasena) "
                   + "VALUES (?, ?, ?, ?)";
 
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, correo);
            ps.setString(4, contrasena);
            ps.executeUpdate();
 
            System.out.println(GREEN + BOLD
                + "\n✔ Usuario registrado correctamente." + RESET);
 
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(RED + "✘ El correo ya esta registrado." + RESET);
        } catch (SQLException e) {
            System.out.println(RED + "Error de base de datos: " + e.getMessage() + RESET);
        }
    }
}
 