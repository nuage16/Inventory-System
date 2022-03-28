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
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import DAO.InventoryDao;
import DAO.SupplierDao;
import DAO.TransDao;
import Beans.Inventory;
import Beans.Supplier;
import Beans.Transactions;
import Extras.BtnColor;
import Extras.ExtraMethods;
import Home.InventoryWin;

import java.sql.Date;
import java.text.SimpleDateFormat;


public class UpdateStocks extends JFrame {

	private JPanel contentPane;
	private JTextField txtTransCode;
	private JTextField txtCode;
	private JTextField txtPrice;
	private JTextField txtQty;
	private JButton btnSearch;
	
	private java.util.Date dateNow = new java.util.Date();
	private SimpleDateFormat hrFormat = new SimpleDateFormat("HH:mm");
	private SimpleDateFormat dayFormat = new SimpleDateFormat("MM-dd-yyyy");
	private SimpleDateFormat dayFormat2 = new SimpleDateFormat("yyyy-MM-dd");
	private JTextField txtAmount;
	
	private double totalAmount;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateStocks frame = new UpdateStocks();
					frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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
	
	public UpdateStocks() {
		setTitle("UPDATE STOCKS");
		setBounds(500, 200, 380, 240);
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 102, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		contentPane.setLayout(new CardLayout(0, 0));
		
		CardLayout cardLayout = (CardLayout)(contentPane.getLayout());
		
		JPanel SPanel = new JPanel();
		SPanel.setBackground(new Color(51, 102, 153));
		contentPane.add(SPanel, "spane");
		SPanel.setLayout(null);

		JPanel RPanel = new JPanel();
		RPanel.setBackground(new Color(51, 102, 153));
		contentPane.add(RPanel, "rpane");
		RPanel.setLayout(null);
		setVisible(true);

		txtCode = new JTextField();
		txtCode.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 102, 204), new Color(153, 204, 255),
				new Color(51, 51, 51), new Color(0, 0, 0)));
		txtCode.setBounds(54, 84, 250, 30);
		ExtraMethods.txtPlaceHolder(txtCode, " Code");
		SPanel.add(txtCode);
		txtCode.setColumns(10);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), Color.DARK_GRAY));
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnClear.setForeground(Color.WHITE);
		btnClear.setBackground(new Color(0, 0, 51));
		btnClear.addMouseListener(new BtnColor(btnClear));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExtraMethods.txtPlaceHolder(txtCode, " Code");
			}
		});
		btnClear.setBounds(127, 146, 80, 30);
		SPanel.add(btnClear);
		
		JLabel lblItemId = new JLabel("Item Code");
		lblItemId.setForeground(Color.WHITE);
		lblItemId.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblItemId.setBounds(55, 61, 82, 16);
		SPanel.add(lblItemId);
				
		btnSearch = new JButton("Search");
		btnSearch.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), Color.DARK_GRAY));
		btnSearch.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSearch.setForeground(Color.WHITE);
		btnSearch.setBackground(new Color(0, 0, 51));
		btnSearch.addMouseListener(new BtnColor(btnSearch));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object obj = null;
				
				obj = InventoryDao.searchRecord(txtCode.getText());
				if(obj!=null) {
					setBounds(500, 100, 380, 443);
					cardLayout.show(contentPane, "rpane");

				}
				else
					JOptionPane.showMessageDialog(contentPane, "No record is found.");
					
			}
		});
		btnSearch.setBounds(224, 146, 80, 30);
		SPanel.add(btnSearch);
		
		JLabel label = new JLabel("Please specify the item to be modified.");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label.setBounds(0, 0, 364, 66);
		SPanel.add(label);

		txtTransCode = new JTextField();
		txtTransCode.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 102, 204), new Color(153, 204, 255),
				new Color(102, 102, 102), new Color(0, 0, 0)));
		txtTransCode.setHorizontalAlignment(SwingConstants.TRAILING);
		txtTransCode.setColumns(10);
		txtTransCode.setBounds(40, 53, 200, 30);
		txtTransCode.setEditable(false);
		RPanel.add(txtTransCode);
		txtTransCode.setText(String.valueOf(TransDao.transTracker(1)));
		
		JLabel lblTransCode = new JLabel("Transaction Code");
		lblTransCode.setForeground(new Color(255, 255, 255));
		lblTransCode.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTransCode.setBounds(41, 30, 226, 23);
		RPanel.add(lblTransCode);
		
		JButton btnUpdate = new JButton("Confirm");
		btnUpdate.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), Color.DARK_GRAY));
		btnUpdate.setForeground(Color.WHITE);
		btnUpdate.setBackground(new Color(0, 0, 51));
		btnUpdate.setBounds(138, 334, 110, 35);
		btnUpdate.addMouseListener(new BtnColor(btnUpdate));
		RPanel.add(btnUpdate);
		
		String strHr = hrFormat.format(dateNow);
		String strDay = dayFormat.format(dateNow);
		
		JLabel lblHour = new JLabel();
		lblHour.setForeground(Color.WHITE);
		lblHour.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblHour.setVerticalAlignment(SwingConstants.TOP);
		lblHour.setText(strHr);
		lblHour.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHour.setBounds(240, 44, 89, 23);
		RPanel.add(lblHour);		
		
		JLabel lblDay = new JLabel();
		lblDay.setForeground(Color.WHITE);
		lblDay.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDay.setVerticalAlignment(SwingConstants.TOP);
		lblDay.setText(strDay);
		lblDay.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDay.setBounds(240, 66, 89, 16);
		RPanel.add(lblDay);

		txtPrice = new JTextField();
		txtPrice.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 102, 204), new Color(153, 204, 255),
				new Color(102, 102, 102), new Color(0, 0, 0)));
		txtPrice.setColumns(10);
		txtPrice.setBounds(40, 190, 130, 30);
		RPanel.add(txtPrice);

		JLabel lblSupplierCodde = new JLabel("Price Per Unit");
		lblSupplierCodde.setForeground(new Color(255, 255, 255));
		lblSupplierCodde.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSupplierCodde.setBounds(41, 167, 141, 23);
		RPanel.add(lblSupplierCodde);

		txtQty = new JTextField();
		txtQty.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 102, 204), new Color(153, 204, 255),
				new Color(102, 102, 102), new Color(0, 0, 0)));
		txtQty.setColumns(10);
		txtQty.setBounds(199, 190, 130, 30);
		RPanel.add(txtQty);

		JLabel lblQty = new JLabel("Quantity");
		lblQty.setForeground(new Color(255, 255, 255));
		lblQty.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblQty.setBounds(200, 167, 112, 23);
		RPanel.add(lblQty);
		
		JLabel lblSupplier = new JLabel("Supplier");
		lblSupplier.setForeground(new Color(255, 255, 255));
		lblSupplier.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSupplier.setBounds(41, 99, 226, 23);
		RPanel.add(lblSupplier);
		
		JComboBox cmbSupplier = new JComboBox();
		cmbSupplier.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 102, 204), new Color(153, 204, 255),
				new Color(102, 102, 102), new Color(0, 0, 0)));
		cmbSupplier.setBounds(40, 121, 200, 30);
		RPanel.add(cmbSupplier);
		
		for(int i=0; i<SupplierDao.getSupplier().size();i++) {
			cmbSupplier.addItem(SupplierDao.getSupplier().get(i).getCode());
		}
		JButton btnClrSupp = new JButton("+");
		btnClrSupp.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), Color.DARK_GRAY));
		btnClrSupp.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnClrSupp.setForeground(Color.WHITE);
		btnClrSupp.setMargin(new Insets(2, 2, 2, 2));
		btnClrSupp.setBackground(new Color(0, 51, 102));
		btnClrSupp.addMouseListener(new BtnColor(btnClrSupp));
		btnClrSupp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddSupplierFrame();
				}	
		});				
		btnClrSupp.setBounds(255, 121, 30, 30);
		RPanel.add(btnClrSupp);
		
		JButton btnClrSupp2 = new JButton("R");
		btnClrSupp2.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), Color.DARK_GRAY));
		btnClrSupp2.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnClrSupp2.setForeground(Color.WHITE);
		btnClrSupp2.setMargin(new Insets(2, 2, 2, 2));
		btnClrSupp2.setBackground(new Color(0, 51, 102));
		btnClrSupp2.addMouseListener(new BtnColor(btnClrSupp2));
		btnClrSupp2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cmbSupplier.removeAllItems();
				for(int i=0; i<SupplierDao.getSupplier().size();i++) {
					cmbSupplier.addItem(SupplierDao.getSupplier().get(i).getCode());
				}	
			}
		});		
		btnClrSupp2.setBounds(299, 121, 30, 30);
		RPanel.add(btnClrSupp2);
		
		
		txtAmount = new JTextField();
		txtAmount.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 102, 204), new Color(153, 204, 255),
				new Color(102, 102, 102), new Color(0, 0, 0)));
		txtAmount.setBounds(40, 259, 250, 30);
		RPanel.add(txtAmount);
		txtAmount.setColumns(10);
		txtAmount.setEditable(false);
		
		JLabel lblAmt = new JLabel("Total Amount");
		lblAmt.setForeground(new Color(255, 255, 255));
		lblAmt.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAmt.setBounds(41, 237, 121, 23);
		RPanel.add(lblAmt);
		
		JButton button = new JButton("=");
		button.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), Color.DARK_GRAY));
		button.setFont(new Font("Tahoma", Font.BOLD, 11));
		button.setForeground(Color.WHITE);
		button.setMargin(new Insets(2, 2, 2, 2));
		button.setBackground(new Color(0, 51, 102));
		button.addMouseListener(new BtnColor(button));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				totalAmount = Integer.parseInt(txtQty.getText()) * Double.parseDouble(txtPrice.getText());
				txtAmount.setText(String.valueOf(totalAmount));
			}
		});
		button.setBounds(299, 259, 30, 30);
		RPanel.add(button);
		
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Inventory u = new Inventory();
				Transactions t = new Transactions();
				u.setCost(Double.parseDouble(txtPrice.getText()));
				u.setQty(Integer.parseInt(txtQty.getText()));
				u.setId(txtCode.getText());
				t.setID(Integer.parseInt(txtTransCode.getText()));
				t.setProdId(txtCode.getText());
				t.setSuppId(Integer.parseInt(cmbSupplier.getSelectedItem().toString()));
				t.setDate(Date.valueOf(dayFormat2.format(dateNow)));
				t.setCost(Double.parseDouble(txtPrice.getText()));
				t.setQty(Integer.parseInt(txtQty.getText()));
				t.setAmt(Integer.parseInt(txtQty.getText()) * Double.parseDouble(txtPrice.getText()));
				
				InventoryDao.updateStock(u);
				TransDao.saveSuppTrans(t);
				dispose();
			}
		});
		
	}
}