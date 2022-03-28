package Home;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Insets;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Locale;

import javax.swing.JButton;
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
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import com.toedter.calendar.JDateChooser;

import DAO.SalesDao;
import Beans.Inventory;
import Beans.Sales;
import Extras.BtnColor;
import Extras.ButtonRenderer;
import Extras.ButtonRendererEditor;
import Extras.ExtraMethods;
import Extras.Tabs;
import Extras.TabListener;

public class SalesWin extends JPanel{

	JPanel salesPane;

	private static JTextField totInvent;
	private static JTextField totEliquid;
	private static JTextField totAcc;
	private static JTextField totMods;
	private static JTextField totAtom;
	private static JTextField totServ;
	
	private static JTextField totSales;
	private static JTextField totSEliquid;
	private static JTextField totSAcc;
	private static JTextField totSMods;
	private static JTextField totSAtom;
	private static JTextField totSServ;
	private static JTextField txtSearch;
	
	private static JTable tableAItems;
	private static JTable tableELiquid;
	private static JTable tableAccesr;
	private static JTable tableMods;
	private static JTable tableAtom;
	private static JTable tableService;
	

	private static DefaultTableModel model1;
	private static DefaultTableModel model2;
	private static DefaultTableModel model3;
	private static DefaultTableModel model4;
	private static DefaultTableModel model5;
	private static DefaultTableModel model6;
	private TableRowSorter<TableModel> rowSorter;

	private JDateChooser dateChS, dateChE;
	private LocalDate dend, dstart;
	private static java.util.Date start;

	private static java.util.Date end; 
	
