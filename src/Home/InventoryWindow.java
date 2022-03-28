package Home;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.Image;
import java.awt.Component;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.BoxLayout;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import Beans.Inventory;
import Extras.LeftColor;

import com.toedter.calendar.JDateChooser;

public class InventoryWindow {

	private JFrame frmInventory;
	
	// resized images used as icon in main pane
		private Image BGImage = new ImageIcon(InventoryWindow.class.getResource("/Icons/smoke3.jpg")).getImage()
				.getScaledInstance(1280, 720, Image.SCALE_SMOOTH);
		private Image img_order = new ImageIcon(InventoryWindow.class.getResource("/Icons/order.png")).getImage()
				.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		private Image img_inventory = new ImageIcon(InventoryWindow.class.getResource("/Icons/inventory.png")).getImage()
				.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		private Image img_sales = new ImageIcon(InventoryWindow.class.getResource("/Icons/sales.png")).getImage()
				.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		private Image img_supp = new ImageIcon(InventoryWindow.class.getResource("/Icons/supplier.png")).getImage()
				.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

		// resized images used as icon in left menu

		private Image img_logo = new ImageIcon(InventoryWindow.class.getResource("/Icons/vapeSampleLogo_B.png")).getImage()
				.getScaledInstance(149, 149, Image.SCALE_SMOOTH);
		private Image icon_home = new ImageIcon(InventoryWindow.class.getResource("/Icons/home.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		private Image icon_inventory = new ImageIcon(InventoryWindow.class.getResource("/Icons/inventory2.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		private Image icon_sales = new ImageIcon(InventoryWindow.class.getResource("/Icons/sales2.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		private Image icon_order = new ImageIcon(InventoryWindow.class.getResource("/Icons/cart.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		private Image icon_trans = new ImageIcon(InventoryWindow.class.getResource("/Icons/trans.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		private Image icon_supp = new ImageIcon(InventoryWindow.class.getResource("/Icons/supplier2.png")).getImage()
				.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InventoryWindow window = new InventoryWindow();
					window.frmInventory.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

		/**
	 * Create the application.
	 */
	public InventoryWindow() {

		frmInventory = new JFrame();
		frmInventory.setVisible(true);
		frmInventory.setResizable(false);
		frmInventory.getContentPane().setBackground(new Color(255, 255, 255));
		frmInventory.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 11));
		frmInventory.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmInventory.setTitle("Inventory and Sales Management");
		frmInventory.setBounds(50, 5, 1280, 720);
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		frmInventory.getContentPane().setLayout	(new CardLayout(0, 0));
		CardLayout card = (CardLayout)(frmInventory.getContentPane().getLayout()); 
		
		JPanel homePane = new JPanel();
		homePane.setBackground(SystemColor.control);
		homePane.setBounds(0, 0, 1274, 691);
		frmInventory.getContentPane().add(homePane,"homepane");
		homePane.setLayout(null);
		
		JPanel oPane = new JPanel();
		oPane.setSize(1274, 691);
		oPane.setBounds(0, 0, 1274, 691);
		frmInventory.getContentPane().add(oPane,"opane");
		oPane.setLayout(null);
		
		
			
		//THE MAIN PANE -------------------------------------------------------------------------------------

		JPanel mainPane = new JPanel();
		mainPane.setBounds(255, 0, 1019, 691);
		mainPane.setBackground(Color.GRAY);
		mainPane.setLayout(new CardLayout(0, 0));
		oPane.add(mainPane);
		CardLayout cardLayout = (CardLayout)(mainPane.getLayout()); 
		
		InventoryWin iw = new InventoryWin();
		mainPane.add(iw.inventoryPane,"inventorypane");

		SalesWin sw = new SalesWin();
		mainPane.add(sw.salesPane,"salespane");
		
		TransWindow tw = new TransWindow();
		mainPane.add(tw.transPane,"transpane");
		
		SupplierWin supw = new SupplierWin();
		mainPane.add(supw.supplierPane,"supplierpane");
		
		OrderWin ow = new OrderWin();
		mainPane.add(ow.orderPane,"orderpane");
		
		
		//CONTENTS OF HOME PANE-------------------------------------------------------------------------------------------
		//INVENTORY
		JPanel Iborder = new JPanel();
		Iborder.setLayout(null);
		Iborder.setBorder(null);
		Iborder.setBounds(670, 100, 225, 244);
		Iborder.setBackground(Color.LIGHT_GRAY);
		Iborder.setOpaque(false);

		JButton btnHInventory = new JButton("Inventory");
		btnHInventory.setVerticalAlignment(SwingConstants.BOTTOM);
		btnHInventory.setBorder(null);
		btnHInventory.setOpaque(false);
		btnHInventory.setBounds(5, 5, 215, 215);
		btnHInventory.setForeground(new Color(255, 255, 255));
		btnHInventory.setBackground(SystemColor.textHighlight);
		btnHInventory.setFont(new Font("Tahoma", Font.BOLD, 23));
		btnHInventory.addMouseListener(new ColorChanger(btnHInventory, Iborder));
		btnHInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card.show(frmInventory.getContentPane(), "opane");
				cardLayout.show(mainPane, "inventorypane");
			}
		});
		// inventory lbl_image
		JLabel ilblinventory = new JLabel();
		ilblinventory.setHorizontalAlignment(SwingConstants.CENTER);
		ilblinventory.setBounds(8, 30, 215, 150);
		ilblinventory.setIcon(new ImageIcon(img_inventory));

		Iborder.add(ilblinventory);
		Iborder.add(btnHInventory);
		homePane.add(Iborder);
				
		// SALES----------------------------------------------------------------
		JPanel Sborder = new JPanel();
		Sborder.setLayout(null);
		Sborder.setBorder(null);
		Sborder.setBackground(Color.LIGHT_GRAY);
		Sborder.setBounds(670, 376, 225, 244);
		Sborder.setOpaque(false);

		JButton btnHSales = new JButton("Sales");
		btnHSales.setVerticalAlignment(SwingConstants.BOTTOM);
		btnHSales.setBorder(null);
		btnHSales.setOpaque(false);
		btnHSales.setBounds(5, 5, 215, 215);
		btnHSales.setForeground(new Color(255, 255, 255));
		btnHSales.setBackground(SystemColor.textHighlight);
		btnHSales.setFont(new Font("Tahoma", Font.BOLD, 23));
		btnHSales.addMouseListener(new ColorChanger(btnHSales, Sborder));
		btnHSales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card.show(frmInventory.getContentPane(), "opane");
				cardLayout.show(mainPane, "salespane");
			}
		});
		// sales lbl_image
		JLabel ilblsales = new JLabel();
		ilblsales.setHorizontalAlignment(SwingConstants.CENTER);
		ilblsales.setBounds(5, 30, 215, 150);
		ilblsales.setIcon(new ImageIcon(img_sales));

		Sborder.add(ilblsales);
		Sborder.add(btnHSales);
		homePane.add(Sborder);
		
		// ORDER------------------------------------------------------------
		// order panel
		JPanel Oborder = new JPanel();
		Oborder.setLayout(null);
		Oborder.setBorder(null);
		Oborder.setBackground(Color.LIGHT_GRAY);
		Oborder.setOpaque(false);
		Oborder.setBounds(385, 100, 225, 244);
		// order button
		JButton btnHOrder = new JButton("Order");
		btnHOrder.setBorder(null);
		btnHOrder.setVerticalAlignment(SwingConstants.BOTTOM);
		btnHOrder.setBackground(new Color(238, 232, 170));
		btnHOrder.setOpaque(false);
		btnHOrder.setBounds(5, 5, 215, 215);
		btnHOrder.setForeground(new Color(255, 255, 255));
		btnHOrder.setBackground(SystemColor.textHighlight);
		btnHOrder.setFont(new Font("Tahoma", Font.BOLD, 23));
		btnHOrder.addMouseListener(new ColorChanger(btnHOrder, Oborder));
		btnHOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card.show(frmInventory.getContentPane(), "opane");
				cardLayout.show(mainPane, "orderpane");
			}
		});
		// order lbl_image
		JLabel ilblorder = new JLabel();
		ilblorder.setHorizontalAlignment(SwingConstants.CENTER);
		ilblorder.setBounds(5, 30, 215, 150);
		ilblorder.setIcon(new ImageIcon(img_order));

		Oborder.add(ilblorder);
		Oborder.add(btnHOrder);
		homePane.add(Oborder);
		
		// SUPPLIER----------------------------------------------------------
		JPanel supborder = new JPanel();
		supborder.setBorder(null);
		supborder.setBackground(Color.LIGHT_GRAY);
		supborder.setOpaque(false);
		supborder.setBounds(385, 376, 225, 244);
		supborder.setLayout(null);

		JButton btnSupp = new JButton("Supplier");
		btnSupp.setVerticalAlignment(SwingConstants.BOTTOM);
		btnSupp.setBorder(null);
		btnSupp.setOpaque(false);
		btnSupp.setBounds(5, 5, 215, 215);
		btnSupp.setForeground(new Color(255, 255, 255));
		btnSupp.setBackground(SystemColor.textHighlight);
		btnSupp.setFont(new Font("Tahoma", Font.BOLD, 23));
		btnSupp.addMouseListener(new ColorChanger(btnSupp, supborder));
		btnSupp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card.show(frmInventory.getContentPane(), "opane");
				cardLayout.show(mainPane, "supplierpane");
			}
		});
		// supplier lbl_image
		JLabel ilblsupp = new JLabel();
		ilblsupp.setHorizontalAlignment(SwingConstants.CENTER);
		ilblsupp.setBounds(5, 30, 215, 150);
		ilblsupp.setIcon(new ImageIcon(img_supp));

		supborder.add(ilblsupp);
		supborder.add(btnSupp);
		homePane.add(supborder);
		
		// SETTINGS----------------------------------------------------------
		JButton btnSettings = new JButton();
		btnSettings.setBackground(Color.LIGHT_GRAY);
		btnSettings.setIcon(new ImageIcon(InventoryWindow.class.getResource("/Icons/settings.png")));
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SettingsFrame();
			}
		});
		btnSettings.setBounds(1154, 62, 59, 55);
		homePane.add(btnSettings);

		// Background IMAGE
		JLabel lblBgimage = new JLabel();
		lblBgimage.setBounds(0, 0, 1280, 720);
		homePane.add(lblBgimage);
		lblBgimage.setIcon(new ImageIcon(BGImage));
						
		//START OF LEFT MENU PANE ---------------------------------------------------------------------------
		
		
		JPanel LeftMenu = new JPanel();
		LeftMenu.setBackground(Color.BLACK);
		LeftMenu.setBounds(0, 0, 256, 691);
		oPane.add(LeftMenu);
		
		JPanel Home = new JPanel();
		Home.setOpaque(false);
		Home.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		Home.setSize(255, 64);
		Home.setLocation(0, 256);
		Home.setBackground(Color.WHITE);
		Home.setLayout(null);
		
		JButton btnHome = new JButton("HOME");
		btnHome.setBorderPainted(false);
		btnHome.setMargin(new Insets(2, 23, 2, 14));
		btnHome.setIcon(new ImageIcon(icon_home));
		btnHome.setIconTextGap(15);
		btnHome.setHorizontalAlignment(SwingConstants.LEFT);
		btnHome.setBounds(10, 0, 245, 64);
		btnHome.setForeground(new Color(255, 255, 255));
		btnHome.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnHome.setBackground(Color.BLACK);
		btnHome.addMouseListener(new Changer(btnHome));
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card.show(frmInventory.getContentPane(), "homepane");
			}
		});
		Home.add(btnHome);
		
		// inventory button-------------------------------------------------------------
		JPanel panelInventory = new JPanel();
		panelInventory.setSize(255, 64);
		panelInventory.setLocation(0, 318);
		panelInventory.setLayout(null);
		panelInventory.setBackground(Color.BLACK);

		JButton btnInventory = new JButton("INVENTORY");
		btnInventory.setMargin(new Insets(2, 23, 2, 14));
		btnInventory.setIcon(new ImageIcon(icon_inventory));
		btnInventory.setIconTextGap(15);
		btnInventory.setBorderPainted(false);
		btnInventory.setHorizontalAlignment(SwingConstants.LEFT);
		btnInventory.setBounds(10, 0, 245, 64);
		btnInventory.setForeground(new Color(255, 255, 255));
		btnInventory.setBackground(Color.BLACK);
		btnInventory.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnInventory.addMouseListener(new Changer(btnInventory));
		btnInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(mainPane, "inventorypane");
			}
		});
		panelInventory.add(btnInventory);
		
		// sales button-----------------------------------------------------------------
		JPanel panelSales = new JPanel();
		panelSales.setLayout(null);
		panelSales.setSize(255, 64);
		panelSales.setLocation(0, 379);
		panelSales.setBackground(Color.BLACK);

		JButton btnSales = new JButton("SALES");
		btnSales.setMargin(new Insets(2, 23, 2, 14));
		btnSales.setIcon(new ImageIcon(icon_sales));
		btnSales.setIconTextGap(15);
		btnSales.setBorderPainted(false);
		btnSales.setHorizontalAlignment(SwingConstants.LEFT);
		btnSales.setBounds(10, 0, 245, 64);
		btnSales.setForeground(new Color(255, 255, 255));
		btnSales.setBackground(Color.BLACK);
		btnSales.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSales.addMouseListener(new Changer(btnSales));
		btnSales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(mainPane, "salespane");
			}
		});
		panelSales.add(btnSales);
		
		// transaction button-----------------------------------------------------------
		JPanel panelTrans = new JPanel();
		panelTrans.setLayout(null);
		panelTrans.setLocation(0, 441);
		panelTrans.setSize(255, 64);
		panelTrans.setBackground(Color.BLACK);

		JButton btnTrans = new JButton("TRANSACTIONS");
		btnTrans.setMargin(new Insets(2, 23, 2, 14));
		btnTrans.setIcon(new ImageIcon(icon_trans));
		btnTrans.setIconTextGap(15);
		btnTrans.setBorderPainted(false);
		btnTrans.setHorizontalAlignment(SwingConstants.LEFT);
		btnTrans.setBounds(10, 0, 245, 64);
		btnTrans.setForeground(Color.WHITE);
		btnTrans.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnTrans.setBackground(Color.BLACK);
		btnTrans.addMouseListener(new Changer(btnTrans));
		btnTrans.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(mainPane, "transpane");
			}
		});
		panelTrans.add(btnTrans);
		 
		// supplier button-------------------------------------------------------------
		JPanel panelSupplier = new JPanel();
		panelSupplier.setLayout(null);
		panelSupplier.setLocation(0, 503);
		panelSupplier.setSize(255, 64);
		panelSupplier.setBackground(Color.BLACK);

		JButton btnSupplier = new JButton("SUPPLIER");
		btnSupplier.setMargin(new Insets(2, 23, 2, 14));
		btnSupplier.setIcon(new ImageIcon(icon_supp));
		btnSupplier.setIconTextGap(15);
		btnSupplier.setBorderPainted(false);
		btnSupplier.setHorizontalAlignment(SwingConstants.LEFT);
		btnSupplier.setBounds(10, 0, 245, 64);
		btnSupplier.setForeground(new Color(255, 255, 255));
		btnSupplier.setBackground(Color.BLACK);
		btnSupplier.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSupplier.addMouseListener(new Changer(btnSupplier));
		btnSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(mainPane, "supplierpane");
			}
		});
		panelSupplier.add(btnSupplier);

		
		// order button------------------------------------------------------------------
		JPanel panelOrder = new JPanel();
		panelOrder.setLayout(null);
		panelOrder.setLocation(0, 565);
		panelOrder.setSize(255, 64);
		panelOrder.setBackground(Color.BLACK);

		JButton btnOrder = new JButton("ORDER FORM");
		btnOrder.setMargin(new Insets(2, 23, 2, 14));
		btnOrder.setIcon(new ImageIcon(icon_order));
		btnOrder.setIconTextGap(15);
		btnOrder.setBorderPainted(false);
		btnOrder.setHorizontalAlignment(SwingConstants.LEFT);
		btnOrder.setBounds(10, 0, 245, 64);
		btnOrder.setForeground(Color.WHITE);
		btnOrder.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnOrder.setBackground(Color.BLACK);
		btnOrder.addMouseListener(new Changer(btnOrder));
		btnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(mainPane, "orderpane");
			}
		});
		panelOrder.add(btnOrder);
		
		// main window left color changer
		Iborder.addMouseListener(new LeftColor(btnInventory, btnSales, btnTrans, btnSupplier, btnOrder, panelInventory,
						panelSales, panelTrans, panelSupplier, panelOrder));
		Sborder.addMouseListener(new LeftColor(btnSales, btnInventory, btnTrans, btnSupplier, btnOrder, panelSupplier,
						panelInventory, panelSales, panelTrans, panelOrder));
		supborder.addMouseListener(new LeftColor(btnSupplier, btnInventory, btnSales, btnTrans, btnOrder, panelSupplier,
						panelInventory, panelSales, panelTrans, panelOrder));
		Oborder.addMouseListener(new LeftColor(btnOrder, btnInventory, btnSales, btnTrans, btnSupplier, panelOrder,
						panelInventory, panelSales, panelTrans, panelSupplier));

		// left menu left color changer
		btnInventory.addMouseListener(new LeftColor(btnInventory, btnSales, btnTrans, btnSupplier, btnOrder,
						panelInventory, panelSales, panelTrans, panelSupplier, panelOrder));
		btnSales.addMouseListener(new LeftColor(btnSales, btnInventory, btnTrans, btnSupplier, btnOrder, panelSales,
						panelSupplier, panelInventory, panelTrans, panelOrder));
		btnTrans.addMouseListener(new LeftColor(btnTrans, btnInventory, btnSales, btnSupplier, btnOrder, panelTrans,
						panelInventory, panelSales, panelSupplier, panelOrder));
		btnSupplier.addMouseListener(new LeftColor(btnSupplier, btnInventory, btnSales, btnTrans, btnOrder,
						panelSupplier, panelInventory, panelSales, panelTrans, panelOrder));
		btnOrder.addMouseListener(new LeftColor(btnOrder, btnInventory, btnSales, btnTrans, btnSupplier, panelOrder,
						panelInventory, panelSales, panelTrans, panelSupplier));

		// logo in upper left
				JLabel lbl_logo = new JLabel();
				lbl_logo.setFont(new Font("Tahoma", Font.BOLD, 18));
				lbl_logo.setForeground(Color.WHITE);
				lbl_logo.setText("HAMOG VAPE SHOP");
				lbl_logo.setVerticalTextPosition(SwingConstants.BOTTOM);
				lbl_logo.setVerticalAlignment(SwingConstants.TOP);
				lbl_logo.setBorder(null);
				lbl_logo.setHorizontalTextPosition(SwingConstants.CENTER);
				lbl_logo.setHorizontalAlignment(SwingConstants.CENTER);
				lbl_logo.setBounds(27, 26, 201, 191);
				lbl_logo.setIcon(new ImageIcon(img_logo));

				LeftMenu.setLayout(null);
				LeftMenu.add(Home);
				LeftMenu.add(panelInventory);
				LeftMenu.add(panelSales);
				LeftMenu.add(panelTrans);
				LeftMenu.add(panelSupplier);
				LeftMenu.add(panelOrder);
				LeftMenu.add(lbl_logo);
	///END OF LEFT MENU-------------------------------------------------------------------------------------------------
	 }		
	// Color changer class for main window buttons
		private class ColorChanger extends MouseAdapter {

			JButton button = new JButton();
			JPanel panel = new JPanel();

			public ColorChanger(JButton button, JPanel panel) {
				this.button = button;
				this.panel = panel;
			}

			public void mouseEntered(MouseEvent e) {
				button.setForeground(Color.BLACK);
				button.setFont(new Font("Tahoma", Font.BOLD, 30));
				panel.setOpaque(true);
			}

			public void mouseExited(MouseEvent e) {
				button.setForeground(Color.WHITE);
				button.setFont(new Font("Tahoma", Font.BOLD, 23));
				panel.setOpaque(false);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				button.setBackground(Color.WHITE);
			}
		}

		// Color changer for the left menu buttons
		private class Changer extends MouseAdapter {

			JButton button = new JButton();

			public Changer(JButton button) {
				this.button = button;
			}

			public void mouseEntered(MouseEvent e) {
				button.setBackground(Color.GRAY);
				button.setFont(new Font("Tahoma", Font.BOLD, 18));
			}

			public void mouseExited(MouseEvent e) {
				button.setBackground(Color.BLACK);
				button.setFont(new Font("Tahoma", Font.BOLD, 15));
			}
		}

}



