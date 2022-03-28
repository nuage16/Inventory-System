package Home;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import com.toedter.calendar.JDateChooser;

import DAO.SupplierDao;
import DAO.TransDao;
import Beans.Inventory;
import Extras.BtnColor;
import Extras.ButtonRenderer;
import Extras.ExtraMethods;
import Extras.TabListener;
import Extras.Tabs;

public class TransWindow extends JPanel{

	JPanel transPane;

	private static JTextField totInvent;
	private static JTextField totEliquid;
	
	private static JTextField totTrans;
	private static JTextField totSEliquid;
	private static JTextField txtSearch;
	
	private static JTable tableProducts;
	private static JTable tableSupplier;
	

	private static DefaultTableModel model1;
	private static DefaultTableModel model2;
	private TableRowSorter<TableModel> rowSorter;

	private JDateChooser dateChS, dateChE;
	private LocalDate dend, dstart;
	private static java.util.Date start;

	private static java.util.Date end; 
	private JLabel txtTrans;
	private JLabel txtSupplierTrans;
	private JPanel transpanel;
	private JPanel suppPanel;
	
	private static Double totalProdAmt = 0.00;
	private static Double totalSuppAmt = 0.00;
	private static int totalProd=0;

	private static int totalSupp=0;

	
	public TransWindow() {
		
		transPane = new JPanel();
		transPane.setBorder(new CompoundBorder(new MatteBorder(10, 10, 10, 10, (Color) new Color(128, 128, 128)),
				new EtchedBorder(EtchedBorder.LOWERED, new Color(192, 192, 192), new Color(0, 0, 0))));
		transPane.setBackground(new Color(178, 34, 34));
		transPane.setBounds(255, 0, 1019, 691);
		transPane.setBounds(255, 0, 1019, 691);
		transPane.setVisible(true);
		transPane.setLayout(null);

		txtSearch = new JTextField();
		txtSearch.setBounds(505, 91, 360, 34);
		transPane.add(txtSearch);
		ExtraMethods.txtPlaceHolder(txtSearch, "Type to Search");

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnRefresh.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), Color.DARK_GRAY));
		btnRefresh.setForeground(Color.WHITE);
		btnRefresh.setBackground(new Color(205, 92, 92));
		btnRefresh.setBounds(895, 90, 90, 34);
		transPane.add(btnRefresh);
		btnRefresh.addMouseListener(new BtnColor(btnRefresh));
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model1.setRowCount(0);
				model2.setRowCount(0);
				txtSearch.setText("");
				ExtraMethods.txtPlaceHolder(txtSearch, "Type to Search");
				query();
			}
		});
		
		ZoneId defaultZoneId = ZoneId.systemDefault();
		
		dstart = LocalDate.of(2018,Month.JANUARY,01);
		start = java.util.Date.from(dstart.atStartOfDay(defaultZoneId).toInstant());
		dateChS = new JDateChooser();
		dateChS.getCalendarButton().setBackground(new Color(178, 34, 34));
		dateChS.setBounds(88, 98, 101, 20);
		dateChS.setDate(start);
		transPane.add(dateChS);
		
		JLabel lblNewLabel = new JLabel("Start:");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(42, 98, 46, 20);
		transPane.add(lblNewLabel);

		JLabel lblEnd = new JLabel("End:");
		lblEnd.setForeground(Color.WHITE);
		lblEnd.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEnd.setBounds(205, 98, 46, 20);
		transPane.add(lblEnd);
		
		dend = LocalDate.now();
		end = java.util.Date.from(dend.atStartOfDay(defaultZoneId).toInstant());
		dateChE = new JDateChooser();
		dateChE.getCalendarButton().setBackground(new Color(178, 34, 34));
		dateChE.setBounds(240, 98, 101, 20);
		dateChE.setDate(end);
		transPane.add(dateChE);
		
		JButton btnSort = new JButton("Filter");
		btnSort.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), Color.DARK_GRAY));
		btnSort.setForeground(Color.WHITE);
		btnSort.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSort.setBackground(new Color(51, 0, 0));
		btnSort.addMouseListener(new BtnColor(btnSort));
		btnSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start = dateChS.getDate();
				end = dateChE.getDate();
				query();
			}
		});
		btnSort.setBounds(362, 95, 76, 27);
		transPane.add(btnSort);
		
		
		JPanel tabHeader = new JPanel();
		tabHeader.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(0, 0, 0)));
		tabHeader.setBackground(Color.GRAY);
		tabHeader.setBounds(12, 12, 995, 60);
		transPane.add(tabHeader);
		tabHeader.setLayout(null);

		transpanel = new JPanel();
		tabHeader.add(transpanel);
		transpanel.setBounds(0, 0, 320, 60);
		transpanel.setBorder(new MatteBorder(3, 3, 0, 3, (Color.BLACK)));
		transpanel.setBackground(new Color(178, 34, 34));
		transpanel.setLayout(new BorderLayout(0, 0));

		JPanel tabHolder = new JPanel();
		JPanel tab1Content = new JPanel();
		JPanel tab2Content = new JPanel();
		
		tabHolder.setLayout(new CardLayout(0, 0));
		CardLayout cardLayout3 = (CardLayout)(tabHolder.getLayout());
		tabHolder.setBackground(new Color(255, 255, 255, 255));
		tabHolder.setBounds(35, 143, 950, 520);
		transPane.add(tabHolder);
		
		txtTrans = new JLabel();
		txtTrans.setForeground(Color.WHITE);
		txtTrans.setFont(new Font("Tahoma", Font.BOLD, 20));
		txtTrans.setHorizontalAlignment(SwingConstants.CENTER);
		txtTrans.setText("SALES TRANSACTIONS");
		transpanel.add(txtTrans);
		transpanel.addMouseListener(new TabListener(txtTrans));
		transpanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout3.show(tabHolder, "tab1");
			}
		});
		suppPanel = new JPanel();
		suppPanel.setBounds(320, 0, 320, 60);
		tabHeader.add(suppPanel);
		suppPanel.setBackground(new Color(51, 51, 51));
		suppPanel.setLayout(new BorderLayout(0, 0));
		suppPanel.setBorder(new MatteBorder(0, 0, 3, 1, (Color.BLACK)));
				
		txtSupplierTrans = new JLabel();
		txtSupplierTrans.setForeground(Color.WHITE);    
		txtSupplierTrans.setFont(new Font("Tahoma", Font.BOLD, 20));
		txtSupplierTrans.setHorizontalAlignment(SwingConstants.CENTER);
		txtSupplierTrans.setText("SUPPLIER TRANSACTIONS");
		suppPanel.add(txtSupplierTrans);
		suppPanel.addMouseListener(new TabListener(txtSupplierTrans));
		suppPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout3.show(tabHolder, "tab2");
			}
		});
		int c1 = 178, c2 = 34, c3 = 34;
		transpanel.addMouseListener(new Tabs(c1, c2, c3, transpanel, suppPanel));
		suppPanel.addMouseListener(new Tabs(c1, c2, c3, suppPanel, transpanel));
		
