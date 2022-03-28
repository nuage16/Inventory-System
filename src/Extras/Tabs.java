// this class is for the tab panel-buttons changer colors when clicked
package Extras;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

public class Tabs extends MouseAdapter {
	JPanel main = new JPanel();
	//JPanel btn1 = new JPanel();
	JPanel btn2 = new JPanel();
	JPanel btn3 = new JPanel();
	JPanel btn4 = new JPanel();
	JPanel btn5 = new JPanel();
	JPanel btn6 = new JPanel();
	int c1, c2, c3;

	public Tabs(int c1, int c2, int c3, JPanel main, JPanel btn2, JPanel btn3, JPanel btn4, JPanel btn5, JPanel btn6) {
		this.c1 = c1;
		this.c2 = c2;
		this.c3 = c3;
		this.main = main;
		this.btn2 = btn2;
		this.btn3 = btn3;
		this.btn4 = btn4;
		this.btn5 = btn5;
		this.btn6 = btn6;
	}
	public Tabs(int c1, int c2, int c3, JPanel main, JPanel btn2) {
		this.c1 = c1;
		this.c2 = c2;
		this.c3 = c3;
		this.main = main;
		this.btn2 = btn2;
	}

	public void mouseClicked(MouseEvent e) {
		main.setBackground(new Color(c1, c2, c3));
		btn2.setBackground(new Color(51, 51, 51));
		btn3.setBackground(new Color(51, 51, 51));
		btn4.setBackground(new Color(51, 51, 51));
		btn5.setBackground(new Color(51, 51, 51));
		btn6.setBackground(new Color(51, 51, 51));
		// border
		main.setBorder(new MatteBorder(3, 3, 0, 3, (Color.BLACK)));
		btn2.setBorder(new MatteBorder(0, 0, 3, 1, (Color.BLACK)));
		btn3.setBorder(new MatteBorder(0, 0, 3, 1, (Color.BLACK)));
		btn4.setBorder(new MatteBorder(0, 0, 3, 1, (Color.BLACK)));
		btn5.setBorder(new MatteBorder(0, 0, 3, 1, (Color.BLACK)));
		btn6.setBorder(new MatteBorder(0, 0, 3, 1, (Color.BLACK)));

	}
}