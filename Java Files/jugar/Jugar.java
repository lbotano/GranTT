/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jugar;

import grantt.BaseDeDatos;
import grantt.Ocurrencia;
import grantt.Partido;
import l.lacordeon.LAcordeon;
import l.lacordeon.LAcordeonItem;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.*;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
/**
 *
 * @author usuario
 */
public final class Jugar extends JPanel {
	private BorderLayout layout;
	private LAcordeon partidosAcordeon;
    
    public Jugar(){
        super();
        
        layout = new BorderLayout(25, 25);
        this.setLayout(layout);
        
        initComponents();
    }
      
    public void update() {
    	partidosAcordeon.clear();
    	// Añade los partidos al acordeón
    	List<Partido> partidosJugados = BaseDeDatos.obtenerPartidosJugados();
    	for(Partido p : partidosJugados) {
    		// Pone la cabecera del partido
    		String headerString =
    			p.getEquipoLocal().getNombre() + " " +
    			p.getGolesLocal() + " - " +
    			p.getGolesVisitante() + " " +
    			p.getEquipoVisitante().getNombre();
    		JPanel header = new JPanel();
    		JLabel headerLabel = new JLabel(headerString);
    		headerLabel.setAlignmentX(CENTER_ALIGNMENT);
    		header.add(headerLabel);
    		LAcordeonItem item = new LAcordeonItem(partidosAcordeon, header);
    		
    		// Pone en el cuerpo del item el día en el que se jugó el partido
    		JPanel diaPanel = new JPanel();
    		JLabel diaLabel = new JLabel("Día: " + p.getJornada());
    		diaLabel.setAlignmentX(CENTER_ALIGNMENT);
    		diaPanel.add(diaLabel);
    		
    		item.addSubItem(diaPanel);
    		
    		// Pone las ocurrencias del partido como subitems
    		for(Ocurrencia ocurrencia : BaseDeDatos.obtenerOcurrencias(p.getId())) {
    			String ocurrenciaString = "";
				switch (ocurrencia.getTipo()) {
					case GOL:
						ocurrenciaString = "Gol de " + ocurrencia.getNombre();
						break;
					case LESION:
						ocurrenciaString = ocurrencia.getNombre() + " se ha lesionado";
						break;
					case AMARILLA:
						ocurrenciaString = ocurrencia.getNombre() + " recibe tarjeta amarilla";
						break;
					case ROJA:
						ocurrenciaString = ocurrencia.getNombre() + " recibe tarjeta roja";
						break;
				}
    			
    			JPanel ocurrenciaPanel = new JPanel(new BorderLayout());
    			JLabel ocurrenciaLabel = new JLabel(ocurrenciaString);
    			if(ocurrencia.getEsLocal()) {
    				ocurrenciaLabel.setHorizontalAlignment(SwingConstants.LEFT);
    			}else {
    				ocurrenciaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    			}
    			ocurrenciaPanel.add(ocurrenciaLabel, BorderLayout.CENTER);
    			
    			item.addSubItem(ocurrenciaPanel);
    		}
    		
    		// Añade el partido al acordeon
    		partidosAcordeon.addItem(item);
    		
    	}
    	
    	List<Partido> partidosPendientes = BaseDeDatos.obtenerPartidosPendientes();
    	for(Partido p : partidosPendientes) {
    		// Pone la cabecera del partido
    		String headerString =
    			p.getEquipoLocal().getNombre() + " - " +
    			p.getEquipoVisitante().getNombre();
    		JPanel header = new JPanel();
    		JLabel headerLabel = new JLabel(headerString);
    		headerLabel.setAlignmentX(CENTER_ALIGNMENT);
    		header.add(headerLabel);
    		LAcordeonItem item = new LAcordeonItem(partidosAcordeon, header);
    		
    		// Pone en el cuerpo del item el día en el que se jugó el partido
    		JPanel diaPanel = new JPanel();
    		JLabel diaLabel = new JLabel("Día: " + p.getJornada());
    		diaLabel.setAlignmentX(CENTER_ALIGNMENT);
    		diaPanel.add(diaLabel);
    		
    		item.addSubItem(diaPanel);
    		
    		// Añade el partido al acordeon
    		partidosAcordeon.addItem(item);
    		
    	}
    }
       
    private void initComponents(){
    	
    	partidosAcordeon = new LAcordeon();
    	JScrollPane scrollPartidos = new JScrollPane(partidosAcordeon);
    	
    	update();
    	
    	this.add(scrollPartidos, BorderLayout.CENTER);
    }
}
