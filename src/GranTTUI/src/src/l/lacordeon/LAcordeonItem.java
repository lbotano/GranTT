package l.lacordeon;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import javax.swing.border.BevelBorder;

public class LAcordeonItem extends JPanel{
	private LAcordeon acordeon;
	private LAcordeonHeader header;
	private LAcordeonBody body;
	private GridBagLayout layout;
	private GridBagConstraints c;
	
	private boolean activado = false;
	
	public LAcordeonItem(LAcordeon acordeon, JPanel headerPanel) {
		layout = new GridBagLayout();
		c = new GridBagConstraints();
		this.setLayout(layout);
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		this.acordeon = acordeon;
		
		this.header = new LAcordeonHeader(this);
		this.header.add(headerPanel);
		
		this.body = new LAcordeonBody();
		this.body.setVisible(false);
		
		c.fill = GridBagConstraints.BOTH;
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.weightx = 1;
		c.weighty = 0;
		this.add(this.header, c);
		
		c.gridy = 1;
		c.weighty = 1;
		this.add(this.body, c);
	}
	
	public LAcordeonBody getBody() {
		return body;
	}
	
	public void addSubItem(JPanel panel) {
		body.addItem(panel);
	}
	
	public void alternarActivado() {
		if(!activado) {
			acordeon.desactivarTodo();
		}
		body.setVisible(!activado);
		
		activado = !activado;
	}
	
	public void desactivar() {
		body.setVisible(false);
		activado = false;
	}
}
