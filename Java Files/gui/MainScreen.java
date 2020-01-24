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

import java.util.ArrayList;

import editor.EditorPanel;

public class MainScreen extends JFrame {
    public JPanel menuContainer;
    public JPanel leftPanel;
    public JPanel rightPanel;
    
    public CardLayout cl;
    
    private Dimension size;
    
    private ArrayList<Pestana> pestanas = new ArrayList<Pestana>();
    
    public MainScreen() {
        super("GranTT - Menu");
        
        BaseDeDatos.setUltimoTorneo();
        
        this.initComponents();
        this.setSize(this.size.width, this.size.height);
        this.setResizable(false);
        
        // Centrar la ventana
        this.setLocation(
            (int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth() - this.getWidth()) / 2), 
            (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight()- this.getHeight()) / 2)
        );
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        // Cambia a la pestaña de la tienda
        this.cambiarPestana("Tienda");
    }
    
    private class Pestana {
    	public String nombre;
    	public JButton boton;
    	public JPanel panel;
    	
    	public Pestana(String nombre, JPanel panel){
    		this.nombre = nombre;
    		this.boton = new JButton(this.nombre);
    		this.panel = panel;
    	}
    	
    	public void updatePanel() {
    		if(panel instanceof Jugar)
    			((Jugar) panel).update(); 
    		else if(panel instanceof TiendaPanel)
    			Updater.update();
    		else if(panel instanceof EditorPanel) {
    			((EditorPanel) panel).getJugadores();
    			((EditorPanel) panel).estado.actualizarValor();
    		} else if(panel instanceof TopPanel)
    			((TopPanel) panel).update();
    		else if(panel instanceof Admin)
    			((Admin) panel).update();
    	}
    }
    
    
    public void initComponents() {
        menuContainer = new JPanel();
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        
        this.size = new Dimension(
            Toolkit.getDefaultToolkit().getScreenSize().width / 20 * 18,
            Toolkit.getDefaultToolkit().getScreenSize().height / 20 * 17
        );
        
        cl = new CardLayout();
        
        leftPanel.setBackground(Color.DARK_GRAY);
        rightPanel.setBackground(Color.GRAY);
        menuContainer.setBackground(Color.WHITE);
        
        leftPanel.setPreferredSize(
            new Dimension(
                this.size.width / 20 * 2, 
                this.size.height
            )
        );

        rightPanel.setPreferredSize(
            new Dimension(
                this.size.width / 20 * 18, 
                this.size.height
            )
        );

        // Fija los layouts de los paneles
        menuContainer.setLayout(new BorderLayout());
        leftPanel.setLayout(new BoxLayout(this.leftPanel, BoxLayout.Y_AXIS));
        rightPanel.setLayout(this.cl);
        
        menuContainer.add(this.leftPanel, BorderLayout.LINE_START);
        menuContainer.add(this.rightPanel, BorderLayout.CENTER);
        
        getContentPane().add(this.menuContainer);
        
        pestanas.add(new Pestana("Jugar", new Jugar()));
        pestanas.add(new Pestana("Tienda", new TiendaPanel(this, BaseDeDatos.obtenerEquiposReales())));
        pestanas.add(new Pestana("Editor", new EditorPanel(new Dimension(this.size.width / 20 * 18, this.size.height))));
        pestanas.add(new Pestana("Top", new TopPanel()));
        // Añade la pestaña de administrador si es necesario
        if(BaseDeDatos.esAdmin()) pestanas.add(new Pestana("Admin", new Admin(this)));
        
        // Cambia el estilo de los botones
        Dimension buttonSize = new Dimension(size.width / 20 * 2, size.height / 20);
        for(Pestana p : this.pestanas) {
        	// Cambia los estilos
        	p.boton.setBackground(Color.LIGHT_GRAY);
        	p.boton.setMaximumSize(buttonSize);
        	p.boton.setFocusPainted(false);
        	
        	// Añade los componentes
        	leftPanel.add(p.boton);
        	rightPanel.add(p.panel, p.nombre);
        	
        	// Añade los eventos
        	p.boton.addActionListener(
        		new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						cambiarPestana(p);
					}
				}
        	);
        }
    }
    
    public void cambiarPestana(Pestana pestana) {
    	CardLayout cl = (CardLayout)this.rightPanel.getLayout();
    	cl.show(this.rightPanel, pestana.nombre);
    	
    	pestana.updatePanel();
    }
    
    public void cambiarPestana(String pestana) {
    	for(Pestana p: this.pestanas) {
    		if(p.nombre.equals(pestana)) {
    			CardLayout cl = (CardLayout)this.rightPanel.getLayout();
    	    	cl.show(this.rightPanel, p.nombre);
    	    	
    	    	p.updatePanel();
    		}
    	}
    }
}