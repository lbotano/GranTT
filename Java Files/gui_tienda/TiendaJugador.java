package gui_tienda;

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
	private JLabel	nombre;
	private JLabel	precio;
	private JLabel 	dorsal;
	private JLabel	posicion;
	private JLabel	situacion;
	private JButton botonComprar;
	
	private GridBagLayout layout;
	
	public TiendaJugador(Jugador jugador) {
		super();
		
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		
		// Poner el layout
		this.layout = new GridBagLayout();
		this.setLayout(layout);
		
		// Define los objetos
		this.nombre = new JLabel(jugador.getNombre());
		this.precio = new JLabel("$" + Float.toString(jugador.getValor()));
		this.dorsal = new JLabel(Integer.toString(jugador.getDorsal()));
		this.posicion = new JLabel(jugador.getPosicion().name());
		
		String strSituacion = "<html>Situación: ";
		for(int i = 0; i < jugador.getAmarillas(); i++) {
			strSituacion += "<font color=#ffe100>█</font> ";
		}
		for(int i = 0; i < jugador.getRojas(); i++) {
			strSituacion += "<font color=red>█</font> ";
		}
		if(jugador.getDiasLesionado() > 0) {
			strSituacion += "<font color=blue>⚕</font> ";
		}
		strSituacion += "</html>";
		this.situacion = new JLabel(strSituacion);
		
		this.botonComprar = new JButton("Comprar");
		
		// Desactiva el botón comprar si el jugador no tiene plata
		if(jugador.getValor() <= BaseDeDatos.obtenerPresupuesto() && Updater.esValido(jugador)) {
			botonComprar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(BaseDeDatos.comprarJugador(jugador.getId())) {
						Updater.update();
					}
				}
			});
		}else {
			botonComprar.setEnabled(false);
		}
				
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
		
		c.gridx = 0;
		c.gridy = 3;
		this.add(this.posicion, c);
		
		c.gridx = 0;
		c.gridy = 4;
		this.add(this.situacion, c);
		
		c.weightx = 0;
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 5;
		this.add(this.botonComprar, c);
	}
}