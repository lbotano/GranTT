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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SignUp extends JFrame {

    JPanel panel;
    
    BoxLayout boxlayout;
    
    JTextField txtUsuario;
    
    JPasswordField txtContraseña;
    
    JPasswordField txtCodigo;
    
    NumberFormat formato = NumberFormat.getNumberInstance();
    JFormattedTextField txtDni = new JFormattedTextField(formato);
    
    JButton btnRegistrarse;
    
    JButton btnCancelar;
    
    public SignUp() {
        super("GranTT - Registrarse");
        
        JFrame.setDefaultLookAndFeelDecorated(false);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setResizable(false);

        
        this.initComponents();
        

        
        //inicio eventos y demas
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
                
        
        this.btnRegistrarse.requestFocus();
        this.setVisible(true);
    }
    
    private void initComponents() {
         
        this.panel = new JPanel();
        this.boxlayout = new BoxLayout(this.panel, BoxLayout.Y_AXIS);
        this.txtUsuario = new JTextField("Nombre de usuario");
        this.txtContraseña = new JPasswordField("Contraseña");
        this.txtCodigo = new JPasswordField();
        this.btnRegistrarse = new JButton("Registrarse");
        this.btnCancelar = new JButton("Cancelar");
        
        this.panel.setLayout(this.boxlayout);
        
        this.btnRegistrarse.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.txtUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.txtContraseña.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.txtDni.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.btnCancelar.setAlignmentX(Component.CENTER_ALIGNMENT);
        
       
        //agrego los controles al frame
        this.panel.add(this.txtUsuario);
        this.panel.add(Box.createRigidArea(new Dimension(0, 10)));   
        this.panel.add(this.txtContraseña);
        this.panel.add(Box.createRigidArea(new Dimension(0, 10)));
        this.panel.add(this.txtCodigo);
        this.panel.add(Box.createRigidArea(new Dimension(0, 10)));
        this.panel.add(this.txtDni);
        this.panel.add(Box.createRigidArea(new Dimension(0, 10)));
        this.panel.add(this.btnRegistrarse);
        this.panel.add(Box.createRigidArea(new Dimension(0, 10)));
        this.panel.add(this.btnCancelar);
        
        this.txtContraseña.setEchoChar((char)0);
        this.txtContraseña.setText("Contraseña");
        
        this.txtCodigo.setEchoChar((char)0);
        this.txtCodigo.setText("Codigo");
        this.txtDni.setText("DNI");
        
        
        // Seteo el borde vacio
        
        this.panel.setBorder(new EmptyBorder(new Insets(150, 200, 150, 200)));

    }
    
    private void initEvents() {
        this.txtUsuario.addFocusListener(
            new FocusListener() {
                public void focusGained(FocusEvent fe) {
                    if(txtUsuario.getText().equals("Nombre de usuario"))
                        txtUsuario.setText("");
                }
                public void focusLost(FocusEvent fe) {
                    if(txtUsuario.getText().equals(""))
                        txtUsuario.setText("Nombre de usuario");
                }

            }
        );
        
        
        this.txtContraseña.addFocusListener(
        	new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent arg0) {
					if(new String(txtContraseña.getPassword()).equals("")){
						txtContraseña.setText("Contraseña");
						txtContraseña.setEchoChar((char)0);
        			}
				}
				
				@Override
				public void focusGained(FocusEvent arg0) {
					if(new String(txtContraseña.getPassword()).equals("Contraseña"))
					{
						txtContraseña.setText("");
						txtContraseña.setEchoChar('●');
					}
				}
			}
        );
        
        this.txtCodigo.addMouseListener(
            new MouseListener() {
                public void mouseClicked(MouseEvent e) {
                    txtCodigo.setText("");
                    txtCodigo.setEchoChar('●');
                }
                //no le prestes atencion a esto
                public void mousePressed(MouseEvent e) {}
                public void mouseReleased(MouseEvent e) {}
                public void mouseEntered(MouseEvent e) {}
                public void mouseExited(MouseEvent e) {}
            }
        );
        
        
        this.txtDni.addFocusListener(
            new FocusListener() {
                public void focusGained(FocusEvent fe) {
                    if(txtDni.getText().equals("DNI"))
                        txtDni.setText("");
                }
                public void focusLost(FocusEvent fe) {
                    if(txtDni.getText().equals(""))
                        txtDni.setText("DNI");
                }

            }
        );
        
        this.btnRegistrarse.addActionListener(
            new ActionListener() {
                @Override 
                public void actionPerformed(ActionEvent e) {
                    if(!(txtUsuario.getText().isEmpty() || txtDni.getText().length() != 10)) {
                        if(!txtContraseña.getPassword().toString().isEmpty()) {    
                            boolean adm = false;
                            String valor = new String(txtCodigo.getPassword());
                            
                            if(valor.equals("admin")) {
                                adm = true;
                            }
                            if(BaseDeDatos.crearUsuario(
                                new Usuario(
                                    txtUsuario.getText(), 
                                    String.valueOf(txtContraseña.getPassword())
                                ), 
                                adm,
                                String.valueOf(txtDni.getText()))
                            ) {
                                JOptionPane.showMessageDialog(
                                    null, "Se Ha Creado El Usuario Con Exito"
                                );
                                
                                btnCancelar.doClick();
                                
                            } else {
                                JOptionPane.showMessageDialog(
                                    null, 
                                    "Error Al Crear El Nuevo Usuario. Ingrese Otro Nombre"
                                );
                            }
                        
                            return;
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Ingrese Todos Los Datos");
                }
            }
        );
        
        this.btnCancelar.addActionListener(
            new ActionListener() {
                @Override 
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    LogIn li = new LogIn();
                }
            }
        );
    }
}
