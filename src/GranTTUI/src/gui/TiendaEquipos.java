package gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import grantt.Equipo;

public class TiendaEquipos extends JPanel{
	
	TiendaListaComprar			listaComprar;
	DefaultListModel<Equipo>                dataModel;
	JList<Equipo>				lista;
	
	BorderLayout layout;
	
	private void initComponents() {
		this.layout = new BorderLayout(2, 2);
		this.setLayout(layout);
		
		dataModel 	= new DefaultListModel<Equipo>();
		lista 		= new JList<>(dataModel);
		
		lista.addMouseListener(new MouseListener() {
			
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
				listaComprar.setJugadores(lista.getSelectedValue().getJugadores());
			}
		});
		
		JScrollPane pana = new JScrollPane(lista);
		this.add(pana, BorderLayout.CENTER);
	}
	
	public TiendaEquipos(TiendaListaComprar listaComprar, List<Equipo> equipos) {
		super();
		initComponents();
		
		this.listaComprar = listaComprar;
		ponerEquipos(equipos);
	}
	
	public void ponerEquipos(List<Equipo> equipos) {
		for(Equipo equipo : equipos) {
			dataModel.addElement(equipo);
		}
	}
}
