package gui_tienda;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
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
	
	private JButton botonComprar;
	
	private GridBagLayout layout;
	
	private Color color1 = null;
	private Color color2 = null;
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(color1 != null) {
			if(color2 != null) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				int w = this.getWidth();
				int h = this.getHeight();
				int halfH = h / 2;
				GradientPaint gp = new GradientPaint(0, halfH, color1, w, halfH, color2);
				g2d.setPaint(gp);
				g2d.fillRect(0, 0, w - botonComprar.getWidth(), h);
			}else {
				this.setBackground(color1);
			}
		}
		
	}
	
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
		
		this.botonComprar = new JButton("Comprar");
		
		// Desactiva el botón comprar si el jugador no tiene plata
		if(jugador.getValor() > BaseDeDatos.obtenerPresupuesto() && !Updater.esValido(jugador)) {
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
		
		// Pone el color del jugador según su estado
		boolean tarjetasAmarillas	= jugador.getAmarillas();
		boolean tarjetasRojas		= jugador.getRojas();
		int diasLesionado		= jugador.getDiasLesionado();
		
		if(diasLesionado > 0) {
			if(tarjetasAmarillas || tarjetasRojas) {
				color2 = Color.CYAN;
			}else {
				color1 = Color.CYAN;
			}
		}
		
		if(tarjetasAmarillas) {
			color1 = Color.YELLOW;
		}
		
		if(tarjetasRojas) {
			color1 = Color.RED;
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
		
		c.weightx = 0;
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 4;
		this.add(this.botonComprar, c);
	}
}