package Home;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import Beans.User;
import DAO.UserDao;
import Extras.BtnColor;
import Extras.ExtraMethods;

public class SettingsFrame extends JFrame {

	private JPanel contentPane;

	private JPasswordField txtpPass;
	private JPasswordField txtnPass;
	private JPasswordField txtcPass;

	
	public SettingsFrame() {
		setTitle("PASSWORD");
		setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(500, 120, 380, 442);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		
		JLabel lblMsg = new JLabel();
		lblMsg.setHorizontalTextPosition(SwingConstants.CENTER);
		lblMsg.setHorizontalAlignment(SwingConstants.CENTER);
		lblMsg.setForeground(new Color(255, 0, 0));
		lblMsg.setBackground(Color.WHITE);
		lblMsg.setBounds(0, 22, 364, 30);
		contentPane.add(lblMsg);

		txtpPass = new JPasswordField();
		txtpPass.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.BLACK));
		txtpPass.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtpPass.setBounds(75, 236, 220, 30);
		contentPane.add(txtpPass);
		txtpPass.setColumns(10);

		txtnPass = new JPasswordField();
		txtnPass.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.BLACK));
		txtnPass.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtnPass.setColumns(10);
		txtnPass.setBounds(75, 172, 220, 30);
		contentPane.add(txtnPass);

		txtcPass = new JPasswordField();
		txtcPass.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.BLACK));
		txtcPass.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtcPass.setColumns(10);
		txtcPass.setBounds(75, 86, 220, 30);
		contentPane.add(txtcPass);
		
		JLabel lblNewPassword = new JLabel("New Password");
		lblNewPassword.setForeground(Color.WHITE);
		lblNewPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewPassword.setBounds(76, 150, 90, 24);
		contentPane.add(lblNewPassword);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setForeground(Color.WHITE);
		lblConfirmPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblConfirmPassword.setBounds(76, 213, 125, 24);
		contentPane.add(lblConfirmPassword);
		
		JLabel lblCurrentPassword = new JLabel("Current Password");
		lblCurrentPassword.setForeground(Color.WHITE);
		lblCurrentPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCurrentPassword.setBounds(76, 63, 125, 24);
		contentPane.add(lblCurrentPassword);
		
		JLabel lblError = new JLabel("");
		lblError.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		lblError.setBackground(Color.WHITE);
		lblError.setForeground(new Color(255, 0, 0));
		lblError.setBounds(75, 266, 220, 30);
		contentPane.add(lblError);
		
		JButton btnNewButton = new JButton("Confirm");
		btnNewButton.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				Color.BLACK, Color.DARK_GRAY));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setBackground(Color.GRAY);
		btnNewButton.addMouseListener(new BtnColor(btnNewButton));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					char[] inputPass1 = txtnPass.getPassword(); //NEW PASSWORD
					char[] inputPass2 = txtpPass.getPassword(); //TO VERIFY IF USER ENTERED THE RIGHT DESIRED PASSWORD
					char[] inputPass3 = txtcPass.getPassword(); //CURRENT PASSWORD
					String pass = null;
					String pass2 = null;
					String pass3 = null;
					
					pass = String.valueOf(inputPass1);
					pass2 = String.valueOf(inputPass2);
					pass3 = String.valueOf(inputPass3);
					
					
					//TO CHECK IF ALL FIELDS ARE FIELD OUT
					//IF AT LEAST ONE FIELD IS EMPTY:
					if(pass.isEmpty() || pass2.isEmpty() || pass3.isEmpty()) {  
						lblMsg.setText("*Please fill all fields");
						lblMsg.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
						lblError.setText("");
						txtnPass.setText(null);
						txtpPass.setText(null);
						txtcPass.setText(null);
					}
					
					else {	
						//TO CHECK IF FIRST PASSWORD FIELD MATCHES WITH THE SECOND PASSWORD FIELD
						//IF PASSWORDS DO NOT MATCH:
						if(!(pass.contentEquals(pass2))) {     
							lblMsg.setText("");
							lblError.setText("*Passwords do not match");
							lblMsg.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
							txtnPass.setText(null);
							txtpPass.setText(null);
							txtcPass.setText(null);
						}
						else {
							boolean stat= UserDao.validate(pass3);
							//TO CHECK IF THE FIRST PASSWORD FIELD MATCHES WITH THE CURRENT PASSWORD
							//IF IT MATCHES:
							if(stat ) {
								User u = new User();
								u.setPass(pass3);
								UserDao.updatePassword(u);
								lblMsg.setForeground(Color.BLACK);
								lblMsg.setText("Update Succesful !");
								txtnPass.setText(null);
								txtpPass.setText(null);
								txtcPass.setText(null);
							}
							else
								lblError.setText("");
								lblMsg.setBounds(75, 116, 130, 30);;
								lblMsg.setText("*Invalid password.");
								lblMsg.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
							}
						}
			}
						
		});
		btnNewButton.setBounds(135, 330, 100, 40);
		contentPane.add(btnNewButton);
		
		
		
	}
}
