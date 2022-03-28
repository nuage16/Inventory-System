package Home;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;
import javax.swing.JCheckBox;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;

import DAO.UserDao;
import Extras.BtnColor;

public class LogIn extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField;
	private JLabel lblMsg;
	
	// logo image
	private Image img_logo = new ImageIcon(LogIn.class.getResource("/Icons/vapeSampleLogo_B.png")).getImage().getScaledInstance(100,
				100, Image.SCALE_SMOOTH);
	//padlock icon
	private Image img_padlock = new ImageIcon(LogIn.class.getResource("/Icons/padlock.png")).getImage().getScaledInstance(33, 33, Image.SCALE_SMOOTH);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogIn frame = new LogIn();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LogIn() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 140, 536, 399);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(51, 51, 51));
		
		// added the image logo in JLabel
		JLabel lbllogo = new JLabel();
		lbllogo.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		lbllogo.setIcon(new ImageIcon(img_logo));
		lbllogo.setBounds(207, 40, 100, 100);
		
		// Container of Button (Acts as border)
		JPanel borderpanel = new JPanel();
		borderpanel.setBackground(new Color(255, 255, 255));
		borderpanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.BLACK));
		borderpanel.setBounds(109, 204, 299, 43);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(255, 255, 255),
				new Color(204, 204, 204), Color.BLACK, new Color(64, 64, 64)));
		btnNewButton.setMargin(new Insets(2, 12, 2, 12));
		btnNewButton.setIconTextGap(6);
		btnNewButton.setIcon(new ImageIcon(LogIn.class.getResource("/Icons/minikey.png")));
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setBackground(new Color(204, 204, 204));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.addMouseListener(new BtnColor(btnNewButton));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char[] input = passwordField.getPassword();
				String pass = null;
				pass = String.valueOf(input);
				
				boolean stat= UserDao.validate(pass);
				if(stat) {
					new InventoryWindow();
					lblMsg.setText("Login successful!");
					dispose();
				}
				else {
					lblMsg.setForeground(Color.RED);
					lblMsg.setText("Incorrect password!");
				}
			
			}
		});
		btnNewButton.setBounds(190, 284, 137, 35);
		contentPane.add(lbllogo);
		
		lblMsg = new JLabel("Please enter password.");
		lblMsg.setForeground(Color.WHITE);
		lblMsg.setHorizontalAlignment(SwingConstants.CENTER);
		lblMsg.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMsg.setBounds(109, 145, 299, 58);
		contentPane.add(lblMsg);
		contentPane.add(btnNewButton);
		contentPane.add(borderpanel);
		borderpanel.setLayout(null);
		
		JLabel lblpadlock = new JLabel("");
		lblpadlock.setBounds(258, 5, 33, 33);
		borderpanel.add(lblpadlock);
		lblpadlock.setIcon(new ImageIcon(img_padlock));
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.BOLD, 21));
		passwordField.setBounds(2, 2, 256, 39);
		borderpanel.add(passwordField);
		passwordField.setForeground(Color.BLACK);
		passwordField.setBackground(new Color(255, 255, 255));
		passwordField.setBorder(null);
		passwordField.setMargin(new Insets(2, 5, 2, 2));
		
		// show password checkbox save as utf-8
		JCheckBox chckbxShowpass = new JCheckBox("Show Password");
		chckbxShowpass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxShowpass.isSelected())
					passwordField.setEchoChar((char) 0);
				else
					passwordField.setEchoChar('•');
			}
		});
		chckbxShowpass.setForeground(new Color(255, 255, 255));
		chckbxShowpass.setIconTextGap(10);
		chckbxShowpass.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxShowpass.setBorder(null);
		chckbxShowpass.setBackground(new Color(51, 51, 51));
		chckbxShowpass.setBounds(112, 251, 117, 23);
		contentPane.add(chckbxShowpass);
	
	}
}