	private static Double totalAllSales = 0.00;
	private static Double saleEl = 0.00;
	private static Double saleAcc = 0.00;
	private static Double saleMods = 0.00;
	private static Double saleAtom = 0.00;
	private static Double saleServ = 0.00;
	private static int totalAll=0;
	private static int totalEl=0;
	private static int totalAcc=0;
	private static int totalMods=0;
	private static int totalAtom=0;
	private static int totalService=0;
	

	
	public SalesWin() {
		
		salesPane = new JPanel();
		salesPane.setBorder(new CompoundBorder(new MatteBorder(10, 10, 10, 10, (Color) new Color(128, 128, 128)),
				new EtchedBorder(EtchedBorder.LOWERED, new Color(192, 192, 192), new Color(0, 0, 0))));
		salesPane.setBackground(new Color(102, 153, 51));
		salesPane.setBounds(255, 0, 1019, 691);
		salesPane.setLayout(null);
		salesPane.setVisible(true);	
		
		txtSearch = new JTextField();
		txtSearch.setBounds(505, 90, 360, 34);
		salesPane.add(txtSearch);
		ExtraMethods.txtPlaceHolder(txtSearch, "Type to Search");
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), Color.DARK_GRAY));
		btnRefresh.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnRefresh.setForeground(Color.WHITE);
		btnRefresh.setBackground(new Color(153, 204, 51));
		btnRefresh.setBounds(895, 89, 90, 34);
		salesPane.add(btnRefresh);
		btnRefresh.addMouseListener(new BtnColor(btnRefresh));
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model1.setRowCount(0);
				model2.setRowCount(0);
				model3.setRowCount(0);
				model4.setRowCount(0);
				model5.setRowCount(0);
				model6.setRowCount(0);
				txtSearch.setText("");
				ExtraMethods.txtPlaceHolder(txtSearch, "Type to Search");
				query();
			}
		});
		
		
		ZoneId defaultZoneId = ZoneId.systemDefault();
		
		dstart = LocalDate.of(2018,Month.JANUARY,01);
		start = java.util.Date.from(dstart.atStartOfDay(defaultZoneId).toInstant());
		dateChS = new JDateChooser();
		dateChS.setBorder(null);
		dateChS.getCalendarButton().setBackground(new Color(102, 153, 51));
		dateChS.setBounds(84, 98, 101, 20);
		dateChS.setDate(start);
		salesPane.add(dateChS);
		
		JLabel lblNewLabel = new JLabel("Start:");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(41, 98, 46, 20);
		salesPane.add(lblNewLabel);

		JLabel lblEnd = new JLabel("End:");
		lblEnd.setForeground(Color.WHITE);
		lblEnd.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEnd.setBounds(205, 98, 46, 20);
		salesPane.add(lblEnd);
		
		dend = LocalDate.now();
		end = java.util.Date.from(dend.atStartOfDay(defaultZoneId).toInstant());
		dateChE = new JDateChooser();
		dateChE.setBorder(null);
		dateChE.getCalendarButton().setBackground(new Color(102, 153, 51));
		dateChE.setBounds(240, 98, 101, 20);
		dateChE.setDate(end);
		salesPane.add(dateChE);
		
		JButton btnSort = new JButton("Filter");
		btnSort.setMargin(new Insets(1, 14, 1, 14));
		btnSort.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(255, 255, 255), new Color(204, 204, 204),
				new Color(0, 0, 0), Color.DARK_GRAY));
		btnSort.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSort.setForeground(Color.WHITE);
		btnSort.setBackground(new Color(85, 107, 47));
		btnSort.addMouseListener(new BtnColor(btnSort));
		btnSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start = dateChS.getDate();
				end = dateChE.getDate();
				query();
			}
		});
		btnSort.setBounds(362, 95, 76, 25);
		salesPane.add(btnSort);
		
		
		//HEADERS
		JPanel saleEliquid = new JPanel();
		JPanel saleItems = new JPanel();
		JPanel saleAccesories = new JPanel();
		JPanel saleServices = new JPanel();
		JPanel saleAtoms = new JPanel();
		JPanel saleMods = new JPanel();
		

		JPanel tabHolder = new JPanel();
		JPanel tab1Content = new JPanel();
		JPanel tab2Content = new JPanel();
		JPanel tab3Content = new JPanel();
		JPanel tab4Content = new JPanel();
		JPanel tab5Content = new JPanel();
		JPanel tab6Content = new JPanel();


		
		tabHolder.setLayout(new CardLayout(0, 0));
		CardLayout cardLayout3 = (CardLayout)(tabHolder.getLayout());
		tabHolder.setBackground(new Color(255,255,255,255));
		tabHolder.setBounds(35, 143, 950, 520);
		salesPane.add(tabHolder);
		
		// initial tab panel-button borders
		saleEliquid.setBorder(new MatteBorder(0, 0, 3, 1, (Color.BLACK)));
		saleItems.setBorder(new MatteBorder(3, 3, 0, 3, (Color.BLACK)));
		saleAccesories.setBorder(new MatteBorder(0, 0, 3, 1, (Color.BLACK)));
		saleServices.setBorder(new MatteBorder(0, 0, 3, 1, (Color.BLACK)));
		saleAtoms.setBorder(new MatteBorder(0, 0, 3, 1, (Color.BLACK)));
		saleMods.setBorder(new MatteBorder(0, 0, 3, 1, (Color.BLACK)));
		
		// initial colors
		saleItems.setBackground(new Color(102, 153, 51));
		saleAccesories.setBackground(new Color(51, 51, 51));
		saleServices.setBackground(new Color(51, 51, 51));
		saleAtoms.setBackground(new Color(51, 51, 51));
		saleMods.setBackground(new Color(51, 51, 51));
		
		saleItems.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout3.show(tabHolder, "tab1");
			}
		});
		saleItems.setBounds(12, 12, 160, 60);
		salesPane.add(saleItems);
		saleItems.setLayout(new BorderLayout(0, 0));
		
		JLabel lblSAll = new JLabel("ALL ITEMS");
		lblSAll.setBackground(new Color(102, 153, 51));
		lblSAll.setForeground(Color.WHITE);
		lblSAll.setHorizontalAlignment(SwingConstants.CENTER);
		lblSAll.setFont(new Font("Tahoma", Font.BOLD, 18));
		saleItems.add(lblSAll);
		
		saleEliquid.setBounds(170, 12, 169, 60);
		salesPane.add(saleEliquid);
		saleEliquid.setBackground(new Color(51, 51, 51));

		saleEliquid.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout3.show(tabHolder, "tab2");
			}
		});
		saleEliquid.setLayout(new BorderLayout(0, 0));
		JLabel lblLiquid = new JLabel("ELIQUID");
		lblLiquid.setForeground(Color.WHITE);
		lblLiquid.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblLiquid.setHorizontalAlignment(SwingConstants.CENTER);
		saleEliquid.add(lblLiquid);
		
		saleAccesories.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout3.show(tabHolder, "tab3");

			}
		});

		saleAccesories.setBounds(339, 12, 169, 60);
		salesPane.add(saleAccesories);
		saleAccesories.setLayout(new BorderLayout(0, 0));
		JLabel lblAccesories = new JLabel("ACCESSORIES");
		lblAccesories.setForeground(Color.WHITE);
		lblAccesories.setHorizontalAlignment(SwingConstants.CENTER);
		lblAccesories.setFont(new Font("Tahoma", Font.BOLD, 18));
		saleAccesories.add(lblAccesories, BorderLayout.CENTER);
		
		saleMods.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout3.show(tabHolder, "tab4");
			}
		});
		saleMods.setBounds(508, 12, 169, 60);
		salesPane.add(saleMods);
		saleMods.setLayout(new BorderLayout(0, 0));

		JLabel lblMods = new JLabel("MODS");
		lblMods.setForeground(Color.WHITE);
		lblMods.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMods.setHorizontalAlignment(SwingConstants.CENTER);
		saleMods.add(lblMods, BorderLayout.CENTER);

		saleAtoms.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout3.show(tabHolder, "tab5");

			}
		});
		saleAtoms.setBounds(677, 12, 169, 60);
		salesPane.add(saleAtoms);
		saleAtoms.setLayout(new BorderLayout(0, 0));

		JLabel lblAtom = new JLabel("ATOMIZERS");
		lblAtom.setForeground(Color.WHITE);
		lblAtom.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAtom.setHorizontalAlignment(SwingConstants.CENTER);
		saleAtoms.add(lblAtom, BorderLayout.CENTER);

		saleServices.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cardLayout3.show(tabHolder, "tab6");

			}
		});
		saleServices.setBounds(846, 12, 161, 60);
		salesPane.add(saleServices);
		saleServices.setLayout(new BorderLayout(0, 0));

		JLabel lblServices = new JLabel("SERVICES");
		lblServices.setForeground(Color.WHITE);
		lblServices.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblServices.setHorizontalAlignment(SwingConstants.CENTER);
		saleServices.add(lblServices, BorderLayout.CENTER);
		
		int c1 = 102, c2 = 153, c3 = 51;
		
		// color change in tab buttons
		saleItems.addMouseListener(new Tabs(c1, c2, c3, saleItems, saleEliquid, saleAccesories, saleMods, saleAtoms, saleServices));
		saleEliquid.addMouseListener(new Tabs(c1,c2,c3, saleEliquid, saleItems, saleAccesories, saleMods, saleAtoms, saleServices));
		saleAccesories.addMouseListener(new Tabs(c1,c2,c3, saleAccesories, saleItems, saleEliquid, saleMods, saleAtoms, saleServices));
		saleMods.addMouseListener(new Tabs(c1,c2,c3, saleMods, saleItems, saleEliquid, saleAccesories, saleAtoms, saleServices));
		saleAtoms.addMouseListener(new Tabs(c1,c2,c3, saleAtoms, saleItems, saleEliquid, saleAccesories, saleMods, saleServices));
		saleServices.addMouseListener(new Tabs(c1,c2,c3, saleServices, saleItems, saleEliquid, saleAccesories, saleMods, saleAtoms));

		// makes border when mouse entered
		saleItems.addMouseListener(new TabListener(lblSAll));
		saleEliquid.addMouseListener(new TabListener(lblLiquid));
		saleAccesories.addMouseListener(new TabListener(lblAccesories));
		saleMods.addMouseListener(new TabListener(lblMods));
		saleAtoms.addMouseListener(new TabListener(lblAtom));
		saleServices.addMouseListener(new TabListener(lblServices));

		
