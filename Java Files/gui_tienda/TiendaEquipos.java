package gui_tienda;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
	
		
		lista.addListSelectionListener(
			new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent arg0) {
					 JList<Equipo> l = (JList<Equipo>)arg0.getSource();
					 Equipo selected = l.getSelectedValue();
					 listaComprar.setJugadores(lista.getSelectedValue().getJugadores());
					 Updater.update();
				}
			}
		);

		
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
