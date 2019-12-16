package l.lacordeon;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class LAcordeonHeader extends JPanel{
	private LAcordeonItem item;
	private BorderLayout layout;
	
	public LAcordeonHeader(LAcordeonItem item) {
		super();
		this.item = item;
		
		layout = new BorderLayout();
		this.setLayout(layout);
		
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				item.alternarActivado();
			}
		});
	}
	
	public LAcordeonHeader(LAcordeonItem item, JPanel panel) {
		this(item);
		this.setHeaderPanel(panel);
	}
	
	public void setHeaderPanel(JPanel panel) {
		this.removeAll();
		this.add(panel, BorderLayout.CENTER);
	}
}
