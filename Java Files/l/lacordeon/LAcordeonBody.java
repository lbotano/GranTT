package l.lacordeon;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class LAcordeonBody extends JPanel{
	BoxLayout layout;
	
	public LAcordeonBody() {
		super();
		
		layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layout);
	}
	
	public void addItem(JPanel panel) {
		this.add(panel);
	}
}
