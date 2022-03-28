package Extras;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LeftColor extends MouseAdapter{
	JButton btnMain = new JButton();
	JButton btn2 = new JButton();
	JButton btn3 = new JButton();
	JButton btn4 = new JButton();
	JButton btn5 = new JButton();
	JPanel paneMain = new JPanel();
	JPanel pane2 = new JPanel();
	JPanel pane3 = new JPanel();
	JPanel pane4 = new JPanel();
	JPanel pane5 = new JPanel();
	
	JButton btemp = new JButton();
	JPanel ptemp = new JPanel();
	
	public LeftColor(JButton btnMain,JButton btn2,JButton btn3,JButton btn4,JButton btn5,JPanel paneMain,JPanel pane2,JPanel pane3,JPanel pane4,JPanel pane5) {
		this.btnMain = btnMain;
		this.btn2 = btn2;
		this.btn3 = btn3;
		this.btn4 = btn4;
		this.btn5 = btn5;
		this.paneMain = paneMain;
		this.pane2 = pane2;
		this.pane3 = pane3;
		this.pane4 = pane4;
		this.pane5 = pane5;
		btemp.setBackground(Color.DARK_GRAY);
		ptemp.setBackground(Color.LIGHT_GRAY);

	}
	@Override
	public void mouseClicked(MouseEvent e) {
		btnMain.setBackground(btemp.getBackground());
		paneMain.setBackground(ptemp.getBackground());
		btn2.setBackground(Color.BLACK);
		pane2.setBackground(Color.BLACK);
		btn3.setBackground(Color.BLACK);
		pane3.setBackground(Color.BLACK);
		btn4.setBackground(Color.BLACK);
		pane4.setBackground(Color.BLACK);
		btn5.setBackground(Color.BLACK);
		pane5.setBackground(Color.BLACK);
	}
}