//END OF TAB HEADERS-----------------------------------------------------------------------------------
		
		
		
		//TAB 1 CONTENT ------------------------------------------------------------------------------
		tab1Content.setBackground(new Color(128, 0, 0));
		tab1Content.setLayout(null);
		tabHolder.add(tab1Content, "tab1");

		
		model1 = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column==4)
					return true;
				else
					return false;
			}
		//FROM HERE	
			public Class<?> getColumnClass(int col){
				if(col == 0 || col == 1)
					return int.class;
				else if(col == 2  || col == 3 )
					return Double.class;
				else if(col == 4 )
					return Date.class;
				else
					return Boolean.class;
				}		
		};
		//TO HERE
		
		tableProducts=new JTable(model1);
		tableProducts.setBackground(new Color(176, 196, 222));
		tableProducts.setBounds(186, 95, 333, 200);
		
		JScrollPane scpnProduct = new JScrollPane(tableProducts);
		scpnProduct.setBounds(0, 38, 950, 482);
		tab1Content.add(scpnProduct);
		
		totInvent = new JTextField();
		totInvent.setEditable(false);
		totInvent.setBounds(10, 5, 200, 30);
		totInvent.setFont(new Font("Tahoma", Font.BOLD, 13));
		totInvent.setHorizontalAlignment(SwingConstants.LEFT);
		tab1Content.add(totInvent);
		totInvent.setColumns(10);
		
		totTrans = new JTextField();
		totTrans.setEditable(false);
		totTrans.setBounds(740, 5, 200, 30);
		totTrans.setFont(new Font("Tahoma", Font.BOLD, 13));
		totTrans.setHorizontalAlignment(SwingConstants.RIGHT);
		tab1Content.add(totTrans);
		totTrans.setColumns(10);
		rowSorter = new TableRowSorter<>(tableProducts.getModel());
		tableProducts.setRowSorter(rowSorter);
		ExtraMethods.searchFilter(rowSorter, txtSearch, "Type to Search");

		
