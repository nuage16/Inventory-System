package Home;

import java.awt.EventQueue;
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
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;

import java.awt.Font;

import DAO.InventoryDao;
import Beans.Inventory;
import Extras.ExtraMethods;
import Home.InventoryWin;

import java.sql.Date;

import Extras.BtnColor;

public class EditProductFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtProdCode;
	private JTextField txtProdName;
	private JTextField txtProdPrice;
	private JTextField txtMarkup;
	private JTextField txtProdBrand;
	private JTextField txtCode;
	private JTextArea txtProdDesc;
	private JComboBox comboCateg;
	private JTextField txtSellPrice;

		
	public EditProductFrame() {
		setTitle("EDIT PRODUCT");
		setBackground(Color.BLACK);
		setBounds(500, 200, 380, 240);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 102, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		contentPane.setLayout(new CardLayout(0, 0));
		
		CardLayout cardLayout = (CardLayout)(contentPane.getLayout());
		
		JPanel SPanel = new JPanel();
		SPanel.setSize(386, 546);
		SPanel.setBackground(new Color(51, 102, 153));
		contentPane.add(SPanel, "spane");
		SPanel.setLayout(null);
		
		JPanel RPanel = new JPanel();
		RPanel.setBackground(new Color(51, 102, 153));
		RPanel.setSize(386, 546);
		RPanel.setLayout(null);
		setVisible(true);
		
		JScrollPane scpnRPanel = new JScrollPane(RPanel);
		scpnRPanel.setBorder(null);
		contentPane.add(scpnRPanel, "rpane");
		
		JLabel lblSearchForA = new JLabel("Please specify the product to be modified.");
		lblSearchForA.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearchForA.setForeground(Color.WHITE);
		lblSearchForA.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSearchForA.setBounds(0, 0, 354, 47);
		SPanel.add(lblSearchForA);
		
		txtCode = new JTextField();
		txtCode.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));
		txtCode.setBounds(53, 78, 250, 30);
		ExtraMethods.txtPlaceHolder(txtCode, " Code");
		SPanel.add(txtCode);
		txtCode.setColumns(10);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), Color.DARK_GRAY));
		btnClear.setBackground(new Color(0, 0, 51));
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnClear.setForeground(Color.WHITE);
		btnClear.addMouseListener(new BtnColor(btnClear));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExtraMethods.txtPlaceHolder(txtCode, " Code");
			}
		});
		btnClear.setBounds(133, 135, 80, 30);
		SPanel.add(btnClear);
		
		JLabel lblItemId = new JLabel("Item Code");
		lblItemId.setForeground(Color.WHITE);
		lblItemId.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblItemId.setBounds(54, 58, 81, 16);
		SPanel.add(lblItemId);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), Color.DARK_GRAY));
		btnSearch.setBackground(new Color(0, 0, 51));
		btnSearch.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSearch.setForeground(Color.WHITE);
		btnSearch.addMouseListener(new BtnColor(btnSearch));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object obj = null;
				obj = InventoryDao.searchRecord(txtCode.getText());
				if(obj!=null) {
					setBounds(500, 100, 380, 540);
					
					cardLayout.show(contentPane, "rpane");
					txtProdCode.setText(InventoryDao.searchRecord(txtCode.getText()).getId());
					txtProdName.setText(InventoryDao.searchRecord(txtCode.getText()).getName());
					txtProdBrand.setText(InventoryDao.searchRecord(txtCode.getText()).getBrand());
					txtProdDesc.setText(InventoryDao.searchRecord(txtCode.getText()).getDesc());
					txtProdPrice.setText(String.valueOf(InventoryDao.searchRecord(txtCode.getText()).getCost()));
					txtMarkup.setText(String.valueOf(InventoryDao.searchRecord(txtCode.getText()).getMarkup()));
					comboCateg.setSelectedItem(InventoryDao.searchRecord(txtCode.getText()).getCateg());
				}
				else
					JOptionPane.showMessageDialog(contentPane, "No record is found.");
					
			}
		});
		btnSearch.setBounds(223, 135, 80, 30);
		SPanel.add(btnSearch);
		
		
		//----------------------------------------------------------------------------------------------
				
		txtProdCode = new JTextField();
		txtProdCode.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));
		txtProdCode.setBounds(54, 158, 250, 30);
		RPanel.add(txtProdCode);
		txtProdCode.setColumns(10);
		
		txtProdName = new JTextField();
		txtProdName.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));
		txtProdName.setColumns(10);
		txtProdName.setBounds(54, 220, 250, 30);
		RPanel.add(txtProdName);
		
		JLabel lblProductName = new JLabel("Product Code");
		lblProductName.setForeground(Color.WHITE);
		lblProductName.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblProductName.setBounds(55, 136, 94, 23);
		RPanel.add(lblProductName);
		
		JLabel lblProductBrand = new JLabel("Product Brand");
		lblProductBrand.setForeground(Color.WHITE);
		lblProductBrand.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblProductBrand.setBounds(55, 81, 166, 23);
		RPanel.add(lblProductBrand);
		
		JLabel lblProductName_1 = new JLabel("Product Name");
		lblProductName_1.setForeground(Color.WHITE);
		lblProductName_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblProductName_1.setBounds(55, 198, 94, 23);
		RPanel.add(lblProductName_1);
		
		JLabel lblProductCategory = new JLabel("Product Category");
		lblProductCategory.setForeground(Color.WHITE);
		lblProductCategory.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblProductCategory.setBounds(55, 21, 128, 23);
		RPanel.add(lblProductCategory);
		
		comboCateg = new JComboBox();
		comboCateg.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.LIGHT_GRAY, Color.DARK_GRAY));
		comboCateg.setBounds(54, 43, 120, 28);
		RPanel.add(comboCateg);
		
		comboCateg.addItem("ELiquid");
		comboCateg.addItem("Accesories");
		comboCateg.addItem("Mods");
		comboCateg.addItem("Atomizers");

		txtProdPrice = new JTextField();
		txtProdPrice.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));
		txtProdPrice.setColumns(10);
		txtProdPrice.setBounds(54, 376, 95, 30);
		txtProdPrice.setEditable(false);
		RPanel.add(txtProdPrice);
		
		JLabel lblProductPrice = new JLabel("Product Price");
		lblProductPrice.setForeground(Color.WHITE);
		lblProductPrice.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblProductPrice.setBounds(55, 353, 94, 23);
		RPanel.add(lblProductPrice);
		
		txtMarkup = new JTextField();
		txtMarkup.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));
		txtMarkup.setColumns(10);
		txtMarkup.setBounds(166, 376, 88, 30);
		ExtraMethods.txtPlaceHolder(txtMarkup, " Markup");
		RPanel.add(txtMarkup);
		
		JLabel lblMarkup = new JLabel("Markup (%)");
		lblMarkup.setForeground(Color.WHITE);
		lblMarkup.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMarkup.setBounds(167, 353, 94, 23);
		RPanel.add(lblMarkup);
		
		
		JButton btnCreate = new JButton("Create");
		btnCreate.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), Color.DARK_GRAY));
		btnCreate.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCreate.setForeground(Color.WHITE);
		btnCreate.setBackground(new Color(0, 0, 51));
		btnCreate.setBounds(224, 440, 80, 30);
		btnCreate.addMouseListener(new BtnColor(btnCreate));
		RPanel.add(btnCreate);
		
		txtProdBrand = new JTextField();
		txtProdBrand.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));
		txtProdBrand.setColumns(10);
		txtProdBrand.setBounds(54, 102, 250, 30);
		RPanel.add(txtProdBrand);
		
		txtProdDesc = new JTextArea();
		txtProdDesc.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtProdDesc.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));
		txtProdDesc.setBounds(54, 282, 250, 60);
		RPanel.add(txtProdDesc);
		
		JLabel lblProductDescription = new JLabel("Product Description");
		lblProductDescription.setForeground(Color.WHITE);
		lblProductDescription.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblProductDescription.setBounds(55, 260, 143, 23);
		RPanel.add(lblProductDescription);
		
		JButton btnClearAll = new JButton("Clear");
		btnClearAll.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), Color.DARK_GRAY));
		btnClearAll.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnClearAll.setForeground(Color.WHITE);
		btnClearAll.setBackground(new Color(0, 0, 51));
		btnClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnClearAll.setBounds(137, 440, 80, 30);
		btnClearAll.addMouseListener(new BtnColor(btnClearAll));
		RPanel.add(btnClearAll);
		
		txtSellPrice = new JTextField();
		txtSellPrice.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));
		txtSellPrice.setEditable(false);
		txtSellPrice.setColumns(10);
		txtSellPrice.setBounds(214, 42, 90, 28);
		txtSellPrice.setEditable(false);
		RPanel.add(txtSellPrice);

		JLabel label = new JLabel("Sell Price");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Tahoma", Font.BOLD, 13));
		label.setBounds(215, 21, 94, 23);
		RPanel.add(label);
		
		JButton btnNewButton_1 = new JButton("C");
		btnNewButton_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), Color.DARK_GRAY));
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBackground(new Color(0, 51, 102));
		btnNewButton_1.setMargin(new Insets(2, 2, 2, 2));
		btnNewButton_1.addMouseListener(new BtnColor(btnNewButton_1));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Double sellPrice, markup;
				markup = Double.parseDouble(txtMarkup.getText()) / 100;
				sellPrice = Double.parseDouble(txtProdPrice.getText())
						+ Double.parseDouble(txtProdPrice.getText()) * markup;
				txtSellPrice.setText(String.valueOf(sellPrice));
			}
		});
		btnNewButton_1.setBounds(276, 376, 29, 29);
		RPanel.add(btnNewButton_1);
				
		
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Inventory u = new Inventory();
				u.setId(txtProdCode.getText());
				u.setName(txtProdName.getText());
				u.setBrand(txtProdBrand.getText());
				u.setCateg(String.valueOf(comboCateg.getSelectedItem()));
				u.setDesc(txtProdDesc.getText());
				u.setCost(Double.parseDouble(txtProdPrice.getText()));
				u.setMarkup(Double.parseDouble(txtMarkup.getText()));
				u.setSell( Double.parseDouble(txtProdPrice.getText()) + Double.parseDouble(txtProdPrice.getText()) * (Double.parseDouble(txtMarkup.getText()) / 100));
				int stat=0;
				stat = InventoryDao.update(u);
				
				dispose();
			}
		});
		
	}

}