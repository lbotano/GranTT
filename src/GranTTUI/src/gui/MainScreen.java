package gui;

import grantt.BaseDeDatos;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import static javax.swing.BoxLayout.X_AXIS;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainScreen extends JFrame {
    public JPanel menuContainer;
    public JPanel leftPanel;
    public JPanel rightPanel;
    
    public JButton btnJugar;
    public JButton btnTienda;
    public JButton btnEditor;
    
    public CardLayout cl;
    
    private Dimension size;
    
    
    //>>>>>>>>>>>>>>>>>>>>AGREGAAAA ACAAAAA TU JPANELL<<<<<<<<<<<<<<<<<<<<<<<<<<
    //private Jugar jugar;
    private TiendaPanel tienda;
    private Editor editor;
    
    public MainScreen() {
        super("GranTT - Menu");
        
        
        this.initComponents();
        
        this.initEvents();
        
        
        this.setSize(this.size.width, this.size.height);
        this.setResizable(false);
        
        //para centrar la interfaz
        this.setLocation(
            (int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth() - this.getWidth()) / 2), 
            (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight()- this.getHeight()) / 2)
        );
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    
    public void initComponents() {
        this.menuContainer = new JPanel();
        this.leftPanel = new JPanel();
        this.rightPanel = new JPanel();
        this.btnJugar = new JButton("Jugar");
        this.btnTienda = new JButton("Tienda");
        this.btnEditor = new JButton("Editor");
        
        this.tienda = new TiendaPanel(this, BaseDeDatos.obtenerEquiposReales());
        this.editor = new Editor();
        
        this.cl = new CardLayout();
        
        this.size = new Dimension(
            Toolkit.getDefaultToolkit().getScreenSize().width / 20 * 18,
            Toolkit.getDefaultToolkit().getScreenSize().height / 20 * 17
        );
        
        this.leftPanel.setBackground(Color.DARK_GRAY);
        this.rightPanel.setBackground(Color.GRAY);
        this.menuContainer.setBackground(Color.WHITE);
        
        this.btnJugar.setBackground(Color.LIGHT_GRAY);
        this.btnTienda.setBackground(Color.LIGHT_GRAY);
        this.btnEditor.setBackground(Color.LIGHT_GRAY);
        
        this.leftPanel.setPreferredSize(
            new Dimension(
                this.size.width / 20 * 2, 
                this.size.height
            )
        );
        //w: screen / 20 * 18 / 20 * 18
        //h: screen / 20 * 17
        this.rightPanel.setPreferredSize(
            new Dimension(
                this.size.width / 20 * 18, 
                this.size.height
            )
        );
        
        
        this.btnJugar.setMaximumSize(
            new Dimension(
                this.size.width / 20 * 2, 
                this.size.height / 20
            )
        );
        this.btnTienda.setMaximumSize(
            new Dimension(
                this.size.width / 20 * 2, 
                this.size.height / 20
            )
        );
        this.btnEditor.setMaximumSize(
            new Dimension(
                this.size.width / 20 * 2, 
                this.size.height / 20
            )
        );
        
        this.btnJugar.setFocusPainted(false);
        this.btnTienda.setFocusPainted(false);
        this.btnEditor.setFocusPainted(false);

        this.menuContainer.setLayout(new BorderLayout());
        this.leftPanel.setLayout(new BoxLayout(this.leftPanel, BoxLayout.Y_AXIS));
        this.rightPanel.setLayout(this.cl);
        
        
        //this.rightPanel.add(this.jugar, "Jugar");
        this.rightPanel.add(this.tienda, "Tienda");
        this.rightPanel.add(this.editor, "Editor");
        
        
        //aca agrego los paneles de la interfaz
        
        this.leftPanel.add(this.btnJugar);
        this.leftPanel.add(this.btnTienda);
        this.leftPanel.add(this.btnEditor);
        
        this.menuContainer.add(this.leftPanel, BorderLayout.LINE_START);
        this.menuContainer.add(this.rightPanel, BorderLayout.CENTER);
        
        
        this.getContentPane().add(this.menuContainer);
        
    }
    
    private void initEvents() {
        
        this.btnJugar.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    CardLayout cl = (CardLayout)rightPanel.getLayout();
                    //cl.show(/*Tu JPanel*/, "Jugar");
                    
                }
            }
        );
        this.btnTienda.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    CardLayout cl = (CardLayout)rightPanel.getLayout();
                    cl.show(MainScreen.this.rightPanel, "Tienda");
                    
                }
            }
        );
        this.btnEditor.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    CardLayout cl = (CardLayout)rightPanel.getLayout();
                    cl.show(MainScreen.this.rightPanel, "Editor");
                    
                }
            }
        );
        
    }
}
