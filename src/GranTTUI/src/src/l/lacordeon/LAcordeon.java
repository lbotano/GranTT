package l.lacordeon;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class LAcordeon extends JPanel{
	private List<LAcordeonItem> items;
	
	private GridBagLayout layout;
	private GridBagConstraints c;
	
	public LAcordeon() {
		super();
		
		items = new ArrayList<LAcordeonItem>();
		
		layout = new GridBagLayout();
		c = new GridBagConstraints();
		this.setLayout(layout);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	public void addItem(LAcordeonItem item) {
		this.add(item, c);
		items.add(item);
		c.gridy++;
	}
	
	public void desactivarTodo() {
		for(LAcordeonItem item : items) {
			item.desactivar();
		}
	}
	
	public void clear() {
		c.gridy = 0;
		this.removeAll();
	}
}
