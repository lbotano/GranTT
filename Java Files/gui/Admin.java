package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import grantt.BaseDeDatos;
import grantt.Torneo;
import grantt.Usuario;

public class Admin extends JPanel{
	private MainScreen owner;
	
	private GridBagLayout layout;
	private GridBagConstraints c;
	
	private JList<Torneo> torneos;
	private DefaultListModel<Torneo> torneosLM;
	
	private JLabel jornada;
	private JButton pasarJornada;
	private JButton crearTorneo;
	
	public void update() {
		torneosLM.removeAllElements();
		torneosLM.addAll(BaseDeDatos.obtenerTorneos());
		
		BaseDeDatos.setUltimoTorneo();
		
		// Desactiva los botenes de acuerdo a si quedan partidos
		boolean quedanPartidos = BaseDeDatos.obtenerPartidosPendientes().size() > 0;
		
		if(quedanPartidos) {
			pasarJornada.setText("Pasar al dia siguiente");
		} else {
			pasarJornada.setText("Obtener Resultados");
		}
		
		int intJornada = BaseDeDatos.obtenerJornada();
		System.out.println("Numero Jornada: " + intJornada);
		if(intJornada >= 0) {
			jornada.setText("Día: " + BaseDeDatos.obtenerJornada());
			pasarJornada.setEnabled(true);
		} else {
			jornada.setText("No hay torneo");
			pasarJornada.setEnabled(false);
		}
		
		crearTorneo.setEnabled(true);
		pasarJornada.repaint();
		crearTorneo.repaint();
	}
	
	public void mostrarResultado() {
		String mejores = "<html><b>Mejores Jugadores:</b><br>";
		
		List<Usuario> usuarios = BaseDeDatos.obtenerTopUsuarios();
		if(usuarios.size() > 0) {
			int cont = 0;
			for(Usuario u : usuarios) {
				mejores += u.getNombre() + " " + u.getPresupuesto() + "</html>";
				cont++;
				if(cont == 3) {
					break;
				}
			}
		} else {
			mejores += "No Se Encontro Ningun Jugador :(</html>";
		}
		
		JOptionPane.showMessageDialog(this, mejores, "Los Mejores", JOptionPane.WARNING_MESSAGE);
	}
	
	private void initEvents() {
		pasarJornada.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(BaseDeDatos.obtenerPartidosPendientes().size() > 0) {
					BaseDeDatos.jugarDiaSiguiente();
				} else {
					mostrarResultado();
				}
				update();
			}
		});
		
		crearTorneo.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				BaseDeDatos.crearTorneo();
				update();
			}
		});
	}
	
	public Admin(MainScreen ms) {
		this.owner = ms;
		layout = new GridBagLayout();
		c = new GridBagConstraints();
		this.setLayout(layout);
		
		torneosLM	= new DefaultListModel<Torneo>();
		torneosLM.addAll(BaseDeDatos.obtenerTorneos());
		torneos		= new JList<Torneo>(torneosLM);
		
		jornada			= new JLabel();
		pasarJornada	= new JButton("Pasar al día siguiente");
		crearTorneo		= new JButton("Crear nuevo torneo");
		
		torneos.setEnabled(false);
		pasarJornada.setEnabled(false);
		crearTorneo.setEnabled(false);
		
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.ipadx = 200;
		c.ipady = 200;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 3;
		this.add(torneos, c);
		
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1;
		c.weighty = 0;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(jornada, c);
		
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1;
		c.weighty = 1;
		c.ipadx = 200;
		c.ipady = 200;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(pasarJornada, c);
		
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1;
		c.weighty = 1;
		c.ipadx = 200;
		c.ipady = 200;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(crearTorneo, c);
		
		this.initEvents();
		
		this.update();
	}
}