//END OF TAB HEADERS-----------------------------------------------------------------------------------
		
		
		
		//TAB 1 CONTENT ------------------------------------------------------------------------------
		tab1Content.setBackground(new Color(0, 51, 0));
		tab1Content.setLayout(null);
		tabHolder.add(tab1Content, "tab1");
		
		model1 = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableAItems=new JTable(model1);
		tableAItems.setBackground(new Color(176, 196, 222));
		tableAItems.setBounds(186, 95, 333, 200);
		
		JScrollPane scpnProduct = new JScrollPane(tableAItems);
		scpnProduct.setBounds(0, 38, 950, 482);
		tab1Content.add(scpnProduct);
		
		totInvent = new JTextField();
		totInvent.setEditable(false);
		totInvent.setBounds(10, 5, 200, 30);
		totInvent.setFont(new Font("Tahoma", Font.BOLD, 13));
		totInvent.setHorizontalAlignment(SwingConstants.LEFT);
		tab1Content.add(totInvent);
		totInvent.setColumns(10);
		
		totSales = new JTextField();
		totSales.setEditable(false);
		totSales.setBounds(740, 5, 200, 30);
		totSales.setFont(new Font("Tahoma", Font.BOLD, 13));
		totSales.setHorizontalAlignment(SwingConstants.RIGHT);
		tab1Content.add(totSales);
		totSales.setColumns(10);
		rowSorter = new TableRowSorter<>(tableAItems.getModel());
		tableAItems.setRowSorter(rowSorter);
		ExtraMethods.searchFilter(rowSorter, txtSearch, "Type to Search");

		
