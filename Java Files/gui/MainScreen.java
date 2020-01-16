package gui;

import grantt.BaseDeDatos;
import gui_tienda.TiendaPanel;
import gui_tienda.Updater;
import jugar.Jugar;
import top.TopPanel;

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

import editor.EditorPanel;


public class MainScreen extends JFrame {
    public JPanel menuContainer;
    public JPanel leftPanel;
    public JPanel rightPanel;
    
    public JButton btnJugar;
    public JButton btnTienda;
    public JButton btnAdmin;
    public JButton btnEditor;
    public JButton btnTop;
    
    public CardLayout cl;
    
    private Dimension size;
    
    private Jugar jugar;
    private TiendaPanel tienda;
    private Admin adminPanel;
    private EditorPanel editor;
    private TopPanel top;
    
    public MainScreen() {
        super("GranTT - Menu");
        
        BaseDeDatos.setUltimoTorneo();
        
        this.initComponents();
        this.initEvents();
        this.setSize(this.size.width, this.size.height);
        this.setResizable(false);
        
        // Centrar la ventana
        this.setLocation(
            (int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth() - this.getWidth()) / 2), 
            (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight()- this.getHeight()) / 2)
        );
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        this.btnTienda.doClick();
    }
    
    
    public void initComponents() {
        menuContainer = new JPanel();
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        btnJugar = new JButton("Jugar");
        btnTienda = new JButton("Tienda");
        btnAdmin = new JButton("Admin");
        btnEditor = new JButton("Editor");
        btnTop = new JButton("Top Usuarios");
        
        cl = new CardLayout();
        
        size = new Dimension(
            Toolkit.getDefaultToolkit().getScreenSize().width / 20 * 18,
            Toolkit.getDefaultToolkit().getScreenSize().height / 20 * 17
        );
        
        jugar	= new Jugar();
        tienda	= new TiendaPanel(this, BaseDeDatos.obtenerEquiposReales());
        adminPanel = new Admin();
        editor = new EditorPanel(new Dimension(this.size.width / 20 * 18, this.size.height));
        top = new TopPanel();
        
        leftPanel.setBackground(Color.DARK_GRAY);
        rightPanel.setBackground(Color.GRAY);
        menuContainer.setBackground(Color.WHITE);
        
        btnJugar.setBackground(Color.LIGHT_GRAY);
        btnTienda.setBackground(Color.LIGHT_GRAY);
        btnEditor.setBackground(Color.LIGHT_GRAY);
        btnTop.setBackground(Color.LIGHT_GRAY);
        btnAdmin.setBackground(Color.LIGHT_GRAY);
        
        leftPanel.setPreferredSize(
            new Dimension(
                this.size.width / 20 * 2, 
                this.size.height
            )
        );

        rightPanel.setPreferredSize(
            new Dimension(
                size.width / 20 * 18, 
                size.height
            )
        );
        
        Dimension buttonSize = new Dimension(size.width / 20 * 2, size.height / 20);
        btnJugar.setMaximumSize(buttonSize);
        btnTienda.setMaximumSize(buttonSize);
        btnEditor.setMaximumSize(buttonSize);
        btnAdmin.setMaximumSize(buttonSize);
        btnTop.setMaximumSize(buttonSize);
        
        btnJugar.setFocusPainted(false);
        btnTienda.setFocusPainted(false);
        btnEditor.setFocusPainted(false);
        btnAdmin.setFocusPainted(false);
        btnTop.setFocusPainted(false);

        menuContainer.setLayout(new BorderLayout());
        leftPanel.setLayout(new BoxLayout(this.leftPanel, BoxLayout.Y_AXIS));
        rightPanel.setLayout(this.cl);
        
        
        rightPanel.add(this.jugar, "Jugar");
        rightPanel.add(this.tienda, "Tienda");
        rightPanel.add(this.editor, "Editor");
        rightPanel.add(this.top, "Top Usuarios");
        if(BaseDeDatos.esAdmin()) rightPanel.add(this.adminPanel, "Admin");
        
        leftPanel.add(this.btnJugar);
        leftPanel.add(this.btnTienda);
        leftPanel.add(this.btnEditor);
        leftPanel.add(this.btnTop);
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
        this.btnEditor.addActionListener(
        	new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					CardLayout cl = (CardLayout)rightPanel.getLayout();
					cl.show(MainScreen.this.rightPanel, "Editor");
					editor.getJugadores();
				}
			}
        );
        this.btnTop.addActionListener(
        	new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					CardLayout cl = (CardLayout)rightPanel.getLayout();
					cl.show(MainScreen.this.rightPanel, "Top Usuarios");
					top.update();
				}
			}
        );
    }
}
