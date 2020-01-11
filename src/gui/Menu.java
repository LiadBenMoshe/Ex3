package gui;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * Creating menu - with custom String input
 * used only in the Graph_GUI class 
 * @author ben itzhak
 * @author liad ben moshe
 */
public class Menu {
	
	protected Menu(String name) {
		setMenu(name);
	}	
	protected JMenu getMenu() {
		return _menu;
	}
	protected void addItem(String Iname) {
		this.getMenu().add(new JMenuItem(Iname));
	}

	
	/** private data  */
	private void setMenu(String name) {
		this._menu = new JMenu(name);
	}
	
	private JMenu _menu;
	
}
