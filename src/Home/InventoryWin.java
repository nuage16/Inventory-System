package Home;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;
import java.awt.Insets;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import Beans.Inventory;
import Extras.ButtonRenderer;
import Extras.BtnColor;
import Extras.ExtraMethods;
import Extras.TabListener;
import Extras.Tabs;
import Home.DBConnection;
import DAO.InventoryDao;
import DAO.SalesDao;

public class InventoryWin extends JPanel {
	
	JPanel inventoryPane;
	
	private static JTextField totInvent;
	private static JTextField totEliquid;
	private static JTextField totAcc;
	private static JTextField totMods;
	private static JTextField totAtom;
	private static JTextField totServ;
	private static JTextField txtSearch;
	
	private static DefaultTableModel model1;
	private static DefaultTableModel model2;
	private static DefaultTableModel model3;
	private static DefaultTableModel model4;
	private static DefaultTableModel model5;
	private static DefaultTableModel model6;
	
	private static JTable tableAItems;
	private static JTable tableELiquid;
	private static JTable tableAccesr;
	private static JTable tableMods;
	private static JTable tableAtom;
	private static JTable tableService;
	
	private static Object colAllName[] = new Object[] {"Item Code", "Name", "Brand", "Category","Description",
													   "Stock","Cost Price","Markup","Sell Price"};
    private static Object colName[] = new Object[] {"Item Code", "Name", "Brand","Description","Stock",
    												"Cost Price","Markup","Sell Price","Flag"};
	
