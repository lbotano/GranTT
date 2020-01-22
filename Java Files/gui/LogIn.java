package gui;

import grantt.Usuario;
import grantt.BaseDeDatos;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

class LogIn extends JFrame {
    
    JPanel panel = new JPanel();
    
    BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
    
    JTextField txtUsuario = new JTextField("Nombre De Usuario");
    
    JPasswordField txtContraseña = new JPasswordField();
    
    JButton btnIniciarSesion = new JButton("Iniciar Sesion");
    
    JButton btnRegistrarse = new JButton("Registrarse");
    
    SignUp su;
    
    
    public LogIn() {
        super("GranTT - Inicio");
        JFrame.setDefaultLookAndFeelDecorated(false);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setResizable(false);

        
        //inicio componentes
        this.initComponents();
        
        
        //inicio eventos
        this.initEvents();
        
        // Seteo el tamaño
        this.setSize(500, 300);
         
        
        this.add(this.panel);
        this.pack();
        //para centrar la interfaz
        this.setLocation(
            (int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth() - this.getWidth()) / 2), 
            (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight()- this.getHeight()) / 2)
        );
                
        this.btnIniciarSesion.requestFocus();
        
        this.setVisible(true);
    }
    
    private void initComponents() {
        
        this.panel.setLayout(boxlayout);
        
        this.btnIniciarSesion.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.btnRegistrarse.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.txtUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.txtContraseña.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        // Seteo el borde vacio
        this.panel.setBorder(new EmptyBorder(new Insets(150, 200, 150, 200)));
        
         
        //agrego los controles al frame
        this.panel.add(this.txtUsuario);
        this.panel.add(Box.createRigidArea(new Dimension(0, 10)));   
        this.panel.add(this.txtContraseña);
        this.panel.add(Box.createRigidArea(new Dimension(0, 10)));
        this.panel.add(this.btnIniciarSesion);
        this.panel.add(Box.createRigidArea(new Dimension(0, 10)));
        this.panel.add(this.btnRegistrarse);
        
        this.txtContraseña.setEchoChar((char)0);
        this.txtContraseña.setText("Contraseña");
        
        
        
    }
    
    private void initEvents() {
        
        this.txtUsuario.addFocusListener(
            new FocusListener() {
                public void focusGained(FocusEvent fe) {
                    if(txtUsuario.getText().equals("Nombre De Usuario"))
                    txtUsuario.setText("");
                }
                public void focusLost(FocusEvent fe) {
                    if(txtUsuario.getText().equals("")) {
                        txtUsuario.setText("Nombre De Usuario");
                    }
                }

            }
        );
        
        this.txtContraseña.addFocusListener(
            new FocusListener() {
                public void focusGained(FocusEvent fe) {
                    if("Contraseña".equals(new String(txtContraseña.getPassword()))) {
                        txtContraseña.setText("");
                        txtContraseña.setEchoChar('●');
                    }
                }
                public void focusLost(FocusEvent fe) {
                    if("".equals(new String(txtContraseña.getPassword()))) {
                        txtContraseña.setText("Contraseña");
                        txtContraseña.setEchoChar((char)0);
                    }
                }

            }
        );
        
        this.btnIniciarSesion.addActionListener(
            new ActionListener() {
                @Override 
                public void actionPerformed(ActionEvent e) {
                    if(!txtUsuario.getText().isEmpty()) {
                        if(!txtContraseña.getPassword().toString().isEmpty()) {
                            if(!BaseDeDatos.iniciarSesion(
                                new Usuario(
                                    txtUsuario.getText(), 
                                    new String(txtContraseña.getPassword())
                                )
                            )) {
                                JOptionPane.showMessageDialog(
                                    null, "Error Al Iniciar Sesion. El Usuario No Existe"
                                );
                            } else {
                                MainScreen ms = new MainScreen();
                                dispose();
                                
                                // Se fija si hace falta crear el equipo
                                if(BaseDeDatos.usuarioNoTieneEquipo()) {
                                    String in = "";
                                    do{
                                        in = JOptionPane.showInputDialog("Todavia No Posee Un Equipo. Ingrese El Nombre Del Mismo: ");
                                    } while(in == null || in.isEmpty());
                                    if(BaseDeDatos.crearEquipo(in)) {
                                        JOptionPane.showMessageDialog(null, "Se Ha Creado Su Equipo Con Exito");
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Hubo Un Error Al Crear El Equipo");
                                    }
                                }    
                                ms.cambiarPestana("Tienda");
                                if(!(BaseDeDatos.obtenerValorEquipoUsuario(BaseDeDatos.usuarioLogueado.getNombre()) > 0)) {
                                	JOptionPane.showMessageDialog(null, "Su Equipo No Es Valido. Modifiquelo Para Poder Jugar.");
                                	ms.cambiarPestana("Editor");
                                }
                            }
                            return;
                        }
                    }
                    JOptionPane.showMessageDialog(
                        null, "Ingrese Todos Los Datos"
                    );
                }
            }
        );
        
        this.btnRegistrarse.addActionListener(
            new ActionListener() {
                @Override 
                public void actionPerformed(ActionEvent e) {
                    su = new SignUp();
                    dispose();             
                }
            }
        );
    }
}
