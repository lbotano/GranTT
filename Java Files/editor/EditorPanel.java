package editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import grantt.BaseDeDatos;
import grantt.Jugador;

public class EditorPanel extends JPanel {
	
	private GridBagLayout gbl = new GridBagLayout();
	private GridBagConstraints gbc = new GridBagConstraints();
	public Dimension size;
	
	public StatePanel estado;
	public EditorLeftPanel panelIzquierda;
	public EditorRightPanel panelDerecha;
	public StatePanel sp;
	
	ArrayList<ItemJugador> jugadores;
	
	public EditorPanel(Dimension size) {
		super();

		this.setLayout(this.gbl);
		this.initComponents(size);
		this.initEvents();
		
		this.setBackground(Color.GRAY);
	}
	
	private void initComponents(Dimension d) {
		
		this.size = d;
		this.setMaximumSize(this.size);
		
		this.estado = new StatePanel(new Dimension(this.size.width, this.size.height / 5), this);
		this.panelIzquierda = new EditorLeftPanel(new Dimension(this.size.width / 6, this.size.height / 5 * 4), this);
		this.panelDerecha = new EditorRightPanel(new Dimension(this.size.width / 6, this.size.height / 5 * 4), this);
		this.getJugadores();
		this.jugadores = new ArrayList<ItemJugador>();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.5;
		gbc.weighty = 0.95;
		this.add(this.panelIzquierda, gbc);

		gbc.gridx = 1;
		this.add(this.panelDerecha, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weighty = 0.05;
		gbc.gridwidth = 2;
		this.add(this.estado, gbc);
	}
	
	private void initEvents() {
		this.panelIzquierda.btnQuitar.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(panelIzquierda.pfl.getSelectedIndex() > -1) {
						panelIzquierda.pfl.setSelectedIndex(-1);
						panelDerecha.prl.addJugador(panelIzquierda.pfl.quitarJugador());
						estado.actualizarValor();
					} else {
						JOptionPane.showMessageDialog(null, "Tenés que seleccionar un jugador");
					}
				}
			}
		);
		
		this.panelDerecha.btnAgregar.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(panelDerecha.prl.getSelectedIndex() > -1) {
						ItemJugador j = (ItemJugador) panelDerecha.prl.getSelectedValue();
						
						if(j.getDiasLesionado() > 0) {
							JOptionPane.showMessageDialog(null, "El jugador está lesionado");
							return;
						}
						
						if(j.getPartidosSuspendido() > 0) {
							JOptionPane.showMessageDialog(null, "El jugador está suspendido");
							return;
						}
						
						// Fijarse si tiene posiciones vacias
						Jugador.Posiciones p = j.getPosicion();
//						System.out.println("ID Del Seleccionado: " + j.getId() + " Nombre: " + j.getNombre());
						int max = 0;
						switch(p) {
							case ARQUERO:
								max = 1;
								break;
							case DELANTERO:
								max = 2;
								break;
							default:
								max = 4;
								break;
						}
						int cont = 0;
						if(panelIzquierda.pfl.model.getSize() > 0) {
							ArrayList<ItemJugador> jugadores = new ArrayList<ItemJugador>();
							
							// Agrega los jugadores a la lista para despues iterar
							for(int i = 0; i < panelIzquierda.pfl.model.getSize(); i++) {
								jugadores.add(panelIzquierda.pfl.model.getElementAt(i));
							}
							
							for(ItemJugador jugador : jugadores) {
								if(jugador.getPosicion() == p) {
									cont++;
								}
							}
						}
						if(!(cont >= max)) {
							panelDerecha.prl.setSelectedIndex(-1);
							panelIzquierda.pfl.addJugador(panelDerecha.prl.quitarJugador());
							estado.actualizarValor();
						} else {
							JOptionPane.showMessageDialog(
                                null, "No se puede mover el jugador indicado."
                            );
						}
					} else {
						JOptionPane.showMessageDialog(
                            null, "Tenés que seleccionar un jugador."
                        );
					}
				}
			}
		);
		this.estado.btnGuardar.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						if(panelIzquierda.validarEquipo()) {
							//guardo el equipo
							ArrayList<ItemJugador> jugadores = new ArrayList<ItemJugador>();
							
							for(int i = 0; i < panelIzquierda.pfl.model.getSize(); i++) {
								jugadores.add(panelIzquierda.pfl.model.getElementAt(i));
							}
							
							for(ItemJugador j : jugadores) {
								BaseDeDatos.guardarJugador(j, true);
							}
							
							//lo mismo para los suplentes
							
							for(int i = 0; i < panelDerecha.prl.model.getSize(); i++) {
								BaseDeDatos.guardarJugador(panelDerecha.prl.model.elementAt(i), false);
							}
							EditorPanel.this.getJugadores();
							EditorPanel.this.estado.actualizarValor();
						} else {
							JOptionPane.showMessageDialog(
	                            null, "No Se Pudo Guardar Su Equipo. Jugadores Invalidos."
	                        );
						}
					}
				}
		);
	}
	
	public void getJugadores() {
		this.panelIzquierda.getJugadoresTitulares();
		this.panelDerecha.getJugadoresSuplentes();
	}
}