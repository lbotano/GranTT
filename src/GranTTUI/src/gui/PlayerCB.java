/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import grantt.Jugador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.event.ListDataListener;

/**
 *
 * @author usuario
 */
public class PlayerCB extends JComboBox {
    
    private Jugador.Posiciones posicion;
    public Editor owner;
    
    public PlayerCB(Jugador.Posiciones p, Editor owner) {
        this.owner = owner;
        this.setPosicion(p);
        
    }
    
    public Jugador.Posiciones getPosicion() {
        return this.posicion;
    }
    
    public void setPosicion(Jugador.Posiciones p) {
        this.posicion = p;
    }
    
    public void cargarJugadores(ArrayList<Jugador> jugadores) {
        for(int i = 0; i < jugadores.size(); i++) {
            if(jugadores.get(i).getPosicion() == this.getPosicion()) {
                this.addItem(jugadores.get(i).getNombre());
            }
        } 
    }
    private void initEvents() {
        this.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
                    owner.alSeleccionar((Jugador)getSelectedItem());
                }
            }
        );
    }
}
