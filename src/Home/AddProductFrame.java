package Home;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;

import DAO.InventoryDao;
import Beans.Inventory;
import Extras.ExtraMethods;
import Home.DBConnection;
import Home.InventoryWin;
import Extras.BtnColor;

import java.sql.Date;

public class AddProductFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtProdCode;
	private JTextField txtProdName;
	private JTextField txtMarkup;
	private JTextField txtProdBrand;
	private JTextArea txtProdDesc; 
	
	Double sellPrice, markup;	
	
	public AddProductFrame() {
		setTitle("ADD NEW PRODUCT");
		setBackground(Color.BLACK);
		setBounds(500, 90, 380, 546);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 102, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		
		txtProdCode = new JTextField();
		txtProdCode.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtProdCode.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));
		txtProdCode.setBounds(62, 166, 250, 29);
		ExtraMethods.txtPlaceHolder(txtProdCode, " Product Code");
		contentPane.add(txtProdCode);
		txtProdCode.setColumns(10);
		
		txtProdName = new JTextField();
		txtProdName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtProdName.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));
		txtProdName.setColumns(10);
		txtProdName.setBounds(62, 228, 250, 29);
		ExtraMethods.txtPlaceHolder(txtProdName, " Name");
		contentPane.add(txtProdName);
		
		JLabel lblProductName = new JLabel("Product Code");
		lblProductName.setForeground(new Color(255, 255, 255));
		lblProductName.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblProductName.setBounds(63, 144, 94, 23);
		contentPane.add(lblProductName);
		
		JLabel lblProductBrand = new JLabel("Product Brand");
		lblProductBrand.setForeground(new Color(255, 255, 255));
		lblProductBrand.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblProductBrand.setBounds(63, 83, 166, 23);	
		contentPane.add(lblProductBrand);
		
		JLabel lblProductName_1 = new JLabel("Product Name");
		lblProductName_1.setForeground(new Color(255, 255, 255));
		lblProductName_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblProductName_1.setBounds(63, 206, 94, 23);
		contentPane.add(lblProductName_1);
		
		JLabel lblProductCategory = new JLabel("Product Category");
		lblProductCategory.setForeground(new Color(255, 255, 255));
		lblProductCategory.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblProductCategory.setBounds(63, 23, 131, 23);
		contentPane.add(lblProductCategory);
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboBox_1.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.LIGHT_GRAY, Color.DARK_GRAY));
		comboBox_1.setBounds(62, 45, 250, 27);
		contentPane.add(comboBox_1);
		
		comboBox_1.addItem("ELiquid");
		comboBox_1.addItem("Accesories");
		comboBox_1.addItem("Mods");
		comboBox_1.addItem("Atomizers");
		
		txtMarkup = new JTextField();
		txtMarkup.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));
		txtMarkup.setColumns(10);
		txtMarkup.setBounds(62, 388, 250, 29);
		ExtraMethods.txtPlaceHolder(txtMarkup, " Markup");
		contentPane.add(txtMarkup);
		
		JLabel lblMarkup = new JLabel("Markup (%)");
		lblMarkup.setForeground(new Color(255, 255, 255));
		lblMarkup.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMarkup.setBounds(63, 365, 249, 23);
		contentPane.add(lblMarkup);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCreate.setForeground(Color.WHITE);
		btnCreate.setBackground(new Color(0, 0, 51));
		btnCreate.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), Color.DARK_GRAY));
		btnCreate.setBounds(232, 451, 80, 30);
		btnCreate.addMouseListener(new BtnColor(btnCreate));
		contentPane.add(btnCreate);
		
		txtProdBrand = new JTextField();
		txtProdBrand.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtProdBrand.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));
		txtProdBrand.setColumns(10);
		txtProdBrand.setBounds(62, 104, 250, 29);
		ExtraMethods.txtPlaceHolder(txtProdBrand, " Brand");
		contentPane.add(txtProdBrand);
		
		txtProdDesc = new JTextArea();
		txtProdDesc.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtProdDesc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));
		txtProdDesc.setBounds(62, 291, 249, 63);
		contentPane.add(txtProdDesc);
		
		JLabel lblProductDescription = new JLabel("Product Description");
		lblProductDescription.setForeground(new Color(255, 255, 255));
		lblProductDescription.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblProductDescription.setBounds(63, 269, 146, 23);
		contentPane.add(lblProductDescription);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnClear.setForeground(Color.WHITE);
		btnClear.setBackground(new Color(0, 0, 51));
		btnClear.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), Color.DARK_GRAY));
		btnClear.setBounds(142, 451, 80, 30);
		btnClear.addMouseListener(new BtnColor(btnClear));
		contentPane.add(btnClear);
		
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Inventory u = new Inventory();
				u.setId(txtProdCode.getText());
				u.setName(txtProdName.getText());
				u.setBrand(txtProdBrand.getText());
				u.setCateg(String.valueOf(comboBox_1.getSelectedItem()));
				u.setDesc(txtProdDesc.getText());
				u.setStock(0);
				u.setCost(0.00);
				u.setMarkup(Double.parseDouble(txtMarkup.getText()));
				u.setSell(0.00);
				u.setFlag(true);
				InventoryDao.saveEliquid(u);
				InventoryWin.query();
				dispose();
			}
		});
		
		
		
	}
}