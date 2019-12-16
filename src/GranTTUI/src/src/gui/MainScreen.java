package gui;

import grantt.BaseDeDatos;
import gui_tienda.TiendaPanel;
import gui_tienda.Updater;
import jugar.Jugar;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainScreen extends JFrame {
    public JPanel menuContainer;
    public JPanel leftPanel;
    public JPanel rightPanel;
    
    public JButton btnJugar;
    public JButton btnTienda;
    public JButton btnAdmin;
    
    public CardLayout cl;
    
    private Dimension size;
    
    
    //>>>>>>>>>>>>>>>>>>>>AGREGAAAA ACAAAAA TU JPANELL<<<<<<<<<<<<<<<<<<<<<<<<<<
    private Jugar jugar;
    private TiendaPanel tienda;
    private Admin adminPanel;
    
    public MainScreen() {
        super("GranTT - Menu");
        
        BaseDeDatos.setUltimoTorneo();
        
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
        menuContainer = new JPanel();
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        btnJugar = new JButton("Jugar");
        btnTienda = new JButton("Tienda");
        btnAdmin = new JButton("Admin");
        
        jugar	= new Jugar();
        tienda	= new TiendaPanel(this, BaseDeDatos.obtenerEquiposReales());
        adminPanel = new Admin();
        
        cl = new CardLayout();
        
        size = new Dimension(
            Toolkit.getDefaultToolkit().getScreenSize().width / 20 * 18,
            Toolkit.getDefaultToolkit().getScreenSize().height / 20 * 17
        );
        
        leftPanel.setBackground(Color.DARK_GRAY);
        rightPanel.setBackground(Color.GRAY);
        menuContainer.setBackground(Color.WHITE);
        
        btnJugar.setBackground(Color.LIGHT_GRAY);
        btnTienda.setBackground(Color.LIGHT_GRAY);
        
        leftPanel.setPreferredSize(
            new Dimension(
                this.size.width / 20 * 2, 
                this.size.height
            )
        );
        //w: screen / 20 * 18 / 20 * 18
        //h: screen / 20 * 17
        rightPanel.setPreferredSize(
            new Dimension(
                size.width / 20 * 18, 
                size.height
            )
        );
        
        Dimension buttonSize = new Dimension(size.width / 20 * 2, size.height / 20);
        btnJugar.setMaximumSize(buttonSize);
        btnTienda.setMaximumSize(buttonSize);
        btnAdmin.setMaximumSize(buttonSize);
        
        btnJugar.setFocusPainted(false);
        btnTienda.setFocusPainted(false);

        menuContainer.setLayout(new BorderLayout());
        leftPanel.setLayout(new BoxLayout(this.leftPanel, BoxLayout.Y_AXIS));
        rightPanel.setLayout(this.cl);
        
        
        rightPanel.add(this.jugar, "Jugar");
        rightPanel.add(this.tienda, "Tienda");
        if(BaseDeDatos.esAdmin()) rightPanel.add(this.adminPanel, "Admin");
        
        //aca agrego los paneles de la interfaz
        
        leftPanel.add(this.btnJugar);
        leftPanel.add(this.btnTienda);
        if(BaseDeDatos.esAdmin()) leftPanel.add(this.btnAdmin);
        
        menuContainer.add(this.leftPanel, BorderLayout.LINE_START);
        menuContainer.add(this.rightPanel, BorderLayout.CENTER);
        
        getContentPane().add(this.menuContainer);
        
    }
    
    private void initEvents() {
        
        btnJugar.addActionListener(
            new ActionListener() {
            	@Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cl = (CardLayout)rightPanel.getLayout();
                    //cl.show(/*Tu JPanel*/, "Jugar");
                    cl.show(MainScreen.this.rightPanel, "Jugar");
                    jugar.update();
                }
            }
        );
        this.btnTienda.addActionListener(
            new ActionListener() {
            	@Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cl = (CardLayout)rightPanel.getLayout();
                    cl.show(MainScreen.this.rightPanel, "Tienda");
                    Updater.update();
                }
            }
        );
        this.btnAdmin.addActionListener(
        	new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					CardLayout cl = (CardLayout)rightPanel.getLayout();
					cl.show(MainScreen.this.rightPanel, "Admin");
					adminPanel.update();
				}
			}
        );
    }
}
