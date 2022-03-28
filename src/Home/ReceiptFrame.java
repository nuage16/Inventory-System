package Home;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import Beans.Sales;
import DAO.TransDao;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.util.List;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;

import javax.swing.JTextField;

public class ReceiptFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;
	private double totAmt = 0.00, subAmt=0.00, servCh=0.00;
	private JTextField txtSubTotal;
	private JTextField txtCharge;
	private JTextField txtDueAmt;
	private JTextField txtDiscount;
	private java.util.Date dateNow = new java.util.Date();
	private SimpleDateFormat hrFormat = new SimpleDateFormat("HH:mm");
	private SimpleDateFormat dayFormat = new SimpleDateFormat("MM/dd /yyyy");
	String strHr = hrFormat.format(dateNow);
	String strDay = dayFormat.format(dateNow);
	
	public ReceiptFrame(List<Sales> list, double discount) {
		
		setTitle("Sales Receipt");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 756, 482);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
		});
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 750, 77);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 79, 750, 77);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 154, 750, 161);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Amount Due");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3.setBounds(493, 11, 232, 17);
		panel_1.add(lblNewLabel_3);
		
		JLabel lblAmt = new JLabel();
		lblAmt.setHorizontalAlignment(SwingConstants.TRAILING);
		lblAmt.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblAmt.setBounds(495, 29, 232, 30);
		panel_1.add(lblAmt);
		
		JLabel lblNewLabel_4 = new JLabel(strHr);
		lblNewLabel_4.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(10, 24, 40, 17);
		panel_1.add(lblNewLabel_4);
		
		JLabel lblDay = new JLabel(strDay);
		lblDay.setVerticalAlignment(SwingConstants.BOTTOM);
		lblDay.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDay.setBounds(10, 42, 142, 17);
		panel_1.add(lblDay);
		contentPane.setLayout(null);
		panel.setLayout(null);
		contentPane.add(panel_1);
		
		JLabel lblNewLabel_1 = new JLabel("L");
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setBackground(Color.WHITE);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 70));
		lblNewLabel_1.setBounds(10, 10, 55, 55);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("HAMOG");
		lblNewLabel_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(75, 10, 260, 22);
		panel.add(lblNewLabel_2);
		
		JLabel lblCompanyAddress = new JLabel("+639985821554");
		lblCompanyAddress.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCompanyAddress.setBounds(75, 53, 297, 15);
		panel.add(lblCompanyAddress);
		
		JLabel lblReceipt = new JLabel("Receipt #" + String.valueOf(TransDao.transTracker(0)-1));
		lblReceipt.setHorizontalAlignment(SwingConstants.TRAILING);
		lblReceipt.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblReceipt.setBounds(534, 53, 194, 15);
		panel.add(lblReceipt);
		contentPane.add(panel);
		
		JLabel lblSalesReceipt = new JLabel("SALES RECEIPT");
		lblSalesReceipt.setVerticalAlignment(SwingConstants.BOTTOM);
		lblSalesReceipt.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSalesReceipt.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblSalesReceipt.setBounds(468, 10, 260, 41);
		panel.add(lblSalesReceipt);
		
		JLabel label_1 = new JLabel("Brgy. Sta. Cristina 1 Dasmari\u00F1as, Cavite");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_1.setBounds(75, 35, 297, 15);
		panel.add(label_1);
		contentPane.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		 model = new DefaultTableModel() {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
		model.setColumnIdentifiers(new Object[] {"Qty","Item Code","Description","Unit Price","Amount"});    
		
		table = new JTable(model)
		{
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
			{
				Component c = super.prepareRenderer(renderer, row, column);

				//  Alternate row color

				if (!isRowSelected(row))
					c.setBackground(row % 2 == 0 ? getBackground() : Color.LIGHT_GRAY);

				return c;
			}
		};

		table.setBackground(Color.WHITE);
		JTableHeader tableHeader = table.getTableHeader();
		table.setTableHeader(tableHeader);

		table.getTableHeader().setBackground(Color.black);
		table.getTableHeader().setForeground(Color.white);
		
		table.getColumnModel().getColumn(0).setPreferredWidth(70);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(370);
		table.getColumnModel().getColumn(3).setPreferredWidth(99);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		

		JScrollPane scrollPane = new JScrollPane(table);
		panel_2.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(0, 316, 750, 137);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblSubTotal = new JLabel("Total Amount of Items");
		lblSubTotal.setVerticalAlignment(SwingConstants.BOTTOM);
		lblSubTotal.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSubTotal.setBounds(473, 11, 151, 17);
		panel_3.add(lblSubTotal);
		
		JLabel lblService = new JLabel("Total Service Charge");
		lblService.setVerticalAlignment(SwingConstants.BOTTOM);
		lblService.setHorizontalAlignment(SwingConstants.TRAILING);
		lblService.setBounds(502, 38, 122, 17);
		panel_3.add(lblService);
		
		JLabel lblAmountDue = new JLabel("Amount Due");
		lblAmountDue.setVerticalAlignment(SwingConstants.BOTTOM);
		lblAmountDue.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAmountDue.setHorizontalAlignment(SwingConstants.TRAILING);
		lblAmountDue.setBounds(502, 102, 122, 17);
		panel_3.add(lblAmountDue);
		
		txtSubTotal = new JTextField();
		txtSubTotal.setHorizontalAlignment(SwingConstants.TRAILING);
		txtSubTotal.setBounds(634, 9, 91, 20);
		txtSubTotal.setEditable(false);
		panel_3.add(txtSubTotal);
		txtSubTotal.setColumns(10);
		
		txtCharge = new JTextField();
		txtCharge.setHorizontalAlignment(SwingConstants.TRAILING);
		txtCharge.setColumns(10);
		txtCharge.setBounds(634, 36, 91, 20);
		txtCharge.setEditable(false);
		panel_3.add(txtCharge);
		
		txtDueAmt = new JTextField();
		txtDueAmt.setHorizontalAlignment(SwingConstants.TRAILING);
		txtDueAmt.setColumns(10);
		txtDueAmt.setBounds(634, 100, 91, 20);
		txtDueAmt.setEditable(false);
		panel_3.add(txtDueAmt);
		
		
		
		JLabel label = new JLabel("Discount");
		label.setForeground(Color.BLUE);
		label.setVerticalAlignment(SwingConstants.BOTTOM);
		label.setHorizontalAlignment(SwingConstants.TRAILING);
		label.setBounds(502, 64, 122, 17);
		panel_3.add(label);
		
		txtDiscount = new JTextField();
		txtDiscount.setForeground(Color.BLUE);
		txtDiscount.setHorizontalAlignment(SwingConstants.TRAILING);
		txtDiscount.setText("-" +String.valueOf(discount));
		txtDiscount.setEditable(false);
		txtDiscount.setColumns(10);
		txtDiscount.setBounds(634, 62, 91, 20);
		panel_3.add(txtDiscount);

		//INSERTS DATA TO TABLE
		for(int i=0;i<list.size();i++) {
			
			if(list.get(i).getId().equals("111") || list.get(i).getId().equals("222") || list.get(i).getId().equals("333")) {
				model.addRow(new Object[] {1,
						list.get(i).getId(),
						list.get(i).getName() + "  (Service)",
						list.get(i).getServCh(),
						list.get(i).getServCh()
					});
				servCh += list.get(i).getServCh();
			}
			else {
				model.addRow(new Object[] {list.get(i).getSold(),
				list.get(i).getId(),
				list.get(i).getName(),
				list.get(i).getSell(),
				list.get(i).getTSales(),
			});
			
			subAmt += list.get(i).getSell() * list.get(i).getSold();
			}
			
		}
		
		totAmt = subAmt + servCh - discount;
		txtSubTotal.setText(String.valueOf(subAmt));
		txtCharge.setText(String.valueOf(servCh));
		lblAmt.setText("Php " + totAmt);
		txtDueAmt.setText(String.valueOf(totAmt));
		
		JLabel lblNewLabel = new JLabel("Thank you!");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(34, 41, 198, 40);
		panel_3.add(lblNewLabel);
		
	}
}