//TAB 2 CONTENT--------------------------------------------------------------------------

		tab2Content.setBackground(new Color(233, 150, 122));
		tabHolder.add(tab2Content, "tab2");
		tab2Content.setLayout(null);
		
		model2 = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column==6)
					return true;
				else
					return false;
			}
			public Class<?> getColumnClass(int col){
				if(col == 0 || col == 1 || col == 4)
					return int.class;
				else if(col == 2 || col == 3)
					return String.class;
				else if(col == 5 )
					return Double.class;
				else if(col == 6 )
					return Date.class;
				else
					return Boolean.class;
			}
		};
		
		tableSupplier=new JTable(model2);
		tableSupplier.setBackground(new Color(176, 196, 222));
		tableSupplier.setBounds(186, 95, 333, 200);
		
		JScrollPane scpnELiquid = new JScrollPane(tableSupplier);
		scpnELiquid.setBounds(0, 38, 950, 482);
		tab2Content.add(scpnELiquid);
		
		totEliquid = new JTextField();
		totEliquid.setEditable(false);
		totEliquid.setColumns(10);
		totEliquid.setFont(new Font("Tahoma", Font.BOLD, 13));
		totEliquid.setBounds(10, 5, 200, 30);
		tab2Content.add(totEliquid);

		totSEliquid = new JTextField();
		totSEliquid.setEditable(false);
		totSEliquid.setBounds(740, 5, 200, 30);
		totSEliquid.setFont(new Font("Tahoma", Font.BOLD, 13));
		totSEliquid.setHorizontalAlignment(SwingConstants.RIGHT);
		tab2Content.add(totSEliquid);
		totSEliquid.setColumns(10);
		rowSorter = new TableRowSorter<>(tableSupplier.getModel());
		tableSupplier.setRowSorter(rowSorter);
		ExtraMethods.searchFilter(rowSorter, txtSearch, "Type to Search");
		 		
		query();

		
		//TABLE LISTENER FOR VOIDED ITEMS
		tableProducts.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				ExtraMethods.statusTransUpdater(model1,0,5,0);
			}
		});
		
		tableSupplier.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				ExtraMethods.statusTransUpdater(model2,0,7,1);
			}
		});
		
		
	}
	//END OF Transactions PANE-----------------------------------------------------------------------------------------------
		
	
	//QUERY  DATA FROM TRANSACTIONS------------------------------------------------------------------------------------------
	public static void query() {
		
		System.out.print("\nEntered Query");
		totalProdAmt = 0.00;
		totalSuppAmt = 0.00;
		totalProd=0; totalSupp=0;
		Object colProdName[] = new Object[] {"Trans Code","Quantity","Total Amount","Discount","Date", "Void"}; // 6
		Object colSuppName[] = new Object[] {"Trans Code","Supplier ID", "Supplier Name", "Product Code","Quantity","Total Amount","Date", "Void"};  // 7
	
		model1.setRowCount(0);
		model2.setRowCount(0);
	
		model1.setColumnIdentifiers(colProdName);
		model2.setColumnIdentifiers(colSuppName);
	
		Object[] rD = new Object[8];
		for(int j=0;j<2;j++) {
		
			//GETS THE SIZE OF ARRAYLIST : TransDao.getTrans(j,start,end).size()
			for(int i=0;i<TransDao.getTrans(j,start,end).size();i++) {
				
				//QUERY FOR THE PRODUCT/SERVICES TRANSACTIONS
				if(j==0) {
		 		
					if(!TransDao.getTrans(j,start,end).isEmpty()) {
						rD[0] = TransDao.getTrans(j,start,end).get(i).getID();
						rD[1] = TransDao.getTrans(j,start,end).get(i).getQty();
						rD[2] = TransDao.getTrans(j,start,end).get(i).getAmt();
						rD[3] = TransDao.getTrans(j,start,end).get(i).getDiscount();
						rD[4] = TransDao.getTrans(j,start,end).get(i).getDate();
						rD[5] = TransDao.getTrans(j, start, end).get(i).getFlag();
						model1.addRow(rD);
						totalProd+=1;
						totalProdAmt+=Double.parseDouble(rD[2].toString());
					}
				}
		 	
				//QUERY FOR THE SUPPLIER TRANSACTIONS
				else {
			 		if(!TransDao.getTrans(j,start,end).isEmpty()) {
			 			rD[0] = TransDao.getTrans(j,start,end).get(i).getID();
			 			rD[1] = TransDao.getTrans(j,start,end).get(i).getSuppid();
			 			rD[2] = SupplierDao.searchRecord(Integer.parseInt(rD[1].toString())).getName();
			 			rD[3] = TransDao.getTrans(j, start, end).get(i).getProdId();
			 			rD[4] = TransDao.getTrans(j,start,end).get(i).getQty();
			 			rD[5] = TransDao.getTrans(j,start,end).get(i).getAmt();
			 			rD[6] = TransDao.getTrans(j,start,end).get(i).getDate();
			 			rD[7] = TransDao.getTrans(j, start, end).get(i).getFlag();
			 			model2.addRow(rD);
			 			totalSupp+=1;
			 			totalSuppAmt+=Double.parseDouble(rD[5].toString());
		 			
		 			
			 		}//END OF IF (!TransDao.getTrans(j,start,end).isEmpty())
				}//END OF ELSE 
			}//END OF FOR I LOOP
		}//END OF FOR J LOOP
	
		//SET THE TOTAL NUMBER OF ITEMS
		totInvent.setText(String.valueOf(totalProd) + " Transactions");		
		totTrans.setText("Php" + " " + String.valueOf(totalProdAmt));
	
		totEliquid.setText(String.valueOf(totalSupp) + " Transactions");

		totSEliquid.setText("Php" + " " + String.valueOf(totalSuppAmt));

}//END OF QUERY FUNCTION

//======================================================================
	
	public static void insertRow(Object[] newRow, int j) {
		
		DefaultTableModel model;
		
		if(j==0) { 
			model = (DefaultTableModel)tableProducts.getModel();
			totalProd+=1;
		}
		else {
			model = (DefaultTableModel)tableSupplier.getModel();
			totalSupp+=1;
		}	
		model.insertRow(0, newRow);		
	
		//SET THE TOTAL NUMBER OF ITEMS
		totInvent.setText(String.valueOf(totalProd) + " Transactions");		
		totTrans.setText("Php" + " " + String.valueOf(totalProdAmt));
		
		totEliquid.setText(String.valueOf(totalSupp) + " Transactions");

		totSEliquid.setText("Php" + " " + String.valueOf(totalSuppAmt));

	}
	
//=========================================================================
	
}//END OF CLASS
