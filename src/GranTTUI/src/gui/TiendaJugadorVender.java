package gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import grantt.BaseDeDatos;
import grantt.Jugador;

public class TiendaJugadorVender extends JPanel{
	
	JLabel nombre;
	JLabel precio;
	JLabel dorsal;
	JButton botonVender;
	
	GridBagLayout layout;
	
	Jugador jugador;
	
	private void initializeComponents() {
		this.setBorder(BorderFactory.createBevelBorder(1));
		
		nombre = new JLabel(jugador.getNombre());
		precio = new JLabel("$" + Float.toString(jugador.getValor()));
		dorsal = new JLabel(Integer.toString(jugador.getDorsal()));
		botonVender = new JButton("Vender");
		
		layout = new GridBagLayout();
		
		GridBagConstraints c = new GridBagConstraints();
		
		this.setLayout(layout);
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(nombre, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(precio, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(dorsal, c);
		
		c.weightx = 0;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 3;
		add(botonVender, c);
	}
	
	public TiendaJugadorVender(Jugador jugador) {
		super();
		this.jugador = jugador;
		initializeComponents();
		
		botonVender.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(BaseDeDatos.venderJugador(jugador.getId())) {
					Updater.update();
					System.out.println("Jugador vendido");
				}else {
					System.out.println("Jugador no vendido");
				}
			}
		});
	}
}
