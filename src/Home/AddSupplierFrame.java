package Home;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import DAO.InventoryDao;
import DAO.SupplierDao;
import Beans.Inventory;
import Beans.Supplier;
import Extras.ExtraMethods;
import Home.InventoryWin;
import Extras.BtnColor;

import java.sql.Date;

public class AddSupplierFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtSupAddr;
	private JTextField txtSupNum;
	private JTextField txtSupName;
	private JTextArea txtSupDesc;
	private Date format;
	private Object[] obj;
	private JTextField txtCode;
	
	public AddSupplierFrame() {
		setTitle("NEW SUPPLIER");
		setBackground(Color.BLACK);
		setBounds(500, 90, 380, 546);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 153, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		contentPane.setLayout(new CardLayout(0, 0));
		
		CardLayout cardLayout = (CardLayout)(contentPane.getLayout());
		
		JPanel RPanel = new JPanel();
		RPanel.setBackground(new Color(204, 153, 51));
		contentPane.add(RPanel, "rpane");
		RPanel.setLayout(null);
		setVisible(true);
		
		
		//-------------------------------------------------------------------------------------
		
		txtCode = new JTextField();
		txtCode.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));
		txtCode.setColumns(10);
		txtCode.setBounds(53, 57, 250, 30);
		ExtraMethods.txtPlaceHolder(txtCode, " Code");
		RPanel.add(txtCode);
		
		JLabel lblCode = new JLabel("Supplier Code");
		lblCode.setForeground(Color.WHITE);
		lblCode.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCode.setBounds(54, 36, 166, 23);
		RPanel.add(lblCode);
		
		txtSupAddr = new JTextField();
		txtSupAddr.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));
		txtSupAddr.setBounds(53, 180, 250, 29);
		ExtraMethods.txtPlaceHolder(txtSupAddr, " Address");
		RPanel.add(txtSupAddr);
		txtSupAddr.setColumns(10);
		
		txtSupNum = new JTextField();
		txtSupNum.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));
		txtSupNum.setColumns(10);
		txtSupNum.setBounds(54, 242, 250, 29);
		ExtraMethods.txtPlaceHolder(txtSupNum, " Contact No.");
		RPanel.add(txtSupNum);
		
		JLabel lblProductName = new JLabel("Address");
		lblProductName.setForeground(Color.WHITE);
		lblProductName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblProductName.setBounds(54, 158, 94, 23);
		RPanel.add(lblProductName);
		
		JLabel lblProductBrand = new JLabel("Supplier Name");
		lblProductBrand.setForeground(Color.WHITE);
		lblProductBrand.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblProductBrand.setBounds(54, 97, 166, 23);
		RPanel.add(lblProductBrand);
		
		JLabel lblProductName_1 = new JLabel("Contact Number");
		lblProductName_1.setForeground(Color.WHITE);
		lblProductName_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblProductName_1.setBounds(54, 220, 121, 23);
		RPanel.add(lblProductName_1);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), Color.DARK_GRAY));
		btnConfirm.setMargin(new Insets(2, 2, 2, 2));
		btnConfirm.setForeground(Color.WHITE);
		btnConfirm.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnConfirm.setBackground(new Color(102, 51, 0));
		btnConfirm.setBounds(223, 444, 80, 30);
		btnConfirm.addMouseListener(new BtnColor(btnConfirm));
		RPanel.add(btnConfirm);
		
		txtSupName = new JTextField();
		txtSupName.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));
		txtSupName.setColumns(10);
		txtSupName.setBounds(53, 118, 250, 30);
		ExtraMethods.txtPlaceHolder(txtSupName, " Name/Company");
		RPanel.add(txtSupName);
		
		txtSupDesc = new JTextArea();
		txtSupDesc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));
		txtSupDesc.setBounds(53, 304, 250, 70);
		ExtraMethods.txtPlaceHolder(txtSupDesc, " Description");
		RPanel.add(txtSupDesc);
		
		JLabel lblProductDescription = new JLabel("Other Information");
		lblProductDescription.setForeground(Color.WHITE);
		lblProductDescription.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblProductDescription.setBounds(54, 282, 121, 23);
		RPanel.add(lblProductDescription);
		
		JButton btnClearAll = new JButton("Clear");
		btnClearAll.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), Color.DARK_GRAY));
		btnClearAll.setForeground(Color.WHITE);
		btnClearAll.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnClearAll.setBackground(new Color(102, 51, 0));
		btnClearAll.addMouseListener(new BtnColor(btnClearAll));
		btnClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExtraMethods.txtPlaceHolder(txtCode, " Code");
				ExtraMethods.txtPlaceHolder(txtSupName, " Name/Company");
				ExtraMethods.txtPlaceHolder(txtSupAddr, " Address");
				ExtraMethods.txtPlaceHolder(txtSupNum, " Contact No.");
				ExtraMethods.txtPlaceHolder(txtSupDesc, " Description");
				
			}
		});
		btnClearAll.setBounds(133, 444, 80, 30);
		RPanel.add(btnClearAll);
		
		
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object obj = null;
				obj = SupplierDao.searchRecord(Integer.parseInt(txtCode.getText()));
				if(obj==null) {
					Supplier u = new Supplier();
					u.setCode(Integer.parseInt(txtCode.getText()));
					u.setName(txtSupName.getText());
					u.setAddress(txtSupAddr.getText());
					u.setContact(txtSupNum.getText());
					u.setInfo(txtSupDesc.getText());
				
					int stat = 0;
					stat = SupplierDao.save(u);
					SupplierWin.query();
					dispose();

				}
				else{
					JOptionPane.showMessageDialog(contentPane, "Code already exist.");
				}
			}
		});
		
	}
}