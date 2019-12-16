package gui;

import grantt.Jugador;
import grantt.BaseDeDatos;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Editor extends JPanel {
    
    public Dimension size;
    
    //public SpringLayout sl; // para ubicar los combobox
    public PlayerCB[] del = { 
        new PlayerCB(Jugador.Posiciones.DELANTERO, this), 
        new PlayerCB(Jugador.Posiciones.DELANTERO, this)
    };
    
    public PlayerCB[] med = { 
        new PlayerCB(Jugador.Posiciones.MEDIO_CAMPISTA, this),
        new PlayerCB(Jugador.Posiciones.MEDIO_CAMPISTA, this), 
        new PlayerCB(Jugador.Posiciones.MEDIO_CAMPISTA, this),
        new PlayerCB(Jugador.Posiciones.MEDIO_CAMPISTA, this)
    };
    public PlayerCB[] def = { 
        new PlayerCB(Jugador.Posiciones.DEFENSOR, this), 
        new PlayerCB(Jugador.Posiciones.DEFENSOR, this), 
        new PlayerCB(Jugador.Posiciones.DEFENSOR, this), 
        new PlayerCB(Jugador.Posiciones.DEFENSOR, this)
    };
    public PlayerCB arq = new PlayerCB(Jugador.Posiciones.ARQUERO, this);
    
    public List<Jugador> delanteros;
    public List<Jugador> medios;
    public List<Jugador> defensores;
    public List<Jugador> arqueros;
    
    public JButton btnGuardar;
    
    
    public Editor() {
        
        this.setLayout(null);
    
        this.initComponents();
        
        
        
        this.initEvents();
        
        
        this.add(this.arq);
        
        this.add(this.def[0]);
        this.add(this.def[1]);
        this.add(this.def[2]);
        this.add(this.def[3]);
        
        this.add(this.med[0]);
        this.add(this.med[1]);
        this.add(this.med[2]);
        this.add(this.med[3]);
        
        this.add(this.del[0]);
        this.add(this.del[1]);
        
        this.add(this.btnGuardar);
        
        
        
    }
    
    public void getJugadores() {
        this.actualizarJugadores(BaseDeDatos.obtenerJugadoresEquipo());
    }
    
    public void actualizarJugadores(List<Jugador> jugadores) {

        //clear de listas
        this.delanteros.clear();
        this.medios.clear();
        this.defensores.clear();
        this.arqueros.clear();
        
        //clear de CBs
        for(int i = 0; i < 4; i++) {
            if(i < 1) {
                this.arq.removeAllItems();
            }
            if(i < 2) {
                this.del[i].removeAllItems();
            }
            this.def[i].removeAllItems();
            this.med[i].removeAllItems();
        }
        
        for(int i = 0; i < jugadores.size(); i++) {
            switch(jugadores.get(i).getPosicion()) {
                case DELANTERO:
                    this.delanteros.add(jugadores.get(i));
                    break;
                case MEDIO_CAMPISTA:
                    this.medios.add(jugadores.get(i));
                    break;
                case DEFENSOR:
                    this.defensores.add(jugadores.get(i));
                    break;
                default:
                    this.arqueros.add(jugadores.get(i));
                    break;
            }
        }
    }
    
    public void ActualizarCombos() {
        
        this.getJugadores();
        //agrego los jugadores a sus respectivos combos
        //delanteros
        
        //de la lista a los cb
        for(int i = 0; i < this.delanteros.size(); i++) { 
            for(int j = 0; j < this.del.length; j++) {
                this.del[j].addItem(this.delanteros.get(i));
            }
        }
        //defensores
        for(int i = 0; i < this.defensores.size(); i++) { 
            for(int j = 0; j < this.def.length; j++) {
                this.def[j].addItem(this.defensores.get(i));
            }
        }
        //medios
        for(int i = 0; i < this.medios.size(); i++) { 
            for(int j = 0; j < this.med.length; j++) {
                this.med[j].addItem(this.medios.get(i));
            }
        }
        //arqueros
        for(int i = 0; i < this.arqueros.size(); i++) { 
            this.arq.addItem(this.delanteros.get(i));
        }
    }
    
    public void alSeleccionar(Jugador j) {
        PlayerCB[] target;
        switch (j.getPosicion()) {
            case DELANTERO:
                target = this.del;
                break;
            case MEDIO_CAMPISTA:
                target = this.med;
                break;
            case DEFENSOR:
                target = this.def;
                break;
            default: 
                target = this.def;
                break;
        }
        //una vez que obtengo la lista a comparar comparo
        for(int i = 0; i < target.length; i++) {
            //si lo encuentro en otro combobox lo deselecciono :)
            if(target[i].getSelectedItem() == j) {
                target[i].setSelectedIndex(-1);
            }
        }
    }
    
    private void initComponents() {
        
        //inicializo las listas
        this.delanteros = new ArrayList();
        this.medios = new ArrayList();
        this.defensores = new ArrayList();
        this.arqueros = new ArrayList();
        this.btnGuardar = new JButton("Guardar Cambios");
        
        this.size = new Dimension(
            Toolkit.getDefaultToolkit().getScreenSize().width / 20 * 18 / 20 * 18,
            Toolkit.getDefaultToolkit().getScreenSize().height / 20 * 17
        );

        
        this.setBackground(new Color(16, 156, 14));
        
        
        this.arq.setLocation(0, this.size.height / 2 - 10);
        this.arq.setSize(100, 20);
        this.arq.setEditable(false);
        
        //defensa de arriba
        this.def[0].setLocation(this.size.width / 8 * 2, this.size.height / 8 - 10);
        this.def[0].setSize(100, 20);
        this.def[0].setEditable(false);
        
        //defensa medio-superior
        this.def[1].setLocation(this.size.width / 16 * 3, this.size.height / 16 * 5);
        this.def[1].setSize(100, 20);
        this.def[1].setEditable(false);
        
        //defensa medio-inferior
        this.def[2].setLocation(this.size.width / 16 * 3, this.size.height / 16 * 11);
        this.def[2].setSize(100, 20);
        this.def[2].setEditable(false);
        
        //defensa inferior
        this.def[3].setLocation(this.size.width / 8 * 2, this.size.height / 8 * 7 - 10);
        this.def[3].setSize(100, 20);
        this.def[3].setEditable(false);
        
        
        //MEDIOS<<<<<<<<<<<<<<<<<<<<
        
        //medio superior
        this.med[0].setLocation(this.size.width / 16 * 8, this.size.height / 8 - 10);
        this.med[0].setSize(100, 20);
        this.med[0].setEditable(false);
        
        //medio medio-superior
        this.med[1].setLocation(this.size.width / 16 * 7, this.size.height / 16 * 5);
        this.med[1].setSize(100, 20);
        this.med[1].setEditable(false);
        
        
        //medio medio-inferior
        this.med[2].setLocation(this.size.width / 16 * 7, this.size.height / 16 * 11);
        this.med[2].setSize(100, 20);
        this.med[2].setEditable(false);
        
        //medio inferior
        this.med[3].setLocation(this.size.width / 16 * 8, this.size.height / 8 * 7 - 10);
        this.med[3].setSize(100, 20);
        this.med[3].setEditable(false);
        
        
        //DELANTEROS<<<<<<<<<<<<<<<<<<<<<<
        
        //delantero superior
        this.del[0].setLocation(this.size.width / 16 * 11, this.size.height / 8 * 2- 10);
        this.del[0].setSize(100, 20);
        this.del[0].setEditable(false);
        
        //delantero superior
        this.del[1].setLocation(this.size.width / 16 * 11, this.size.height / 8 * 6 - 10);
        this.del[1].setSize(100, 20);
        this.del[1].setEditable(false);

        
        this.btnGuardar.setLocation(this.size.width - 200, this.size.height - 60);
        this.btnGuardar.setSize(200, 20);
        
        
        //obtengo los Jugadores
        this.actualizarJugadores(BaseDeDatos.obtenerJugadoresEquipo());
        
        //actualizo los comboboxes
        this.ActualizarCombos();
    }
    
    private void initEvents() {
    
    }
    
}
