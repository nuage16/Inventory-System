package Extras;

import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.*;

import DAO.InventoryDao;
import Home.OrderWin;
  
/**
 * @version 1.0 11/09/98
 */
public class ButtonRendererEditor implements TableCellRenderer, TableCellEditor {
  protected JButton button = new JButton("Remove");
  private String    label;
  private int row;
  
  public ButtonRendererEditor(JTable table) {
    button.setOpaque(true);
   
    button.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			DefaultTableModel model = (DefaultTableModel)table.getModel();
			model.removeRow(row);
		}
    	
    });
  }
  
  
  
  public Component getTableCellRendererComponent (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	  button.setForeground(Color.WHITE);
      button.setBackground(Color.RED);
	  return button;
  }
  
  
  public Component getTableCellEditorComponent(JTable table, Object value,
                   boolean isSelected, int row, int column) {
    this.row = row;
    return button;
  }
  
  public Object getCellEditorValue() {
	  return null;
  }
  
  public boolean isCellEditable(EventObject e) {
	  return true;
  }
  
  public boolean shouldSelectCell(EventObject e) {
	  return true;
  }
    
  public boolean stopCellEditing() {
	  return true;
  }

@Override
public void cancelCellEditing() {
	// TODO Auto-generated method stub
	
}

@Override
public void addCellEditorListener(CellEditorListener l) {
	// TODO Auto-generated method stub
	
}

@Override
public void removeCellEditorListener(CellEditorListener l) {
	// TODO Auto-generated method stub
	
}
    
 
}
