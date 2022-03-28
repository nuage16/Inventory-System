package Home;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import DAO.InventoryDao;
import DAO.SupplierDao;
import Extras.BtnColor;
import Extras.ExtraMethods;

public class SupplierWin extends JPanel {
	
	JPanel supplierPane;
	private JTextField txtSearch;
	private static DefaultTableModel model1;
	private static JTextField totSupp;
	private TableRowSorter<TableModel> rowSorter;
	
	public SupplierWin() {
		//INVENTORY HEADER TABS
		supplierPane = new JPanel();
		supplierPane.setBorder(new CompoundBorder(new MatteBorder(10, 10, 10, 10, (Color) new Color(128, 128, 128)),
				new EtchedBorder(EtchedBorder.LOWERED, new Color(192, 192, 192), new Color(0, 0, 0))));
		supplierPane.setBackground(new Color(204, 153, 51));
		supplierPane.setBounds(255, 0, 1019, 691);
		supplierPane.setLayout(null);
		supplierPane.setVisible(true);

		txtSearch = new JTextField();
		txtSearch.setBounds(505, 90, 360, 34);
		supplierPane.add(txtSearch);
		ExtraMethods.txtPlaceHolder(txtSearch, "Type to Search");
		ExtraMethods.searchFilter(rowSorter, txtSearch, "Type to Search");

		JButton btnNewButton_4 = new JButton("+ Add");
		btnNewButton_4.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), Color.DARK_GRAY));
		btnNewButton_4.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton_4.setForeground(Color.WHITE);
		btnNewButton_4.setBackground(new Color(102, 51, 0));
		btnNewButton_4.addMouseListener(new BtnColor(btnNewButton_4));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddSupplierFrame();
			}
		});
		btnNewButton_4.setBounds(35, 89, 76, 34);
		supplierPane.add(btnNewButton_4);
		
		JButton btnModify = new JButton("Modify");
		btnModify.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), Color.DARK_GRAY));
		btnModify.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnModify.setForeground(Color.WHITE);
		btnModify.setBackground(new Color(102, 51, 0));
		btnModify.setBounds(121, 89, 76, 34);
		supplierPane.add(btnModify);
		btnModify.addMouseListener(new BtnColor(btnModify));
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EditSupplierFrame();
			}
		});
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.WHITE, new Color(204, 204, 204),
				new Color(102, 102, 102), Color.DARK_GRAY));
		btnRefresh.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnRefresh.setForeground(Color.WHITE);
		btnRefresh.setBackground(new Color(222, 184, 135));
		btnRefresh.setBounds(895, 89, 90, 34);
		supplierPane.add(btnRefresh);
		btnRefresh.addMouseListener(new BtnColor(btnRefresh));
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model1.setRowCount(0);
				txtSearch.setText("");
				ExtraMethods.txtPlaceHolder(txtSearch, "Type to Search");
				query();
			}
		});
		
		JPanel tabHeader = new JPanel();
		tabHeader.setBackground(new Color(153, 102, 0));
		tabHeader.setBounds(12, 12, 995, 60);
		supplierPane.add(tabHeader);
		tabHeader.setLayout(new BorderLayout(0, 0));

		JLabel txtTrans = new JLabel();
		txtTrans.setLabelFor(tabHeader);
		// txttranssTrans.setForeground(Color.WHITE);
		txtTrans.setForeground(Color.WHITE);
		txtTrans.setFont(new Font("Tahoma", Font.BOLD, 25));
		txtTrans.setHorizontalAlignment(SwingConstants.CENTER);
		txtTrans.setText("SUPPLIER INFORMATION");
		tabHeader.add(txtTrans);

		JPanel tabHolder = new JPanel();
		tabHolder.setBackground(new Color(255, 255, 255, 255));
		tabHolder.setBounds(35, 143, 950, 520);
		supplierPane.add(tabHolder);
		tabHolder.setLayout(new CardLayout(0, 0));
		
		CardLayout cardLayout2 = (CardLayout)(tabHolder.getLayout()); 
		
		JPanel tab1Content = new JPanel();
		tabHolder.add(tab1Content, "tab1");

		tab1Content.setBackground(new Color(153, 102, 51));
		tab1Content.setLayout(null);
		
		 model1 = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable tableAItems=new JTable(model1);
		tableAItems.setBackground(new Color(176, 196, 222));
		tableAItems.setBounds(186, 95, 333, 200);
		
		JScrollPane scpnProduct = new JScrollPane(tableAItems);
		scpnProduct.setBounds(0, 38, 950, 482);
		tab1Content.add(scpnProduct);
		
		totSupp = new JTextField();
		totSupp.setEditable(false);
		totSupp.setBounds(10, 5, 200, 30);
		totSupp.setFont(new Font("Tahoma", Font.BOLD, 13));
		totSupp.setHorizontalAlignment(SwingConstants.LEFT);
		tab1Content.add(totSupp);
		totSupp.setColumns(10);

		rowSorter = new TableRowSorter<>(tableAItems.getModel());
		tableAItems.setRowSorter(rowSorter);
		searchFilter(rowSorter);
		
		query();
	}

	
	public void searchFilter(TableRowSorter<TableModel> rowSorter) {	
		
		txtSearch.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				String text = txtSearch.getText();
				
				if(text.trim().length()==0) {
					rowSorter.setRowFilter(null);
				}
				else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				String text = txtSearch.getText();
				
				if(text.trim().length()==0) {
					rowSorter.setRowFilter(null);
				}
				else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				throw new UnsupportedOperationException("Not supported yet.");
			}
			
		});			
		
		
	}
//===========================================================================================================
	
	public void searchPlaceHolder() {
		
		txtSearch.setText("Type to Search");
		txtSearch.setForeground(Color.BLACK);	
		txtSearch.setBackground(new Color(255,255,255));
		
		txtSearch.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if(txtSearch.getText().equals("Type to Search")) {
					txtSearch.setText("");
					txtSearch.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if(txtSearch.getText().isEmpty()) {
					txtSearch.setForeground(Color.GRAY);
					txtSearch.setText("Type To Search");
				}
			}
		});
		
		txtSearch.setText(null);
	}


	public static void query() {
		//QUERY  DATA FROM INVENTORY------------------------------------------			
				String col[] = null;
				int totalAll=0;
				Object colName[] = new Object[] {"Supplier Code", "Name", "Address", "Contact No.","Other Information"};
				Object[] rD = new Object[5];
				
		        model1.setColumnIdentifiers(colName);
		        model1.setRowCount(0);
		        
					for(int i=0;i<SupplierDao.getRecords().size();i++) {
					 	
					 		rD[0] = SupplierDao.getRecords().get(i).getCode();
						 	rD[1] = SupplierDao.getRecords().get(i).getName();
						 	rD[2] = SupplierDao.getRecords().get(i).getAddress();	
						 	rD[3] = SupplierDao.getRecords().get(i).getContact();	
						 	rD[4] = SupplierDao.getRecords().get(i).getInfo();	

						 	model1.addRow(new Object[] {rD[0],rD[1],rD[2],rD[3],rD[4]});
							totalAll+=1;								 	
					 		
					}//END OF FOR I

				
				//SET THE TOTAL NUMBER OF ITEMS
				totSupp.setText(String.valueOf(totalAll) + " Suppliers");								

}	


}