package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

/**
 * Creating buttons for panel1 - with custom String input
 * used only in the Graph_GUI class 
 * @author ben itzhak
 * @author liad ben moshe
 */
public class panel1_Button {
	protected int getheight() {
		return _height;
	}
	protected void setheight(int _height) {
		panel1_Button._height = _height;
	}
	protected JButton getbutton() {
		return _button;
	}
	protected void setbutton(JButton _button) {
		this._button = _button;
	}
	protected Color getBackColor() {
		return _dark;
	}
	
	protected panel1_Button(String name) {
		setbutton(new JButton(name));  
		this.getbutton().setBounds(0, this.getheight(), 300, 45);
		this.getbutton().setForeground(Color.WHITE);
		this.getbutton().setFont((new Font("Arial", Font.PLAIN, 17)));
		this.getbutton().setBackground(this.getBackColor());
		this.getbutton().setBorderPainted(false);
		setheight(this.getheight()+60);
	}
	
	/***** private data ***/
	private static int _height = 120;
	private JButton _button;
	private final Color _dark = new Color(44, 45, 50);
}
