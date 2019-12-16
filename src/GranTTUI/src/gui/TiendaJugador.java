package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import grantt.BaseDeDatos;
import grantt.Jugador;

public class TiendaJugador extends JPanel {
	JLabel	nombre;
	JLabel	precio;
	JLabel 	dorsal;
	
	Jugador jugador;
	
	JButton botonComprar;
	
	GridBagLayout layout;
	
	TiendaListaComprar panelComprar;
	TiendaListaVender panelVender;
	
	public TiendaJugador(Jugador jugador) {
		super();
		
		this.jugador = jugador;
		this.panelComprar = panelComprar;
		this.panelVender = panelVender;
		
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		
		// Poner el layout
		this.layout = new GridBagLayout();
		this.setLayout(layout);
		
		// Define los objetos
		this.nombre = new JLabel(jugador.getNombre());
		this.precio = new JLabel("$" + Float.toString(jugador.getValor()));
		this.dorsal = new JLabel(Integer.toString(jugador.getDorsal()));
		
		this.botonComprar = new JButton("Comprar");
				
		// Insertar los objetos
		GridBagConstraints c = new GridBagConstraints();
		
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;		
		c.gridx = 0;
		c.gridy = 0;
		this.add(this.nombre, c);
		
		c.gridx = 0;
		c.gridy = 1;
		this.add(this.precio, c);
		
		c.gridx = 0;
		c.gridy = 2;
		this.add(this.dorsal, c);
		
		c.weightx = 0;
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 3;
		this.add(this.botonComprar, c);
		
		// Desactiva el botón comprar si el jugador no tiene plata
		if(jugador.getValor() > BaseDeDatos.obtenerPresupuesto()) {
			botonComprar.setEnabled(false);
		}else {
			botonComprar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					if(BaseDeDatos.comprarJugador(jugador.getId())) {
						Updater.update();
						System.out.println("Jugador comprado");
					}else {
						System.out.println("Jugador no comprado");
					}
				}
			});
		}
	}
}