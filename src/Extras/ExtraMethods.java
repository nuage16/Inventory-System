package Extras;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import DAO.InventoryDao;
import DAO.SalesDao;
import DAO.TransDao;
import Beans.Inventory;
import Beans.Sales;
import Beans.Transactions;

public class ExtraMethods {

public static void txtPlaceHolder(JTextField txtField, String value) {
		
		txtField.setText(value);
		txtField.setForeground(Color.GRAY);	
		
		txtField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if(txtField.getText().equals(value)) {
					txtField.setText("");
					txtField.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if(txtField.getText().isEmpty()) {
					txtField.setForeground(Color.GRAY);
					txtField.setText(value);
				}
			}
		});
		
	}


//OVERLOADING txtPlaceHolder to accept JTextArea as one of the arguments listed
public static void txtPlaceHolder(JTextArea txtField, String value) {
	
	txtField.setText(value);
	txtField.setForeground(Color.GRAY);	
	
	txtField.addFocusListener(new FocusListener() {
		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			if(txtField.getText().equals(value)) {
				txtField.setText("");
				txtField.setForeground(Color.BLACK);
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			if(txtField.getText().isEmpty()) {
				txtField.setForeground(Color.GRAY);
				txtField.setText(value);
			}
		}
	});
	
}


public static void searchFilter(TableRowSorter<TableModel> rowSorter, JTextField txtField, String value) {	
	
	txtField.getDocument().addDocumentListener(new DocumentListener() {

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			String text = txtField.getText();
			
			if(text.trim().length()==0 || text.equals(value)) {
				rowSorter.setRowFilter(null);
			}
			else {
				rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
			}
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			String text = txtField.getText();
			
			if(text.trim().length()==0 || text.equals(value)) {
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


public static void insertRow(Object[] newRow, JTable table) {
	DefaultTableModel model = (DefaultTableModel)table.getModel();
	model.addRow(newRow);
}		

public static void removeRow(Object[] newRow, JTable table) {
	DefaultTableModel model = (DefaultTableModel)table.getModel();
	//model.removeRow(row);
}		

public static void refreshOrder(TableModel model, JLabel amt, JLabel item, JRadioButton rdbtn, JTextField txtfield) {
	((DefaultTableModel) model).setRowCount(0);
	amt.setText("0.00");
	item.setText("0");
	rdbtn.setSelected(false);
	txtfield.setText("");
}

public static void rowSorter(JTable table) {
	TableRowSorter<TableModel> rowSorter;
	rowSorter = new TableRowSorter<>(table.getModel());
	table.setRowSorter(rowSorter);
	
	
}

public static void statusUpdater(DefaultTableModel model, int colId, int colFlag, int j) {
	if(model.getRowCount()>0) {								
		for(int row=0;row<model.getRowCount();row++) {
			boolean[] nFlag = new boolean[model.getRowCount()];
			String[] nId = new String[model.getRowCount()];	
			nFlag[row] = (boolean)model.getValueAt(row, colFlag);
			nId[row] = (String) model.getValueAt(row,colId);
				Inventory u = new Inventory();
				u.setId(model.getValueAt(row,colId).toString());
				u.setFlag(Boolean.parseBoolean(model.getValueAt(row, colFlag).toString()));
				InventoryDao.updateStatus(u, j);
			}
		}
	
	}

public static void statusSalesUpdater(DefaultTableModel model, int colTrans, int colId, int colFlag, int j) {
	if(model.getRowCount()>0) {								
		for(int row=0;row<model.getRowCount();row++) {
			boolean[] nFlag = new boolean[model.getRowCount()];
			String[] nId = new String[model.getRowCount()];
			int[] nTrans = new int[model.getRowCount()];
			
			nFlag[row] = (boolean)model.getValueAt(row, colFlag);
			nId[row] = (String) model.getValueAt(row,colId);
			nTrans[row] = (int) model.getValueAt(row, colTrans);
				Sales u = new Sales();
				u.setId(model.getValueAt(row,colId).toString());
				u.setFlag(Boolean.parseBoolean(model.getValueAt(row, colFlag).toString()));
				u.setTrans(Integer.parseInt(model.getValueAt(row, colTrans).toString()));
				SalesDao.updateStatus(u, j);
			}
		}
}

public static void statusTransUpdater(DefaultTableModel model, int colId, int colFlag, int j) {
	if(model.getRowCount()>0) {								
		for(int row=0;row<model.getRowCount();row++) {
			boolean[] nFlag = new boolean[model.getRowCount()];
			int[] nId = new int[model.getRowCount()];
			
			nFlag[row] = (boolean)model.getValueAt(row, colFlag);
			nId[row] = (int) model.getValueAt(row,colId);
				Transactions u = new Transactions();
				u.setID(Integer.parseInt(model.getValueAt(row,colId).toString()));
				u.setFlag(Boolean.parseBoolean(model.getValueAt(row, colFlag).toString()));
				TransDao.updateStatus(u, j);
			}
		}
}
}