//TAB 2 CONTENT--------------------------------------------------------------------------

		tab2Content.setBackground(new Color(0, 102, 0));
		tabHolder.add(tab2Content, "tab2");
		tab2Content.setLayout(null);
		
		model2 = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column==7)
					return true;
				else
				return false;
			}
			
			public Class<?> getColumnClass(int col){
				if(col == 0 || col == 4)
					return int.class;
				else if(  col == 1 || col == 2 )
					return String.class;
				else if(col == 3 || col == 5 )
					return Double.class;
				else if(col == 6 )
					return Date.class;
				else
					return Boolean.class;
			}		
		};
		
		tableELiquid=new JTable(model2);
		tableELiquid.setBackground(new Color(176, 196, 222));
		tableELiquid.setBounds(186, 95, 333, 200);
		
		JScrollPane scpnELiquid = new JScrollPane(tableELiquid);
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
		rowSorter = new TableRowSorter<>(tableELiquid.getModel());
		tableELiquid.setRowSorter(rowSorter);
		ExtraMethods.searchFilter(rowSorter, txtSearch, "Type to Search");

		
		//TAB 3 CONTENT--------------------------------------------------------------------------
		
		tab3Content.setBackground(new Color(0, 153, 0));
		tabHolder.add(tab3Content, "tab3");
		tab3Content.setLayout(null);
		  model3 = new DefaultTableModel() {
				@Override			
				public boolean isCellEditable(int row, int column) {
					if(column==7)
						return true;
					else
					return false;
				}

				public Class<?> getColumnClass(int col){
					if(col == 0 || col == 4)
						return int.class;
					else if(  col == 1 || col == 2 )
						return String.class;
					else if(col == 3 || col == 5 )
						return Double.class;
					else if(col == 6 )
						return Date.class;
					else
						return Boolean.class;
				}
			};
			
			
		tableAccesr=new JTable(model3);
		tableAccesr.setBackground(new Color(176, 196, 222));
		tableAccesr.setBounds(186, 95, 333, 200);
		
		JScrollPane scpnAccesr = new JScrollPane(tableAccesr);
		scpnAccesr.setBounds(0, 38, 950, 482);
		tab3Content.add(scpnAccesr);
		tab3Content.setLayout(null);
		
		totAcc = new JTextField();
		totAcc.setEditable(false);
		totAcc.setColumns(10);
		totAcc.setFont(new Font("Tahoma", Font.BOLD, 13));
		totAcc.setBounds(10, 5, 200, 30);
		
		tab3Content.add(totAcc);
		
		totSAcc = new JTextField();
		totSAcc.setEditable(false);
		totSAcc.setBounds(740, 5, 200, 30);
		totSAcc.setFont(new Font("Tahoma", Font.BOLD, 13));
		totSAcc.setHorizontalAlignment(SwingConstants.RIGHT);
		tab3Content.add(totSAcc);
		totSAcc.setColumns(10);
		rowSorter = new TableRowSorter<>(tableAccesr.getModel());
		tableAccesr.setRowSorter(rowSorter);
		ExtraMethods.searchFilter(rowSorter, txtSearch, "Type to Search");
		
