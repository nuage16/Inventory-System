//this class makes border for tab buttons when mouse entered
package Extras;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.border.EtchedBorder;

public class TabListener extends MouseAdapter {
	JLabel lbl = new JLabel();

	public TabListener(JLabel lbl) {
		this.lbl = lbl;
	}

	public void mouseEntered(MouseEvent e) {
		lbl.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.WHITE, Color.BLACK));
	}

	public void mouseExited(MouseEvent e) {
		lbl.setBorder(null);
	}
}
