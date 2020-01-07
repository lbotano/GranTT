package top;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import grantt.BaseDeDatos;
import grantt.Usuario;

public class TopPanel extends JPanel{
	private DefaultListModel<String> lm = new DefaultListModel<String>();
	private JList<String> usuarios = new JList<String>(lm);
	private JScrollPane scroll = new JScrollPane(usuarios);
	
	private BorderLayout layout = new BorderLayout();
	
	public void update() {
		lm.removeAllElements();
		int i = 1;
		for(Usuario u : BaseDeDatos.obtenerTopUsuarios()) {
			lm.addElement("<html><b>" + i + "</b> " + u.getNombre() + " " + u.valor + "</html>");
			i++;
		}
	}
	
	public TopPanel() {
		super();
		this.setLayout(layout);
		
		this.add(scroll, BorderLayout.CENTER);
		
		update();
	}
}