//TAB 4 CONTENT--------------------------------------------------------------------------
		tab4Content.setBackground(new Color(51, 102, 0));
		tabHolder.add(tab4Content,"tab4");
		
		model4 = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column==7)
					return true;
				else
				return false;
			}
			

			public Class<?> getColumnClass(int col){
				if(col == 0 || col == 4)
					return int.class;
				else if(  col == 1 || col == 2 )
					return String.class;
				else if(col == 3 || col == 5 )
					return Double.class;
				else if(col == 6 )
					return Date.class;
				else
					return Boolean.class;
			}
		};
		tableMods=new JTable(model4);
		tableMods.setBackground(new Color(176, 196, 222));
		tableMods.setBounds(186, 95, 333, 200);
		
		JScrollPane scpnMod = new JScrollPane(tableMods);
		scpnMod.setBounds(0, 38, 950, 482);
		tab4Content.add(scpnMod);
		tab4Content.setLayout(null);
		
		totMods = new JTextField();
		totMods.setEditable(false);
		totMods.setColumns(10);
		totMods.setBounds(10, 5, 200, 30);
		totMods.setFont(new Font("Tahoma", Font.BOLD, 13));
		tab4Content.add(totMods);
		
		totSMods = new JTextField();
		totSMods.setEditable(false);
		totSMods.setBounds(740, 5, 200, 30);
		totSMods.setFont(new Font("Tahoma", Font.BOLD, 13));
		totSMods.setHorizontalAlignment(SwingConstants.RIGHT);
		tab4Content.add(totSMods);
		totSMods.setColumns(10);
		rowSorter = new TableRowSorter<>(tableMods.getModel());
		tableMods.setRowSorter(rowSorter);
		ExtraMethods.searchFilter(rowSorter, txtSearch, "Type to Search");
		
		//TAB 5 CONTENT--------------------------------------------------------------------------
		
		tabHolder.add(tab5Content, "tab5");

		model5 = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column==7)
					return true;
				else
				return false;
			}

			public Class<?> getColumnClass(int col){
				if(col == 0 || col == 4)
					return int.class;
				else if(  col == 1 || col == 2 )
					return String.class;
				else if(col == 3 || col == 5 )
					return Double.class;
				else if(col == 6 )
					return Date.class;
				else
					return Boolean.class;
			}
		};
		tableAtom=new JTable(model5);
		tableAtom.setBackground(new Color(176, 196, 222));
		tableAtom.setBounds(186, 95, 333, 200);
						
		JScrollPane scpnAtom = new JScrollPane(tableAtom);
		scpnAtom.setBounds(0, 38, 950, 482);
		tab5Content.add(scpnAtom);
		tab5Content.setLayout(null);										
		tab5Content.setBackground(new Color(153, 204, 102));
		tabHolder.add(tab5Content,"tab5");
		
		totAtom = new JTextField();
		totAtom.setEditable(false);
		totAtom.setColumns(10);
		totAtom.setFont(new Font("Tahoma", Font.BOLD, 13));
		totAtom.setBounds(10, 5, 200, 30);
		tab5Content.add(totAtom);
		
		totSAtom = new JTextField();
		totSAtom.setEditable(false);
		totSAtom.setBounds(740, 5, 200, 30);
		totSAtom.setFont(new Font("Tahoma", Font.BOLD, 13));
		totSAtom.setHorizontalAlignment(SwingConstants.RIGHT);
		tab5Content.add(totSAtom);
		totSAtom.setColumns(10);
		rowSorter = new TableRowSorter<>(tableAtom.getModel());
		tableAtom.setRowSorter(rowSorter);
		ExtraMethods.searchFilter(rowSorter, txtSearch, "Type to Search");
		
		//TAB 6 CONTENT--------------------------------------------------------------------------					
		tabHolder.add(tab6Content, "tab6");
		tab6Content.setLayout(null);

		model6 = new DefaultTableModel() {
				@Override
				public boolean isCellEditable(int row, int column) {
					if(column==6)
						return true;
					else
						return false;
				}
				public Class<?> getColumnClass(int col){
					if(col == 0 )
						return int.class;
					else if(col == 2 || col == 1)
						return String.class;
					else if(col == 3 )
						return Double.class;
					else if(col == 4 )
						return Date.class;
					else
						return Boolean.class;
				}
			};
		tableService=new JTable(model6);
		tableService.setBackground(new Color(176, 196, 222));
		tableService.setBounds(186, 95, 333, 200);
						
		JScrollPane scpnService = new JScrollPane(tableService);
		scpnService.setBounds(0, 38, 950, 482);
		tab6Content.add(scpnService);
		tabHolder.add(tab4Content, "tab4");
						
		tab6Content.setBackground(new Color(204, 255, 153));
		tabHolder.add(tab6Content,"tab6");
		
		totServ = new JTextField();
		totServ.setEditable(false);
		totServ.setColumns(10);
		totServ.setFont(new Font("Tahoma", Font.BOLD, 13));
		totServ.setBounds(10, 5, 200, 30);
		tab6Content.add(totServ);
		
		totSServ = new JTextField();
		totSServ.setEditable(false);
		totSServ.setBounds(740, 5, 200, 30);
		totSServ.setFont(new Font("Tahoma", Font.BOLD, 13));
		totSServ.setHorizontalAlignment(SwingConstants.RIGHT);
		tab6Content.add(totSServ);
		totSServ.setColumns(10);
				
		rowSorter = new TableRowSorter<>(tableService.getModel());
		tableService.setRowSorter(rowSorter);
		ExtraMethods.searchFilter(rowSorter, txtSearch, "Type to Search");
		
		query();
		
		//TABLE LISTENER FOR VOIDED ITEMS
		tableELiquid.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				ExtraMethods.statusSalesUpdater(model2,0,1,7,1);
			}
		});
		
		tableAccesr.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				ExtraMethods.statusSalesUpdater(model3,0,1,7,1);
				
			}
		});
		
		tableMods.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				ExtraMethods.statusSalesUpdater(model4,0,1,7,1);
			}
		});
		
		tableAtom.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				ExtraMethods.statusSalesUpdater(model5,0,1,7,1);
			}
		});
		
		tableService.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				ExtraMethods.statusSalesUpdater(model6,0,1,5,0);
			}
		});
	
	}
	//END OF SALES PANE-----------------------------------------------------------------------------------------------
	
	//=====================================================================================================
	
	//QUERY  DATA FROM SALES---------------------------------------------------------------------
	public static void query() {
		
		totalAllSales = 0.00;
		saleEl = 0.00;
		saleAcc = 0.00;
		saleMods = 0.00;
		saleAtom = 0.00;
		saleServ = 0.00;
		totalAll=0;
		totalEl=0;
		totalAcc=0;
		totalMods=0;
		totalAtom=0;
		totalService=0;
		
	Object colAllName[] = new Object[] {"Trans Code","Item Code","Name","Category","Sell Price","Total Sold","Total Sales","Date"}; // 8
    Object colName[] = new Object[] {"Trans Code","Item Code", "Name","Sell Price","Total Sold","Total Sales","Date", "Void"};  // 8
	model6.setColumnIdentifiers(new Object[] {"Trans Code","Service Code","Name","Fee", "Date Rendered","Void"});
	String cat;
	
	model1.setRowCount(0);
	model2.setRowCount(0);
	model3.setRowCount(0);
	model4.setRowCount(0);
	model5.setRowCount(0);
	model6.setRowCount(0);

    model1.setColumnIdentifiers(colAllName);
	model2.setColumnIdentifiers(colName);
	model3.setColumnIdentifiers(colName);
	model4.setColumnIdentifiers(colName);
	model5.setColumnIdentifiers(colName);
	
	Object[] rD = new Object[8];
	for(int j=0;j<2;j++) {
		System.out.print("\nEntered Loop = j");
		System.out.print("\nsize: " + SalesDao.getSales(j,start,end).size());
		
		//GETS THE SIZE OF ARRAYLIST : SalesDao.getSales(j,start,end).size()
			for(int i=0;i<SalesDao.getSales(j,start,end).size();i++) {
				
		 	if(j==1) {
		 		System.out.print("\nEntered if");
		 		
		 		rD[0] = SalesDao.getSales(j,start,end).get(i).getTrans();
			 	rD[1] = SalesDao.getSales(j,start,end).get(i).getId();
		 		rD[2] = SalesDao.getSales(j,start,end).get(i).getName();
			 	rD[3] = SalesDao.getSales(j,start,end).get(i).getServCh();
			 	rD[4] = SalesDao.getSales(j,start,end).get(i).getDrend();
			 	rD[5] = SalesDao.getSales(j,start,end).get(i).getFlag();
			 	if(!Boolean.parseBoolean(rD[6].toString()))
			 	model1.addRow(new Object[] {rD[0],rD[1],rD[2],"Services",rD[3],"N/A","N/A","N/A",rD[4]});
				model6.addRow(rD);
				totalAll+=1;
				totalService+=1;
	 			totalAllSales+=Double.parseDouble(rD[3].toString());
	 			saleServ+=Double.parseDouble(rD[3].toString());
		 	}
		 	else {
		 		if(!SalesDao.getSales(j,start,end).isEmpty()) {
		 			rD[0] = SalesDao.getSales(j,start,end).get(i).getTrans();
		 			rD[1] = SalesDao.getSales(j,start,end).get(i).getId();
				 	rD[2] = SalesDao.getSales(j,start,end).get(i).getName();
		 			cat = SalesDao.getSales(j,start,end).get(i).getCateg().toString();
				 	rD[3] = SalesDao.getSales(j,start,end).get(i).getSell();
		 			rD[4] = SalesDao.getSales(j,start,end).get(i).getSold();
		 			rD[5] = SalesDao.getSales(j,start,end).get(i).getTSales();
		 			rD[6] = SalesDao.getSales(j,start,end).get(i).getDSold();
		 			rD[7] = SalesDao.getSales(j, start, end).get(i).getFlag();
		 			if(!Boolean.parseBoolean(rD[7].toString())) {
					 	model1.addRow(new Object[] {rD[0],rD[1],rD[2],cat,rD[3],rD[4],rD[5],rD[6]});
		 			totalAll+=1;
		 			totalAllSales+=Double.parseDouble(rD[5].toString());
		 			}
		 			
		 			if(cat.equals("ELiquid")) {
		 				model2.addRow(rD);
		 				totalEl+=1;
		 				saleEl+=Double.parseDouble(rD[5].toString());
		 			}
		 			else if(cat.equals("Accesories")) {
		 				model3.addRow(rD);
		 				totalAcc+=1;
		 				saleAcc+=Double.parseDouble(rD[5].toString());
		 			}
		 			else if(cat.equals("Mods")) {
		 				model4.addRow(rD);
		 				totalMods+=1;
		 				saleMods+=Double.parseDouble(rD[5].toString());
		 			}
		 			else if(cat.equals("Atomizers")) {
		 				model5.addRow(rD);
		 				totalAtom+=1;
		 				saleAtom+=Double.parseDouble(rD[5].toString());
		 			}
		 	}//END OF IF
				
		 		
			}//END OF ELSE
			
			
		}//END OF FOR I
 		
	}//END OF FOR J
	
	//SET THE TOTAL NUMBER OF ITEMS
	getTotal(totInvent,totSales,totEliquid,totAcc,totMods,totAtom,
			totServ,totSEliquid,totSAcc,totSMods,totSAtom,totSServ);
	

}//END OF QUERY FUNCTION


public static void getTotal(JTextField tot1,JTextField tot2,JTextField tot3,JTextField tot4,JTextField tot5,JTextField tot6,
		JTextField tot7,JTextField tot8,JTextField tot9,JTextField tot10,JTextField tot11,JTextField tot12) {
	
	//SET THE TOTAL NUMBER OF ITEMS
		tot1.setText(String.valueOf(totalAll) + " Sold");		
		tot2.setText("Php" + " " + String.valueOf(totalAllSales));
		
		tot3.setText(String.valueOf(totalEl) + " Sold");
		tot4.setText(String.valueOf(totalAcc) + " Sold");
		tot5.setText(String.valueOf(totalMods) + " Sold");
		tot6.setText(String.valueOf(totalAtom) + " Sold");
		tot7.setText(String.valueOf(totalService) + " Sold");

		tot8.setText("Php" + " " + String.valueOf(saleEl));
		tot9.setText("Php" + " " + String.valueOf(saleAcc));
		tot10.setText("Php" + " " + String.valueOf(saleMods));
		tot11.setText("Php" + " " + String.valueOf(saleAtom));
		tot12.setText("Php" + " " + String.valueOf(saleServ));

}

//===========================================================================================================
		
}//END OF CLASS
