package Extras;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class BtnColor extends MouseAdapter {
		JButton btn = new JButton();
		JButton temp = new JButton();

		public BtnColor(JButton btn) {
			this.btn = btn;
			temp.setBackground(btn.getBackground());
		}

		//temp.setBackground(btn.getBackground());
		
		@Override
		public void mouseEntered(MouseEvent e) {
			btn.setBackground(Color.LIGHT_GRAY);
			btn.setForeground(Color.BLACK);
		}

		public void mouseExited(MouseEvent e) {
			btn.setBackground(temp.getBackground());
			btn.setForeground(Color.WHITE);
		}
	}