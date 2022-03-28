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

import DAO.SupplierDao;
import Beans.Supplier;
import Extras.ExtraMethods;
import Extras.BtnColor;
import Home.InventoryWin;

import java.sql.Date;

public class EditSupplierFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtSupAddr;
	private JTextField txtSupNum;
	private JTextField txtSupName;
	private JTextField txtCode;
	private JTextArea txtSupDesc;
	private Date format;
	private Object[] obj;
	private Supplier u = null;
	
	public EditSupplierFrame() {
		setTitle("ADD NEW PRODUCT");
		setBackground(Color.BLACK);
		setBounds(500, 200, 380, 250);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 153, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		contentPane.setLayout(new CardLayout(0, 0));
		
		CardLayout cardLayout = (CardLayout)(contentPane.getLayout());
		
		JPanel SPanel = new JPanel();
		SPanel.setBorder(null);
		SPanel.setBackground(new Color(204, 153, 51));
		contentPane.add(SPanel, "spane");
		SPanel.setLayout(null);

		JPanel RPanel = new JPanel();
		RPanel.setBackground(new Color(204, 153, 51));
		contentPane.add(RPanel, "rpane");
		RPanel.setLayout(null);
		setVisible(true);

		JLabel lblSearchForA = new JLabel("Please specify the supplier to be modified.");
		lblSearchForA.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearchForA.setForeground(Color.WHITE);
		lblSearchForA.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSearchForA.setBounds(0, 0, 354, 66);
		SPanel.add(lblSearchForA);

		txtCode = new JTextField();
		txtCode.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));
		txtCode.setBounds(54, 84, 250, 33);
		ExtraMethods.txtPlaceHolder(txtCode, " Code");
		SPanel.add(txtCode);
		txtCode.setColumns(10);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), Color.DARK_GRAY));
		btnClear.setBackground(new Color(102, 51, 0));
		btnClear.setForeground(Color.WHITE);
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnClear.addMouseListener(new BtnColor(btnClear));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExtraMethods.txtPlaceHolder(txtCode, " Code");
			}
		});
		btnClear.setBounds(134, 141, 80, 30);
		SPanel.add(btnClear);

		JLabel lblItemId = new JLabel("Supplier Code");
		lblItemId.setForeground(Color.WHITE);
		lblItemId.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblItemId.setBounds(55, 62, 106, 16);
		SPanel.add(lblItemId);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), Color.DARK_GRAY));
		btnSearch.setBackground(new Color(102, 51, 0));
		btnSearch.setForeground(Color.WHITE);
		btnSearch.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSearch.addMouseListener(new BtnColor(btnSearch));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				u = SupplierDao.searchRecord(Integer.parseInt(txtCode.getText()));
				if(u!=null) {
					setBounds(500, 90, 380, 500);
					cardLayout.show(contentPane, "rpane");
					txtSupName.setText(SupplierDao.searchRecord(Integer.parseInt(txtCode.getText())).getName());
					txtSupAddr.setText(SupplierDao.searchRecord(Integer.parseInt(txtCode.getText())).getAddress());
					txtSupNum.setText(SupplierDao.searchRecord(Integer.parseInt(txtCode.getText())).getContact());
					txtSupDesc.setText(SupplierDao.searchRecord(Integer.parseInt(txtCode.getText())).getInfo());
				}
				else
					JOptionPane.showMessageDialog(contentPane, "No record is found.");
					
			}
		});
		btnSearch.setBounds(224, 141, 80, 30);
		SPanel.add(btnSearch);
		
		
		//-------------------------------------------------------------------------------------
				
		txtSupAddr = new JTextField();
		txtSupAddr.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));
		txtSupAddr.setBounds(54, 134, 250, 30);
		RPanel.add(txtSupAddr);
		txtSupAddr.setColumns(10);

		txtSupNum = new JTextField();
		txtSupNum.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));
		txtSupNum.setColumns(10);
		txtSupNum.setBounds(54, 210, 250, 30);
		RPanel.add(txtSupNum);

		JLabel lblProductName = new JLabel("Address");
		lblProductName.setForeground(Color.WHITE);
		lblProductName.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblProductName.setBounds(55, 112, 94, 23);
		RPanel.add(lblProductName);

		JLabel lblProductBrand = new JLabel("Supplier Name");
		lblProductBrand.setForeground(Color.WHITE);
		lblProductBrand.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblProductBrand.setBounds(55, 39, 166, 23);
		RPanel.add(lblProductBrand);

		JLabel lblProductName_1 = new JLabel("Contact Number");
		lblProductName_1.setForeground(Color.WHITE);
		lblProductName_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblProductName_1.setBounds(55, 188, 121, 23);
		RPanel.add(lblProductName_1);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), Color.DARK_GRAY));
		btnConfirm.setBackground(new Color(102, 51, 0));
		btnConfirm.setMargin(new Insets(2, 2, 2, 2));
		btnConfirm.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnConfirm.setForeground(Color.WHITE);
		btnConfirm.setBounds(224, 410, 80, 30);
		btnConfirm.addMouseListener(new BtnColor(btnConfirm));
		RPanel.add(btnConfirm);

		txtSupName = new JTextField();
		txtSupName.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));
		txtSupName.setColumns(10);
		txtSupName.setBounds(54, 60, 250, 30);
		RPanel.add(txtSupName);

		txtSupDesc = new JTextArea();
		txtSupDesc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));
		txtSupDesc.setBounds(54, 290, 249, 70);
		txtSupDesc.setText("asdfghh");
		RPanel.add(txtSupDesc);

		JLabel lblProductDescription = new JLabel("Other Information");
		lblProductDescription.setForeground(Color.WHITE);
		lblProductDescription.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblProductDescription.setBounds(55, 268, 121, 23);
		RPanel.add(lblProductDescription);
		
		JButton btnClearAll = new JButton("Clear");
		btnClearAll.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), Color.DARK_GRAY));
		btnClearAll.setBackground(new Color(102, 51, 0));
		btnClearAll.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnClearAll.setForeground(Color.WHITE);
		btnClearAll.addMouseListener(new BtnColor(btnClearAll));
		btnClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtSupName.setText(null);
				txtSupAddr.setText(null);
				txtSupNum.setText(null);
				txtSupDesc.setText(null);
			}
		});
		btnClearAll.setBounds(134, 410, 80, 30);
		RPanel.add(btnClearAll);
		
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				u.setName(txtSupName.getText());
				u.setAddress(txtSupAddr.getText());
				u.setContact(txtSupNum.getText());
				u.setInfo(txtSupDesc.getText());
				
				int stat=0;
				stat = SupplierDao.update(u);
				
				dispose();
			}
		});
		
	}
}