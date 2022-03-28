package Extras;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
  
/**
 * @version 1.0 11/09/98
 */
public class ButtonRenderer extends JButton implements TableCellRenderer {
  
  public ButtonRenderer() {
    setOpaque(true);
  }
   
  public Component getTableCellRendererComponent(JTable table, Object value,
                   boolean isSelected, boolean hasFocus, int row, int column) {
	 
	  if (isSelected) {	 
        setForeground(Color.BLACK);
        setBackground(Color.RED);
    } else{
    	setForeground(Color.WHITE);
        setBackground(Color.RED);
    }
    setText( (value ==null) ? "" : value.toString() );
    return this;
  }
}