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

import grantt.BaseDeDatos;
import grantt.Jugador;

public class TiendaJugadorVender extends JPanel{
	
	JLabel nombre;
	JLabel precio;
	JLabel dorsal;
	JLabel posicion;
	JLabel situacion;
	JButton botonVender;
	
	GridBagLayout layout;
	
	Jugador jugador;
	
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
				g2d.fillRect(0, 0, w - botonVender.getWidth(), h);
			}else {
				this.setBackground(color1);
			}
		}
		
	}
	
	private void initializeComponents() {
		this.setBorder(BorderFactory.createBevelBorder(1));
		
		nombre = new JLabel(jugador.getNombre());
		precio = new JLabel("$" + Float.toString(jugador.getValor()));
		dorsal = new JLabel(Integer.toString(jugador.getDorsal()));
		posicion = new JLabel(jugador.getPosicion().name());
		
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
		
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(posicion, c);
		
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(situacion, c);
		
		c.weightx = 0;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 4;
		add(botonVender, c);
	}
	
	public TiendaJugadorVender(Jugador jugador) {
		super();
		this.jugador = jugador;
		initializeComponents();
		
		botonVender.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(BaseDeDatos.venderJugador(jugador.getId())) {
					Updater.update();
				}
			}
		});
	}
}