	private TableRowSorter<TableModel> rowSorter;
	
		
	public InventoryWin() {
			
		//INVENTORY HEADER TABS
		inventoryPane = new JPanel();
		inventoryPane.setBorder(new CompoundBorder(new MatteBorder(10, 10, 10, 10, (Color) new Color(128, 128, 128)),
				new EtchedBorder(EtchedBorder.LOWERED, new Color(192, 192, 192), new Color(0, 0, 0))));
		inventoryPane.setBackground(new Color(51, 102, 153));
		inventoryPane.setBounds(255, 0, 1019, 691);
		inventoryPane.setLayout(null);
		inventoryPane.setVisible(true);

		txtSearch = new JTextField();
		txtSearch.setBounds(505, 90, 360, 34);
		inventoryPane.add(txtSearch);
		ExtraMethods.txtPlaceHolder(txtSearch, "Type to Search");

		JButton btnNewButton_4 = new JButton("+ Add Item");
		btnNewButton_4.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), Color.DARK_GRAY));
		btnNewButton_4.setMargin(new Insets(2, 2, 2, 2));
		btnNewButton_4.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_4.setForeground(Color.WHITE);
		btnNewButton_4.setBackground(new Color(0, 0, 51));
		btnNewButton_4.addMouseListener(new BtnColor(btnNewButton_4));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					 new AddProductFrame();
				}
		});
		btnNewButton_4.setBounds(35, 90, 96, 34);
		inventoryPane.add(btnNewButton_4);

		JButton btnModify = new JButton("Modify");
		btnModify.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), new Color(64, 64, 64)));
		btnModify.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnModify.setForeground(Color.WHITE);
		btnModify.setBackground(new Color(0, 0, 51));
		btnModify.setBounds(144, 90, 96, 34);
		inventoryPane.add(btnModify);
		btnModify.addMouseListener(new BtnColor(btnModify));
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EditProductFrame();
				query();
			}
		});
	
		JButton btnStock = new JButton("+ Stocks");
		btnStock.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(255, 255, 255), new Color(204, 204, 204),
				new Color(102, 102, 102), new Color(64, 64, 64)));
		btnStock.setMargin(new Insets(2, 2, 2, 2));
		btnStock.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnStock.setForeground(Color.WHITE);
		btnStock.setBackground(new Color(0, 0, 51));
		btnStock.setBounds(254, 90, 96, 34);
		inventoryPane.add(btnStock);
		btnStock.addMouseListener(new BtnColor(btnStock));
		btnStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UpdateStocks();
				query();
				}
		});
			
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), Color.DARK_GRAY));
		btnRefresh.setForeground(Color.WHITE);
		btnRefresh.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnRefresh.setBackground(new Color(102, 153, 204));
		btnRefresh.setBounds(895, 89, 90, 34);
		inventoryPane.add(btnRefresh);
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
			
			
			JPanel eliquid = new JPanel();
			JPanel aItems = new JPanel();
			JPanel accesories = new JPanel();
			JPanel services = new JPanel();
			JPanel atoms = new JPanel();
			JPanel mods = new JPanel();
			
			JPanel tabHolder = new JPanel();
			tabHolder.setBackground(new Color(255,255,255,255));
			tabHolder.setBounds(35, 143, 950, 520);
			inventoryPane.add(tabHolder);
			tabHolder.setLayout(new CardLayout(0, 0));
			
			CardLayout cardLayout2 = (CardLayout)(tabHolder.getLayout()); 
			
			JPanel tab1Content = new JPanel();
			JPanel tab2Content = new JPanel();
			JPanel tab3Content = new JPanel();
			JPanel tab4Content = new JPanel();
			JPanel tab5Content = new JPanel();
			JPanel tab6Content = new JPanel();
			
			
			inventoryPane.add(aItems);
			inventoryPane.add(eliquid);
			inventoryPane.add(accesories);
			inventoryPane.add(mods);
			inventoryPane.add(atoms);
			inventoryPane.add(services);
			
			tabHolder.add(tab1Content, "tab1");
			tabHolder.add(tab2Content, "tab2");
			tabHolder.add(tab3Content, "tab3");
			tabHolder.add(tab4Content, "tab4");
			tabHolder.add(tab5Content, "tab5");
			tabHolder.add(tab6Content, "tab6");
			
			
		
			// initial tab panel-button borders
			eliquid.setBorder(new MatteBorder(0, 0, 3, 1, (Color.BLACK)));
			aItems.setBorder(new MatteBorder(3, 3, 0, 3, (Color.BLACK)));
			accesories.setBorder(new MatteBorder(0, 0, 3, 1, (Color.BLACK)));
			services.setBorder(new MatteBorder(0, 0, 3, 1, (Color.BLACK)));
			atoms.setBorder(new MatteBorder(0, 0, 3, 1, (Color.BLACK)));
			mods.setBorder(new MatteBorder(0, 0, 3, 1, (Color.BLACK)));

			// initial color of the tab panel-buttons
			aItems.setBackground(new Color(51, 102, 153));
			accesories.setBackground(new Color(51, 51, 51));
			services.setBackground(new Color(51, 51, 51));
			atoms.setBackground(new Color(51, 51, 51));
			mods.setBackground(new Color(51, 51, 51));
			eliquid.setBackground(new Color(51, 51, 51));
			
			aItems.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					cardLayout2.show(tabHolder, "tab1");
				}
			});
			aItems.setBounds(12, 12, 158, 58);
			aItems.setLayout(new BorderLayout(0, 0));
			
			JLabel lblaItems = new JLabel("ALL ITEMS");
			lblaItems.setForeground(Color.WHITE);
			lblaItems.setHorizontalAlignment(SwingConstants.CENTER);
			lblaItems.setFont(new Font("Tahoma", Font.BOLD, 18));
			aItems.add(lblaItems);
			
			eliquid.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					cardLayout2.show(tabHolder, "tab2");
				}
			});
			eliquid.setBounds(170, 12, 169, 58);
			eliquid.setLayout(new BorderLayout(0, 0));

			JLabel lbleliquid = new JLabel("ELIQUID");
			lbleliquid.setForeground(Color.WHITE);
			lbleliquid.setFont(new Font("Tahoma", Font.BOLD, 18));
			lbleliquid.setHorizontalAlignment(SwingConstants.CENTER);
			eliquid.add(lbleliquid);
			
			accesories.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					cardLayout2.show(tabHolder, "tab3");
				}
			});
			accesories.setBounds(339, 12, 169, 58);
			accesories.setLayout(new BorderLayout(0, 0));

			JLabel lblAcc = new JLabel("ACCESSORIES");
			lblAcc.setBackground(SystemColor.textHighlight);
			lblAcc.setForeground(Color.WHITE);
			lblAcc.setHorizontalAlignment(SwingConstants.CENTER);
			lblAcc.setFont(new Font("Tahoma", Font.BOLD, 18));
			accesories.add(lblAcc, BorderLayout.CENTER);
			
			mods.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					cardLayout2.show(tabHolder, "tab4");
				}
			});
			mods.setBounds(508, 12, 169, 58);
			mods.setLayout(new BorderLayout(0, 0));

			JLabel lblMods = new JLabel("MODS");
			lblMods.setBackground(SystemColor.textHighlight);
			lblMods.setForeground(Color.WHITE);
			lblMods.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblMods.setHorizontalAlignment(SwingConstants.CENTER);
			mods.add(lblMods, BorderLayout.CENTER);
			
			atoms.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					cardLayout2.show(tabHolder, "tab5");
				}
			});
			atoms.setBounds(677, 12, 169, 58);
			atoms.setLayout(new BorderLayout(0, 0));

			JLabel lblAtom = new JLabel("ATOMIZERS");
			lblAtom.setForeground(Color.WHITE);
			lblAtom.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblAtom.setHorizontalAlignment(SwingConstants.CENTER);
			atoms.add(lblAtom, BorderLayout.CENTER);
			
			services.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					cardLayout2.show(tabHolder, "tab6");
				}
			});
			services.setBounds(846, 12, 161, 58);
			services.setLayout(new BorderLayout(0, 0));

			JLabel lblServices = new JLabel("SERVICES");
			lblServices.setForeground(Color.WHITE);
			lblServices.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblServices.setHorizontalAlignment(SwingConstants.CENTER);
			services.add(lblServices, BorderLayout.CENTER);
			
			int c1 = 51, c2 = 102, c3 = 153;
			// for the color change when the tab is clicked
			aItems.addMouseListener(new Tabs(c1, c2, c3, aItems, eliquid, accesories, mods, atoms, services));
			eliquid.addMouseListener(new Tabs(c1, c2, c3, eliquid, aItems, accesories, mods, atoms, services));
			accesories.addMouseListener(new Tabs(c1, c2, c3, accesories, aItems, eliquid, mods, atoms, services));
			mods.addMouseListener(new Tabs(c1, c2, c3, mods, aItems, eliquid, accesories, atoms, services));
			atoms.addMouseListener(new Tabs(c1, c2, c3, atoms, aItems, eliquid, accesories, mods, services));
			services.addMouseListener(new Tabs(c1, c2, c3, services, aItems, eliquid, accesories, mods, atoms));

			// makes border when mouse entered
			aItems.addMouseListener(new TabListener(lblaItems));
			eliquid.addMouseListener(new TabListener(lbleliquid));
			accesories.addMouseListener(new TabListener(lblAcc));
			mods.addMouseListener(new TabListener(lblMods));
			atoms.addMouseListener(new TabListener(lblAtom));
			services.addMouseListener(new TabListener(lblServices));
			
			//END OF HEADER TAB-----------------------------------------------------------------------------------
			
			
			//TAB 1 CONTENT ------------------------------------------------------------------------------
			tab1Content.setBackground(new Color(0, 51, 102));
			tab1Content.setLayout(null);

			model1 = new DefaultTableModel() {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}

			};
			model1.setColumnIdentifiers(colAllName);
			tableAItems = new JTable(model1);
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

			rowSorter = new TableRowSorter<>(tableAItems.getModel());
			tableAItems.setRowSorter(rowSorter);
			ExtraMethods.searchFilter(rowSorter, txtSearch, "Type to Search");
					
			//TAB 2 CONTENT--------------------------------------------------------------------------
			
					tab2Content.setBackground(new Color(0, 51, 153));
					tabHolder.add(tab2Content, "tab2");
					tab2Content.setLayout(null);
					
					 model2 = new DefaultTableModel() {
							@Override
							public boolean isCellEditable(int row, int column) {
								if(column==8)
									return true;
								return false;
							}
							
							public Class<?> getColumnClass(int col){
								if(col == 4)
									return int.class;
								else if(col == 0 || col == 1 || col == 2 || col == 3)
									return String.class;
								else if(col == 5 || col == 6 || col == 7)
									return Double.class;
								else
									return Boolean.class;
							}
					};
					model2.setColumnIdentifiers(colName);
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

					rowSorter = new TableRowSorter<>(tableELiquid.getModel());
					tableELiquid.setRowSorter(rowSorter);
					ExtraMethods.searchFilter(rowSorter, txtSearch, "Type to Search");
					
			//TAB 3 CONTENT--------------------------------------------------------------------------
					
					tab3Content.setBackground(new Color(0, 51, 204));
					tabHolder.add(tab3Content, "tab3");
					tab3Content.setLayout(null);
					model3 = new DefaultTableModel() {
							@Override
							public boolean isCellEditable(int row, int column) {
								if(column==8)
									return true;
								return false;
							}
							
							public Class<?> getColumnClass(int col){
								if(col == 4)
									return int.class;
								else if(col == 0 || col == 1 || col == 2 || col == 3)
									return String.class;
								else if(col == 5 || col == 6 || col == 7)
									return Double.class;
								else
									return Boolean.class;
							}
						};
					model3.setColumnIdentifiers(colName);
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
					
					
					
					rowSorter = new TableRowSorter<>(tableAccesr.getModel());
					tableAccesr.setRowSorter(rowSorter);
					ExtraMethods.searchFilter(rowSorter, txtSearch, "Type to Search");
					
			//TAB 4 CONTENT--------------------------------------------------------------------------
					tab4Content.setBackground(new Color(0, 102, 204));
					tabHolder.add(tab4Content,"tab4");
					
					model4 = new DefaultTableModel() {
							@Override
							public boolean isCellEditable(int row, int column) {
								if(column==8)
									return true;
								return false;
							}
							
							public Class<?> getColumnClass(int col){
								if(col == 4)
									return int.class;
								else if(col == 0 || col == 1 || col == 2 || col == 3)
									return String.class;
								else if(col == 5 || col == 6 || col == 7)
									return Double.class;
								else
									return Boolean.class;
							}
						};
					model4.setColumnIdentifiers(colName);
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
					tabHolder.add(tab4Content, "tab4");
					
					rowSorter = new TableRowSorter<>(tableMods.getModel());
					tableMods.setRowSorter(rowSorter);
					ExtraMethods.searchFilter(rowSorter, txtSearch, "Type to Search");
					
			//TAB 5 CONTENT--------------------------------------------------------------------------
					model5 = new DefaultTableModel() {
							@Override
							public boolean isCellEditable(int row, int column) {
								if(column==8)
									return true;
								return false;
							}
							
							public Class<?> getColumnClass(int col){
								if(col == 4)
									return int.class;
								else if(col == 0 || col == 1 || col == 2 || col == 3)
									return String.class;
								else if(col == 5 || col == 6 || col == 7)
									return Double.class;
								else
									return Boolean.class;
							}
						};
					model5.setColumnIdentifiers(colName);	
					tableAtom=new JTable(model5);
					tableAtom.setBackground(new Color(176, 196, 222));
					tableAtom.setBounds(186, 95, 333, 200);
									
					JScrollPane scpnAtom = new JScrollPane(tableAtom);
					scpnAtom.setBounds(0, 38, 950, 482);
					tab5Content.add(scpnAtom);
					tab5Content.setLayout(null);
					tab6Content.setLayout(null);
													
					tab5Content.setBackground(new Color(51, 153, 255));
					tabHolder.add(tab5Content,"tab5");
					
					
					totAtom = new JTextField();
					totAtom.setEditable(false);
					totAtom.setColumns(10);
					totAtom.setFont(new Font("Tahoma", Font.BOLD, 13));
					totAtom.setBounds(10, 5, 200, 30);
					tab5Content.add(totAtom);
					
					rowSorter = new TableRowSorter<>(tableAtom.getModel());
					tableAtom.setRowSorter(rowSorter);
					ExtraMethods.searchFilter(rowSorter, txtSearch, "Type to Search");
					
			//TAB 6 CONTENT--------------------------------------------------------------------------					
					model6 = new DefaultTableModel() {
							@Override
							public boolean isCellEditable(int row, int column) {
								if(column == 3)
									return true;
								else
								return false;
							}
							
							public Class<?> getColumnClass(int col){
								if(col == 1  || col== 0)
									return String.class;
								else if(col == 2)
									return Double.class;
								else
									return Boolean.class;
							}
						};
						
					model6.setColumnIdentifiers(new Object[] {"Service Code","Name","Fee","Flag"});
						
					tableService=new JTable(model6);
					tableService.setBackground(new Color(176, 196, 222));
					tableService.setBounds(186, 95, 333, 200);
									
					JScrollPane scpnService = new JScrollPane(tableService);
					scpnService.setBounds(0, 38, 950, 482);
					tab6Content.add(scpnService);
					
					tab6Content.setBackground(new Color(153, 204, 255));
					tabHolder.add(tab6Content,"tab6");
					
					totServ = new JTextField();
					totServ.setEditable(false);
					totServ.setColumns(10);
					totServ.setFont(new Font("Tahoma", Font.BOLD, 13));
					totServ.setBounds(10, 5, 200, 30);
					tab6Content.add(totServ);
					   
					
					rowSorter = new TableRowSorter<>(tableService.getModel());
					tableService.setRowSorter(rowSorter);
					ExtraMethods.searchFilter(rowSorter, txtSearch, "Type to Search");
						
				    
					//QUERY ITEMS TO BE DISPLAYED ONTO TABLE
					query();
	
					//TABLE LISTENER FOR VOIDED ITEMS
					tableELiquid.getModel().addTableModelListener(new TableModelListener() {
						@Override
						public void tableChanged(TableModelEvent e) {
							ExtraMethods.statusUpdater(model2,0,8,1);
						}
					});
					
					tableAccesr.getModel().addTableModelListener(new TableModelListener() {
						@Override
						public void tableChanged(TableModelEvent e) {
							ExtraMethods.statusUpdater(model3,0,8,1);
						}
					});
					
					tableMods.getModel().addTableModelListener(new TableModelListener() {
						@Override
						public void tableChanged(TableModelEvent e) {
							ExtraMethods.statusUpdater(model4,0,8,1);
						}
					});
					
					tableAtom.getModel().addTableModelListener(new TableModelListener() {
						@Override
						public void tableChanged(TableModelEvent e) {
							ExtraMethods.statusUpdater(model5,0,8,1);
						}
					});
					
					tableService.getModel().addTableModelListener(new TableModelListener() {
						@Override
						public void tableChanged(TableModelEvent e) {
							ExtraMethods.statusUpdater(model6, 0,3,0);
						}
					});

	}
		
	//END OF INVENTORY PANE----------------------------------------------------------------------------------------------------------------
		
	
	public static void query() {
				//QUERY  DATA FROM INVENTORY------------------------------------------			
						String col[] = null;
						int totalAll=0, totalEl=0, totalAcc=0, totalMods=0, totalAtom=0, totalService=0;
						Double sellP;
						Object[] rD = new Object[10];
						String cat;
				        
						model1.setRowCount(0);
						model2.setRowCount(0);
						model3.setRowCount(0);
						model4.setRowCount(0);
						model5.setRowCount(0);
						model6.setRowCount(0);
						
						for(int j=0;j<2;j++) {  //J IS USED TO SEPARATE SERVICES QUERY
							for(int i=0;i<InventoryDao.getInventory(j).size();i++) {  
							 	if(j==1) {
							 		rD[0] = InventoryDao.getInventory(j).get(i).getId();
								 	rD[1] = InventoryDao.getInventory(j).get(i).getName();
								 	rD[2] = InventoryDao.getInventory(j).get(i).getServCh();	
								 	rD[3] =  InventoryDao.getInventory(j).get(i).getFlag();
								 	if(!Boolean.parseBoolean(rD[3].toString()))
								 	model1.addRow(new Object[] {rD[0],rD[1],rD[2],"Services","N/A","N/A","N/A","N/A","N/A"});
									model6.addRow(rD);
									totalAll+=1;
									totalService+=1;								 	
								}
							 	else {
							 		if(!InventoryDao.getInventory(j).isEmpty()) {
							 			rD[0] = InventoryDao.getInventory(j).get(i).getId();
							 			rD[1] = InventoryDao.getInventory(j).get(i).getName();
							 			rD[2] = InventoryDao.getInventory(j).get(i).getBrand();
							 			rD[3] = InventoryDao.getInventory(j).get(i).getDesc();
							 			cat = InventoryDao.getInventory(j).get(i).getCateg().toString();
							 			rD[4] = InventoryDao.getInventory(j).get(i).getStock();
							 			rD[5] = InventoryDao.getInventory(j).get(i).getCost();
							 			rD[6] = InventoryDao.getInventory(j).get(i).getMarkup();
							 			rD[7] = InventoryDao.getInventory(j).get(i).getSell();
							 			rD[8] = InventoryDao.getInventory(j).get(i).getFlag();
									 	if(Boolean.parseBoolean(rD[8].toString())==false) {
							 			model1.addRow(new Object[] {rD[0],rD[1],rD[2],cat,rD[3],rD[4],rD[5],rD[6],rD[7]});
							 			totalAll+=1;
									 	}
							 			
							 			
							 			if(cat.equals("ELiquid")) {
							 				model2.addRow(rD);
							 				totalEl+=1;
							 			}
							 			else if(cat.equals("Accesories")) {
							 				model3.addRow(rD);
							 				totalAcc+=1;
							 			}
							 			else if(cat.equals("Mods")) {
							 				model4.addRow(rD);
							 				totalMods+=1;
							 			}
							 			else if(cat.equals("Atomizers")) {
							 				model5.addRow(rD);
							 				totalAtom+=1;
							 			}
							 	}
									
							 		
								}//END OF ELSE
								
							}//END OF FOR I
					 		
						}//END OF FOR J
						
						//SET THE TOTAL NUMBER OF ITEMS
						
						if(totalAll>1) {
							totInvent.setText(String.valueOf(totalAll) + " Items");
						}
						else
							totInvent.setText(String.valueOf(totalAll) + " Item");
						if(totalEl>1) {
							totEliquid.setText(String.valueOf(totalEl) + " Items");
						}
						else
							totEliquid.setText(String.valueOf(totalEl) + " Item");
							
						if(totalAcc>1) {
							totAcc.setText(String.valueOf(totalAcc) + " Items");						
						}
						else
							totAcc.setText(String.valueOf(totalAcc) + " Item");
						if(totalMods>1) {
							totMods.setText(String.valueOf(totalMods) + " Items");
						}
						else
							totMods.setText(String.valueOf(totalMods) + " Item");
						if(totalAtom>1) {
							totAtom.setText(String.valueOf(totalAtom) + " Items");
						}
						else
							totAtom.setText(String.valueOf(totalAtom) + " Item");
						if(totalService>1) {
							totServ.setText(String.valueOf(totalService) + " Items");
						}
						else
							totServ.setText(String.valueOf(totalService) +" Item");
									

	}	
	
	
	//=======================================================================================================================
	
	

}	
	

