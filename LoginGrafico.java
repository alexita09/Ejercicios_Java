/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loginescolar;
 import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.*;


/**
 *
 * @author DEVELOPMENT
 */

   
/**
 * =====================================================
 *  LOGIN ESCOLAR  –  VERSION GRAFICA (Swing)
 * =====================================================
 *  Pantallas:
 *   - Login      : correo + contrasena
 *   - Registro   : nombre, apellido, correo, contrasena
 *   - Bienvenida : muestra el nombre del usuario
 */
public class LoginGrafico extends javax.swing.JFrame {

    // ── Paleta de colores ──────────────────────────
    private static final Color BG        = new Color(15, 23, 42);
    private static final Color PANEL_BG  = new Color(30, 41, 59);
    private static final Color ACCENT    = new Color(99, 179, 237);
    private static final Color ACCENT2   = new Color(56, 189, 248);
    private static final Color TEXT      = new Color(226, 232, 240);
    private static final Color SUBTEXT   = new Color(148, 163, 184);
    private static final Color INPUT_BG  = new Color(51, 65, 85);
    private static final Color ERROR_COL = new Color(252, 129, 129);
    private static final Color SUCCESS   = new Color(110, 231, 183);

    private CardLayout cardLayout;
    private JPanel     mainPanel;

    // ────────────────────────────────────────────────
    public LoginGrafico() {
        setTitle("Sistema Escolar");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(420, 560);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));

        cardLayout = new CardLayout();
        mainPanel  = new JPanel(cardLayout);
        mainPanel.setBackground(BG);

        mainPanel.add(crearPanelLogin(),    "login");
        mainPanel.add(crearPanelRegistro(), "registro");

        add(mainPanel);
        cardLayout.show(mainPanel, "login");

        // Drag de ventana
        addDrag(mainPanel);
        setShape(new RoundRectangle2D.Double(0, 0, 420, 560, 30, 30));
        setVisible(true);
    }

    // ══════════════════════════════════════════════
    //  PANEL LOGIN
    // ══════════════════════════════════════════════
    private JPanel crearPanelLogin() {
        JPanel panel = new JPanel(null);
        panel.setBackground(BG);
        panel.setPreferredSize(new Dimension(420, 560));

        // Barra de titulo
        JPanel titleBar = titleBar();
        titleBar.setBounds(0, 0, 420, 40);
        panel.add(titleBar);

        // Icono
        JLabel icon = new JLabel("🎓", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 46));
        icon.setBounds(0, 55, 420, 55);
        panel.add(icon);

        // Titulo
        JLabel title = label("Iniciar Sesion", 22, Font.BOLD, TEXT);
        title.setBounds(0, 115, 420, 32);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(title);

        JLabel sub = label("Sistema de Acceso Escolar", 12, Font.PLAIN, SUBTEXT);
        sub.setBounds(0, 149, 420, 20);
        sub.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(sub);

        // Campos
        JLabel lCorreo = label("Correo electronico", 11, Font.BOLD, SUBTEXT);
        lCorreo.setBounds(50, 190, 320, 18);
        panel.add(lCorreo);

        JTextField tfCorreo = inputField("usuario@escuela.com");
        tfCorreo.setBounds(50, 210, 320, 42);
        panel.add(tfCorreo);

        JLabel lPass = label("Contrasena", 11, Font.BOLD, SUBTEXT);
        lPass.setBounds(50, 265, 320, 18);
        panel.add(lPass);

        JPasswordField pfPass = passField();
        pfPass.setBounds(50, 285, 320, 42);
        panel.add(pfPass);

        // Mensaje de error
        JLabel lblError = label("", 11, Font.PLAIN, ERROR_COL);
        lblError.setBounds(50, 335, 320, 18);
        lblError.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblError);

        // Boton login
        JButton btnLogin = accentButton("Iniciar Sesion");
        btnLogin.setBounds(50, 362, 320, 44);
        panel.add(btnLogin);

        // Separador
        JLabel sep = label("¿No tienes cuenta?", 11, Font.PLAIN, SUBTEXT);
        sep.setBounds(0, 420, 420, 18);
        sep.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(sep);

        // Link registro
        JButton btnReg = linkButton("Registrarse");
        btnReg.setBounds(0, 440, 420, 26);
        panel.add(btnReg);

        // Acciones
        btnLogin.addActionListener(e -> {
            lblError.setText("");
            String correo = tfCorreo.getText().trim();
            String pass   = new String(pfPass.getPassword()).trim();
            if (correo.isEmpty() || pass.isEmpty()) {
                lblError.setText("Completa todos los campos.");
                return;
            }
            String[] result = autenticar(correo, pass);
            if (result != null) {
                mostrarBienvenida(panel, result[0] + " " + result[1]);
            } else {
                lblError.setText("Correo o contrasena incorrectos.");
                shake(panel);
            }
        });

        btnReg.addActionListener(e -> cardLayout.show(mainPanel, "registro"));
        return panel;
    }

    // ══════════════════════════════════════════════
    //  PANEL REGISTRO
    // ══════════════════════════════════════════════
    private JPanel crearPanelRegistro() {
        JPanel panel = new JPanel(null);
        panel.setBackground(BG);
        panel.setPreferredSize(new Dimension(420, 560));

        JPanel titleBar = titleBar();
        titleBar.setBounds(0, 0, 420, 40);
        panel.add(titleBar);

        JLabel icon = new JLabel("📝", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        icon.setBounds(0, 48, 420, 48);
        panel.add(icon);

        JLabel title = label("Crear Cuenta", 22, Font.BOLD, TEXT);
        title.setBounds(0, 100, 420, 30);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(title);

        // Nombre
        addLabel(panel, "Nombre", 50, 142);
        JTextField tfNombre = inputField("Juan");
        tfNombre.setBounds(50, 162, 145, 40);
        panel.add(tfNombre);

        // Apellido
        addLabel(panel, "Apellido", 225, 142);
        JTextField tfApellido = inputField("Perez");
        tfApellido.setBounds(225, 162, 145, 40);
        panel.add(tfApellido);

        // Correo
        addLabel(panel, "Correo electronico", 50, 215);
        JTextField tfCorreo = inputField("correo@escuela.com");
        tfCorreo.setBounds(50, 235, 320, 40);
        panel.add(tfCorreo);

        // Contrasena
        addLabel(panel, "Contrasena", 50, 288);
        JPasswordField pfPass = passField();
        pfPass.setBounds(50, 308, 320, 40);
        panel.add(pfPass);

        // Confirmar
        addLabel(panel, "Confirmar contrasena", 50, 361);
        JPasswordField pfConfirm = passField();
        pfConfirm.setBounds(50, 381, 320, 40);
        panel.add(pfConfirm);

        // Mensaje
        JLabel lblMsg = label("", 11, Font.PLAIN, ERROR_COL);
        lblMsg.setBounds(50, 428, 320, 18);
        lblMsg.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblMsg);

        // Boton
        JButton btnReg = accentButton("Crear Cuenta");
        btnReg.setBounds(50, 452, 320, 44);
        panel.add(btnReg);

        // Link volver
        JButton btnVolver = linkButton("← Volver al Login");
        btnVolver.setBounds(0, 504, 420, 26);
        panel.add(btnVolver);

        // Acciones
        btnReg.addActionListener(e -> {
            lblMsg.setForeground(ERROR_COL);
            String nombre    = tfNombre.getText().trim();
            String apellido  = tfApellido.getText().trim();
            String correo    = tfCorreo.getText().trim();
            String pass      = new String(pfPass.getPassword()).trim();
            String confirm   = new String(pfConfirm.getPassword()).trim();

            if (nombre.isEmpty() || apellido.isEmpty()
                    || correo.isEmpty() || pass.isEmpty()) {
                lblMsg.setText("Todos los campos son obligatorios.");
                return;
            }
            if (!pass.equals(confirm)) {
                lblMsg.setText("Las contrasenas no coinciden.");
                return;
            }
            int code = registrar(nombre, apellido, correo, pass);
            if (code == 0) {
                lblMsg.setForeground(SUCCESS);
                lblMsg.setText("✔ Cuenta creada. Inicia sesion.");
                Timer t = new Timer(1500, ev -> cardLayout.show(mainPanel, "login"));
                t.setRepeats(false);
                t.start();
            } else if (code == 1) {
                lblMsg.setText("Ese correo ya esta registrado.");
            } else {
                lblMsg.setText("Error al conectar con la base de datos.");
            }
        });

        btnVolver.addActionListener(e -> cardLayout.show(mainPanel, "login"));
        return panel;
    }

    // ══════════════════════════════════════════════
    //  PANTALLA BIENVENIDA (overlay)
    // ══════════════════════════════════════════════
    private void mostrarBienvenida(JPanel parent, String nombreCompleto) {
        JDialog dialog = new JDialog(this, true);
        dialog.setUndecorated(true);
        dialog.setBackground(new Color(0, 0, 0, 0));
        dialog.setSize(340, 220);
        dialog.setLocationRelativeTo(this);

        JPanel p = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(PANEL_BG);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 24, 24));
                g2.dispose();
            }
        };
        p.setOpaque(false);
        p.setPreferredSize(new Dimension(340, 220));

        JLabel ico = new JLabel("✅", SwingConstants.CENTER);
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 42));
        ico.setBounds(0, 25, 340, 50);
        p.add(ico);

        JLabel welcome = label("¡Bienvenido/a!", 18, Font.BOLD, SUCCESS);
        welcome.setBounds(0, 82, 340, 28);
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        p.add(welcome);

        JLabel name = label(nombreCompleto, 15, Font.BOLD, TEXT);
        name.setBounds(0, 112, 340, 24);
        name.setHorizontalAlignment(SwingConstants.CENTER);
        p.add(name);

        JButton btnCerrar = accentButton("Cerrar sesion");
        btnCerrar.setBounds(70, 152, 200, 40);
        p.add(btnCerrar);

        dialog.add(p);
        dialog.setShape(new RoundRectangle2D.Double(0, 0, 340, 220, 24, 24));
        btnCerrar.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    // ══════════════════════════════════════════════
    //  BASE DE DATOS
    // ══════════════════════════════════════════════
    private String[] autenticar(String correo, String pass) {
        String sql = "SELECT nombre, apellido FROM usuarios "
                   + "WHERE correo = ? AND contrasena = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            ps.setString(2, pass);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return new String[]{rs.getString("nombre"), rs.getString("apellido")};
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** @return 0=OK, 1=correo duplicado, 2=otro error */
    private int registrar(String nombre, String apellido, String correo, String pass) {
        String sql = "INSERT INTO usuarios (nombre, apellido, correo, contrasena) "
                   + "VALUES (?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, correo);
            ps.setString(4, pass);
            ps.executeUpdate();
            return 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 2;
        }
    }

    // ══════════════════════════════════════════════
    //  HELPERS DE UI
    // ══════════════════════════════════════════════
    private JPanel titleBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        bar.setBackground(new Color(15, 23, 42, 200));

        JButton btnClose = new JButton("✕");
        btnClose.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnClose.setForeground(SUBTEXT);
        btnClose.setBackground(new Color(0, 0, 0, 0));
        btnClose.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
        btnClose.setFocusPainted(false);
        btnClose.setContentAreaFilled(false);
        btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnClose.addActionListener(e -> System.exit(0));
        btnClose.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnClose.setForeground(ERROR_COL); }
            public void mouseExited(MouseEvent  e) { btnClose.setForeground(SUBTEXT); }
        });

        bar.add(btnClose);
        return bar;
    }

    private JLabel label(String text, int size, int style, Color color) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", style, size));
        l.setForeground(color);
        return l;
    }

    private void addLabel(JPanel p, String text, int x, int y) {
        JLabel l = label(text, 11, Font.BOLD, SUBTEXT);
        l.setBounds(x, y, 150, 18);
        p.add(l);
    }

    private JTextField inputField(String placeholder) {
        JTextField tf = new JTextField() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(INPUT_BG);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        tf.setOpaque(false);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setForeground(TEXT);
        tf.setCaretColor(ACCENT);
        tf.setBorder(BorderFactory.createCompoundBorder(
            new RoundBorder(ACCENT, 10),
            BorderFactory.createEmptyBorder(5, 12, 5, 12)));

        // Placeholder
        tf.setText(placeholder);
        tf.setForeground(SUBTEXT);
        tf.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (tf.getText().equals(placeholder)) {
                    tf.setText(""); tf.setForeground(TEXT);
                }
            }
            public void focusLost(FocusEvent e) {
                if (tf.getText().isEmpty()) {
                    tf.setText(placeholder); tf.setForeground(SUBTEXT);
                }
            }
        });
        return tf;
    }

    private JPasswordField passField() {
        JPasswordField pf = new JPasswordField() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(INPUT_BG);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        pf.setOpaque(false);
        pf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pf.setForeground(TEXT);
        pf.setCaretColor(ACCENT);
        pf.setBorder(BorderFactory.createCompoundBorder(
            new RoundBorder(ACCENT, 10),
            BorderFactory.createEmptyBorder(5, 12, 5, 12)));
        return pf;
    }

    private JButton accentButton(String text) {
        JButton btn = new JButton(text) {
            private float hover = 0f;
            { 
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hover = 1f; repaint(); }
                    public void mouseExited(MouseEvent  e) { hover = 0f; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                Color c1 = hover > 0 ? new Color(56, 189, 248) : ACCENT;
                Color c2 = hover > 0 ? new Color(99, 179, 237) : new Color(56, 189, 248);
                GradientPaint gp = new GradientPaint(0, 0, c1, getWidth(), 0, c2);
                g2.setPaint(gp);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 12, 12));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(BG);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton linkButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setForeground(ACCENT);
        btn.setBackground(null);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setForeground(ACCENT2); }
            public void mouseExited(MouseEvent  e) { btn.setForeground(ACCENT); }
        });
        return btn;
    }

    private void shake(JPanel panel) {
        int x = getX();
        int[] offsets = {-8, 8, -6, 6, -4, 4, -2, 2, 0};
        Timer t = new Timer(30, null);
        int[] idx = {0};
        t.addActionListener(e -> {
            setLocation(x + offsets[idx[0]++], getY());
            if (idx[0] >= offsets.length) t.stop();
        });
        t.start();
    }

    private void addDrag(JComponent c) {
        int[] pos = new int[2];
        c.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                pos[0] = e.getX(); pos[1] = e.getY();
            }
        });
        c.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                setLocation(getX() + e.getX() - pos[0],
                            getY() + e.getY() - pos[1]);
            }
        });
    }

    // ── Borde redondeado ───────────────────────────
    static class RoundBorder extends AbstractBorder {
        private final Color color;
        private final int   radius;
        RoundBorder(Color c, int r) { color = c; radius = r; }
        @Override public void paintBorder(Component c, Graphics g,
                int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(color.getRed(), color.getGreen(),
                                   color.getBlue(), 80));
            g2.drawRoundRect(x, y, w - 1, h - 1, radius, radius);
            g2.dispose();
        }
        @Override public Insets getBorderInsets(Component c) {
            return new Insets(radius + 1, radius + 1, radius + 1, radius + 1);
        }
    }

    // ══════════════════════════════════════════════
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(LoginGrafico::new);
    }
}


