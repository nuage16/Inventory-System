package Home;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.ChangeEvent;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.SoftBevelBorder;

import DAO.InventoryDao;
import DAO.SalesDao;
import DAO.TransDao;
import Beans.Inventory;
import Beans.Sales;
import Beans.Transactions;
import Extras.BtnColor;
import Extras.ButtonRenderer;
import Extras.ButtonRendererEditor;
import Extras.ExtraMethods;

		
public class OrderWin extends JPanel{
	
	JPanel orderPane;

	protected double totalService = 0.00;
	protected double[] serviceFee = {20.00, 10.00, 15.00};  //BUILD, REWRAP, CHARGE
	protected double totalDiscount = 0.00;
	protected double ServTotal=0.00;
	protected double deduc, disc=0.00;
	protected JTextField totInvent;
	protected JTextField totEliquid;
	protected JTextField totAcc;
	protected JTextField totMods;
	protected JTextField totAtom;
	protected JTextField totServ;

	private JTextField txtCodeInput;
	private JTable tableCart;
	private DefaultTableModel modelCart;
	private JTextField txtDiscount;
	private JTextField txtTransactionCode;
	
	private JCheckBox chckbxBuild;
	private JCheckBox chckbxCharge;
	private JCheckBox chckbxReWrap;
	private JLabel lblTotItems;
	private JLabel lblTotAmt;
	
	
	private int totItems = 0;
	private Double subTotal = 0.00, discTotal=0.00;
	private Double totAmt=0.00;
	private java.util.Date dateNow = new java.util.Date();
	private SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private JRadioButton rdbtnDiscount;
	Sales u = new Sales();
	Transactions t = new Transactions();
	
	int addVal=0;
	Double addAmt=0.00;
	int prevCount;
	
	public OrderWin() {
		orderPane = new JPanel();
		orderPane.setBorder(new CompoundBorder(new MatteBorder(10, 10, 10, 10, (Color) new Color(128, 128, 128)),
				new EtchedBorder(EtchedBorder.LOWERED, new Color(192, 192, 192), new Color(0, 0, 0))));
		orderPane.setBackground(new Color(153, 102, 204));
		orderPane.setBounds(255, 0, 1019, 691);
		orderPane.setLayout(null);
		orderPane.setVisible(true);
	
		txtTransactionCode = new JTextField();
		txtTransactionCode.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(148, 0, 211),
				new Color(204, 153, 255), new Color(0, 0, 0), new Color(153, 153, 153)));
		txtTransactionCode.setBounds(609, 30, 375, 63);
		orderPane.add(txtTransactionCode);
		
		txtTransactionCode.setHorizontalAlignment(SwingConstants.TRAILING);
		txtTransactionCode.setFont(new Font("Tahoma", Font.PLAIN, 32));
		txtTransactionCode.setColumns(10);
		txtTransactionCode.setText(String.valueOf(TransDao.transTracker(0)));

	
		String[] chService;
		chService = new String[] {"None","Build", "Re-Wrap","Charge"};
		
		JPanel cartPanel= new JPanel();
		cartPanel.setBackground(Color.BLUE);
		cartPanel.setBounds(25, 104, 551, 544);
		orderPane.add(cartPanel);
		cartPanel.setLayout(new BorderLayout(0, 0));
					
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(new Color(102, 51, 102));
		panel_6.setForeground(Color.WHITE);
		cartPanel.add(panel_6, BorderLayout.NORTH);
		
		JLabel lblCart = new JLabel("CART");
		lblCart.setFont(new Font("Tahoma", Font.BOLD, 14));			
		lblCart.setForeground(Color.WHITE);
		panel_6.add(lblCart);
					
		modelCart = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column == 0 || column == 5)
						return true;
				else
						return false;
				}
			};
			modelCart.setColumnIdentifiers(new Object[] {"Qty","Product Code","Description","Unit Price","Amount", ""});    
			tableCart=new JTable(modelCart);
			tableCart.setBackground(new Color(224, 255, 255));
			tableCart.setBounds(198, 100, 467, 263);
				
			JScrollPane scpnCart = new JScrollPane(tableCart);
			cartPanel.add(scpnCart, BorderLayout.CENTER);
									
			JPanel transactDtlsPane = new JPanel();
			transactDtlsPane.setFont(new Font("Tahoma", Font.BOLD, 12));
			transactDtlsPane.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255), 1, true), "ITEM",
					TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
			transactDtlsPane.setOpaque(false);
			transactDtlsPane.setBackground(Color.WHITE);
			transactDtlsPane.setBounds(609, 307, 375, 112);
			orderPane.add(transactDtlsPane);
			transactDtlsPane.setLayout(null);
			
			txtCodeInput = new JTextField();
			txtCodeInput.setMargin(new Insets(2, 10, 2, 2));
			txtCodeInput.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(148, 0, 211), new Color(204, 153, 255),
					new Color(0, 0, 0), new Color(153, 153, 153)));
			txtCodeInput.setBounds(12, 20, 301, 30);
			transactDtlsPane.add(txtCodeInput);
			ExtraMethods.txtPlaceHolder(txtCodeInput, " Item Code");
			txtCodeInput.setColumns(10);
			
			SpinnerModel spnrModel = new SpinnerNumberModel(1,0,99,1);
					
			JSpinner spnrQty = new JSpinner(spnrModel);
			spnrQty.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(148, 0, 211), new Color(204, 153, 255),
					new Color(0, 0, 0), new Color(153, 153, 153)));
			spnrQty.setFont(new Font("Tahoma", Font.PLAIN, 13));
			spnrQty.setBounds(318, 20, 45, 30);
			transactDtlsPane.add(spnrQty);
			spnrQty.setValue(1);

			//TO TURN LAST COLUMN INTO A BUTTON
			ButtonRendererEditor btnEd = new ButtonRendererEditor(tableCart);
			tableCart.getColumn("").setCellRenderer(btnEd);
			tableCart.getColumn("").setCellEditor(btnEd);
									
			tableCart.getModel().addTableModelListener(new TableModelListener() {
				@Override
				public void tableChanged(TableModelEvent e) {			
					System.out.println("TABLE CHANGED");
					System.out.println("modelCount" + modelCart.getRowCount());
							
					//IF A ROW WAS DELETED
					if(prevCount>modelCart.getRowCount()) {
						totItems = 0;
						subTotal=0.00;
						if(modelCart.getRowCount()==0) {
							lblTotItems.setText(String.valueOf(0));
							lblTotAmt.setText(String.valueOf(0.00));
						}
						else {
						//RECALCULATE THE CONTENTS OF THE TABLE
							for(int i=0;i<modelCart.getRowCount();i++) {
								totItems += (int) modelCart.getValueAt(i, 0);
								subTotal += (double) modelCart.getValueAt(i,4);
								lblTotItems.setText(String.valueOf(totItems));
								lblTotAmt.setText(String.valueOf(subTotal));	
							} //end of for loop
						}//end of else statement
					}//end of if statement
				}//end of tableChanged
			});
					
			JButton btnAddToCart = new JButton("+ ADD TO CART");
			btnAddToCart.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(192, 192, 192), new Color(64, 64, 64)));
			btnAddToCart.setForeground(Color.WHITE);
			btnAddToCart.setFont(new Font("Tahoma", Font.BOLD, 15));
			btnAddToCart.setBackground(new Color(102, 0, 153));
			btnAddToCart.addMouseListener(new BtnColor(btnAddToCart));
			btnAddToCart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {						
					String content = " Item Code";
					boolean x = false;
					Inventory obj = null;
					Double totAmount = 0.00;
							
					//CHECKS IF USER ENTERED A CODE ON TEXTFIELD BEFORE PRESSING BUTTON
					if(!(txtCodeInput.getText().equals(content) || txtCodeInput.getText().equals(""))) {	
						obj = InventoryDao.searchRecord(txtCodeInput.getText().toString());	
						//IF STATEMENT TO CHECK IF PRODUCT EXISTS OR IF PRODUCT IS NO LONGER SOLD  IS OUT OF STOCK
						if(obj!=null) {
							if(!obj.getFlag()) {
								if(obj.getStock()>0){
									Double costPrice = InventoryDao.searchRecord(txtCodeInput.getText()).getSell();
									int spnrValue =(int) spnrQty.getValue(), addVal=0, curVal;
									Double addAmt=0.00;
								
									//TO CHECK IF PRODUCT IS ALREADY IN THE TABLE
									for(int i=0;i<modelCart.getRowCount();i++) {
									//IF PRODUCT IS FOUND, VALUE FROM spnrQty IS ADDED TO CURRENT QUANTITY
										//if(obj.getId() == modelCart.getValueAt(i, 1).toString()) {
										if(obj.getId().equals(modelCart.getValueAt(i, 1).toString())) {
											curVal = Integer.parseInt(modelCart.getValueAt(i, 0).toString());
											addVal = Integer.parseInt(spnrQty.getValue().toString());
											spnrValue = curVal+ addVal;
											if(spnrValue>obj.getStock()) {
												JOptionPane.showMessageDialog(orderPane, "QTY MUST NOT EXCEED " + obj.getStock());
												spnrValue = curVal - addVal;
											}
											else {
												addAmt = addVal * costPrice;
												modelCart.setValueAt(spnrValue, i, 0);
												totAmount = costPrice * spnrValue;
												modelCart.setValueAt(totAmount, i, 4);
														
												subTotal += addAmt;
												totItems+=addVal;
												lblTotItems.setText(String.valueOf(totItems));
												lblTotAmt.setText(String.valueOf(subTotal));
												prevCount = modelCart.getRowCount();
											}//end of else statement
											x=true;
											break;
													
										}// end of if(obj.getID)
									}//end of for loop
											
									//IF PRODUCT IS NOT PRESENT INSIDE CART
									if(!x) {
										addVal = (int) spnrQty.getValue();
										addAmt = costPrice * spnrValue;
										
										//IF QTY > STOCK
										if(addVal>obj.getStock()) {
											JOptionPane.showMessageDialog(orderPane, "QTY MUST NOT EXCEED " + obj.getStock());
										}
										else {
											ExtraMethods.insertRow(new Object[] {
													spnrQty.getValue(),
													InventoryDao.searchRecord(txtCodeInput.getText()).getId(),
													InventoryDao.searchRecord(txtCodeInput.getText()).getName(),
													InventoryDao.searchRecord(txtCodeInput.getText()).getSell(),
													addAmt,"Remove"
											}, tableCart);
										}
										subTotal += addAmt;
										totItems+=addVal;
										lblTotItems.setText(String.valueOf(totItems));
										lblTotAmt.setText(String.valueOf(subTotal));
										prevCount = modelCart.getRowCount();
									}//end of if(!x)
									}//if(obj.getStock()>0)
									else {
										JOptionPane.showMessageDialog(orderPane, "ITEM IS OUT OF STOCK");
									}	
								}//IF(!obj.getFlag()
								else {
									JOptionPane.showMessageDialog(orderPane, "ITEM IS UNAVAILABLE");	
								}								
							}//IF(obj!=null)
							else {
								JOptionPane.showMessageDialog(orderPane, "ITEM DOES NOT EXIST");
							}
							ExtraMethods.txtPlaceHolder(txtCodeInput, " Item Code");
							spnrQty.setValue(1);
						}//END OF IF txtCodeInput(null)
						else {
							JOptionPane.showMessageDialog(orderPane, "No Input");
						}
					}	
				});
				btnAddToCart.setBounds(12, 58, 353, 40);
				transactDtlsPane.add(btnAddToCart);
					
				JPanel servicePane = new JPanel();
				servicePane.setFont(new Font("Tahoma", Font.BOLD, 12));
				servicePane.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255), 1, true), "SERVICE",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
				servicePane.setBackground(new Color(153, 102, 204));
				servicePane.setBounds(609, 433, 375, 71);
				orderPane.add(servicePane);
				servicePane.setLayout(new GridLayout(0, 3, 10, 0));
				
				chckbxBuild = new JCheckBox("BUILD (20)");
				chckbxBuild.setOpaque(false);
				chckbxBuild.setForeground(Color.WHITE);
				chckbxBuild.setHorizontalAlignment(SwingConstants.CENTER);
				chckbxBuild.setBackground(new Color(102, 51, 153));
				servicePane.add(chckbxBuild);
				chckbxBuild.setFont(new Font("Tahoma", Font.BOLD, 12));
				
				chckbxReWrap = new JCheckBox("RE-WRAP (10)");
				chckbxReWrap.setOpaque(false);
				chckbxReWrap.setHorizontalAlignment(SwingConstants.CENTER);
				chckbxReWrap.setForeground(Color.WHITE);
				chckbxReWrap.setBackground(new Color(102, 51, 153));
				servicePane.add(chckbxReWrap);
				chckbxReWrap.setFont(new Font("Tahoma", Font.BOLD, 12));

				chckbxCharge = new JCheckBox("CHARGE (15)");
				chckbxCharge.setOpaque(false);
				chckbxCharge.setHorizontalAlignment(SwingConstants.CENTER);
				chckbxCharge.setForeground(Color.WHITE);
				chckbxCharge.setBackground(new Color(102, 51, 153));
				servicePane.add(chckbxCharge);
				chckbxCharge.setFont(new Font("Tahoma", Font.BOLD, 12));
				
				chckbxCharge.addItemListener((ItemListener) new ItemListener() { 
					public void itemStateChanged(ItemEvent e) {
						if(e.getStateChange() == ItemEvent.SELECTED) {
							ServTotal += serviceFee[2];
						}
						//IF CHECKBOX IS UNCHECKED
						else {
							ServTotal -= serviceFee[2];
						}
							
					}
				});
				chckbxReWrap.addItemListener(new ItemListener() { // 10
					public void itemStateChanged(ItemEvent e) {
						if(e.getStateChange() == ItemEvent.SELECTED) {
							ServTotal +=  serviceFee[1];								
						}
						//IF CHECKBOX IS UNCHECKED
						else {
							ServTotal -= serviceFee[1];
						}
							
					}
				});
				chckbxBuild.addItemListener(new ItemListener() { // 20
					public void itemStateChanged(ItemEvent e) {
						if(e.getStateChange() == ItemEvent.SELECTED) {
							ServTotal += serviceFee[0];
						}
						//IF CHECKBOX IS UNCHECKED
						else {
							ServTotal -= serviceFee[0];			
						}								
					}
				});
					
				JPanel panel = new JPanel();
				panel.setOpaque(false);
				panel.setBackground(Color.WHITE);
				panel.setBounds(25, 30, 551, 63);
				orderPane.add(panel);
				panel.setLayout(null);
				
				JLabel lblNewLabel = new JLabel("Total Amount");
				lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
				lblNewLabel.setForeground(Color.WHITE);
				lblNewLabel.setBounds(460, 40, 83, 20);
				panel.add(lblNewLabel);

				lblTotAmt = new JLabel("0.00");
				lblTotAmt.setForeground(Color.WHITE);
				lblTotAmt.setVerticalAlignment(SwingConstants.BOTTOM);
				lblTotAmt.setHorizontalAlignment(SwingConstants.TRAILING);
				lblTotAmt.setFont(new Font("Tahoma", Font.BOLD, 32));
				lblTotAmt.setBounds(352, 0, 181, 38);
				panel.add(lblTotAmt);
	
				JLabel lblTotalItems = new JLabel("Total Items");
				lblTotalItems.setFont(new Font("Tahoma", Font.BOLD, 11));
				lblTotalItems.setForeground(Color.WHITE);
				lblTotalItems.setBounds(13, 39, 70, 20);
				panel.add(lblTotalItems);

				lblTotItems = new JLabel("0");
				lblTotItems.setForeground(Color.WHITE);
				lblTotItems.setVerticalAlignment(SwingConstants.BOTTOM);
				lblTotItems.setBounds(13, 1, 173, 36);
				panel.add(lblTotItems);
				lblTotItems.setFont(new Font("Tahoma", Font.BOLD, 31));

				JPanel logoPanel = new JPanel();
				logoPanel.setBounds(609, 104, 375, 186);
				orderPane.add(logoPanel);
				logoPanel.setLayout(new BorderLayout(0, 0));

				JPanel picPanel = new JPanel();
				logoPanel.add(picPanel);
				
				//FOR INSERTING PICTURE
				JLabel picLabel;

				Image image = new ImageIcon(OrderWin.class.getResource("/Icons/SHOP_B.jpg")).getImage()
						.getScaledInstance(375, 188, Image.SCALE_DEFAULT);
				picLabel = new JLabel(new ImageIcon(image));
				picLabel.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(192, 192, 192), new Color(64, 64, 64)));
				picPanel.setLayout(new BorderLayout(0, 0));
				picPanel.add(picLabel);

				JPanel panel_1 = new JPanel();
				panel_1.setBounds(609, 104, 375, 186);
				orderPane.add(panel_1);
					
				JPanel panel_2 = new JPanel();
				panel_2.setBackground(new Color(204, 204, 204));
				panel_2.setBounds(611, 515, 372, 48);
				orderPane.add(panel_2);
				panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
					
				rdbtnDiscount = new JRadioButton("DISCOUNT");
				rdbtnDiscount.setFont(new Font("Tahoma", Font.BOLD, 11));
				rdbtnDiscount.setMargin(new Insets(2, 10, 2, 10));
				rdbtnDiscount.setAlignmentY(Component.TOP_ALIGNMENT);
				rdbtnDiscount.setForeground(Color.GRAY);
				rdbtnDiscount.setBackground(new Color(204, 204, 204));
				rdbtnDiscount.setSelected(false);
				panel_2.add(rdbtnDiscount);

				txtDiscount = new JTextField();
				txtDiscount.setMargin(new Insets(2, 10, 2, 2));
				txtDiscount.setBackground(new Color(204, 204, 204));
				txtDiscount.setForeground(Color.GRAY);
				panel_2.add(txtDiscount);
				txtDiscount.setColumns(10);
				ExtraMethods.txtPlaceHolder(txtDiscount, "0.00");
				txtDiscount.setEditable(false);	
					
				rdbtnDiscount.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent e) {
						if (rdbtnDiscount.isSelected()) {
							txtDiscount.setEditable(true);
							panel_2.setBackground(new Color(102, 51, 102));
							rdbtnDiscount.setBackground(new Color(102, 51, 102));
							rdbtnDiscount.setForeground(Color.WHITE);
							txtDiscount.setBackground(Color.WHITE);
							txtDiscount.setForeground(Color.BLACK);
							txtDiscount.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(148, 0, 211),
									new Color(204, 153, 255), new Color(0, 0, 0), new Color(153, 153, 153)));

						} else {
							txtDiscount.setEditable(false);
							panel_2.setBackground(Color.LIGHT_GRAY);
							txtDiscount.setBackground(Color.LIGHT_GRAY);
							txtDiscount.setForeground(Color.GRAY);
							txtDiscount.setBorder(null);
							rdbtnDiscount.setBackground(Color.LIGHT_GRAY);
							rdbtnDiscount.setForeground(Color.GRAY);
						}
					}
				});
								
				//FROM HERE
					
				JButton btnCheckout = new JButton("Checkout");
				btnCheckout.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
							
						int dialogButton = JOptionPane.YES_NO_OPTION;
						List<Sales> objList = new ArrayList<Sales>();
						totAmt = 0.00;
						double totServ=0;
							
						//GENERATES NEW TRANSACTION CODE			
						u.setTrans(TransDao.transTracker(0));	
						//SETS THE DATE
						u.setDSold(Date.valueOf(dayFormat.format(dateNow)));

						if(modelCart.getRowCount()!=0) {
							discTotal = subTotal;
							
						//TO CHECK IF rdbtnDiscount is enabled
						//If enabled = Total amount to be paid will be deducted by the amount entered by the user
						if(rdbtnDiscount.isSelected()) {
							disc = Double.parseDouble(txtDiscount.getText());
							discTotal -= disc;
							deduc = disc/(modelCart.getRowCount());
						}
						else {
							discTotal = subTotal;
							deduc = 0;
						}
							
							
						totAmt = discTotal + ServTotal;
						int dialogResult = JOptionPane.showConfirmDialog(orderPane, "Total Amount :\nPHP" + totAmt, "Confirmation", dialogButton);
						if(dialogResult == JOptionPane.YES_OPTION) {

						//FOR HANDLING SERVICE TRANSACTIONS
						if(chckbxCharge.isSelected()) {
							Sales svC = new Sales();
							svC.setTrans(TransDao.transTracker(0));
							svC.setId("222");
							svC.setServCh(15.00);
							svC.setName("Charge");
							svC.setDSold(Date.valueOf(dayFormat.format(dateNow)));
							objList.add(svC);
							SalesDao.saveServiceSales(svC);
							totServ+=15;									
						}
						if(chckbxBuild.isSelected()) {
								Sales svB = new Sales();
								svB.setTrans(TransDao.transTracker(0));
								svB.setId("111");
								svB.setServCh(20.00);
								svB.setName("Build");
								svB.setDSold(Date.valueOf(dayFormat.format(dateNow)));
								objList.add(svB);
								SalesDao.saveServiceSales(svB);
								totServ+=20;
							}
							if(chckbxReWrap.isSelected()) {
								Sales sv = new Sales();
								sv.setTrans(TransDao.transTracker(0));
								sv.setId("333");
								sv.setServCh(10.00);
								sv.setName("ReWrap");
								sv.setDSold(Date.valueOf(dayFormat.format(dateNow)));
								objList.add(sv);
								SalesDao.saveServiceSales(sv);
								totServ+=10;
							}
							
							List<Sales> list = new ArrayList<Sales>();
							
							//FOR INSERTING DATA TO SALES DATABASE
							for(int row=0;row<tableCart.getRowCount();row++){
								String categ = InventoryDao.searchRecord(modelCart.getValueAt(row, 1).toString()).getCateg();
								Double sell = InventoryDao.searchRecord(modelCart.getValueAt(row, 1).toString()).getCost() *
										(InventoryDao.searchRecord(modelCart.getValueAt(row, 1).toString()).getMarkup()/100);
									
									u.setSold(Integer.parseInt(modelCart.getValueAt(row,0).toString()));
									u.setId(modelCart.getValueAt(row,1).toString());
									u.setName(modelCart.getValueAt(row,2).toString());
									u.setCateg(categ);
									u.setSell(Double.parseDouble(modelCart.getValueAt(row,3).toString()));
									u.setTSales(Double.parseDouble(modelCart.getValueAt(row,4).toString())-deduc);
									u.setSell(sell);
									
								//Store each object to listF
								list.add(u);
								SalesDao.saveSales(u);
								SalesDao.updateStock(u);
								SalesWin.query();

								Sales uv = new Sales();
								uv.setTrans(TransDao.transTracker(0));
								uv.setSold(Integer.parseInt(modelCart.getValueAt(row,0).toString()));
								uv.setId(modelCart.getValueAt(row,1).toString());
								uv.setName(modelCart.getValueAt(row,2).toString());
								uv.setCateg(categ);
								uv.setSell(Double.parseDouble(modelCart.getValueAt(row,3).toString()));
								uv.setTSales(Double.parseDouble(modelCart.getValueAt(row,4).toString())-deduc);
								objList.add(uv);
							}//End of Loop Row
							
							//FOR RECORDING TRANSACTION
							t.setID(TransDao.transTracker(0));
							t.setDate(Date.valueOf(dayFormat.format(dateNow)));
							t.setAmt(Double.parseDouble(lblTotAmt.getText()) + totServ -deduc);
							t.setDiscount(deduc);
							t.setQty(Integer.parseInt(lblTotItems.getText()));
							
							TransDao.saveProdTrans(t);
							
							TransWindow.query();
							
							//RESETS VALUES IN ORDER WINDOW
							txtTransactionCode.setText(String.valueOf(TransDao.transTracker(0)));
							modelCart.setRowCount(0);
							lblTotAmt.setText("0.00");
							subTotal=0.00;
							totItems=0;
							lblTotItems.setText("0");
							rdbtnDiscount.setSelected(false);
							txtDiscount.setText("");
							chckbxBuild.setSelected(false);
							chckbxCharge.setSelected(false);
							chckbxReWrap.setSelected(false);
							
							ReceiptFrame frame = new ReceiptFrame(objList, disc);
							frame.setVisible(true);
							
						}//End of IF(msgDialog)
							else if(dialogResult == JOptionPane.NO_OPTION || dialogResult == JOptionPane.CLOSED_OPTION) {
								totAmt= 0.00;
							}
							
						}//END OF IF(modelCart is not empty)
						
						//IF ONLY SERVICE WAS RENDERED
						else if(modelCart.getRowCount()==0 && (chckbxBuild.isSelected() || chckbxCharge.isSelected() || chckbxReWrap.isSelected())) {

							discTotal = ServTotal;

							//TO CHECK IF rdbtnDiscount is enabled
							 //If enabled = Total amount to be paid will be deducted by the amount entered by the user
							if(rdbtnDiscount.isSelected()) {
								System.out.println("Redio is selected");
								disc = Double.parseDouble(txtDiscount.getText());
								discTotal -= disc;
								deduc = disc/(modelCart.getRowCount());
							}
							else {
								discTotal = ServTotal;
							}
							
							totAmt = discTotal;
							int dialogResult = JOptionPane.showConfirmDialog(orderPane, "Total Amount :\nPHP" + totAmt, "Confirmation", dialogButton);
							if(dialogResult == JOptionPane.YES_OPTION) {
								
							if(chckbxCharge.isSelected()) {
								Sales svC = new Sales();
								svC.setTrans(TransDao.transTracker(0));
								svC.setId("222");
								svC.setServCh(15.00);
								svC.setName("Charge");
								svC.setDSold(Date.valueOf(dayFormat.format(dateNow)));
								objList.add(svC);
								SalesDao.saveServiceSales(svC);
								totServ+=15;
							}
							if(chckbxBuild.isSelected()) {
								Sales svB = new Sales();
								svB.setTrans(TransDao.transTracker(0));
								svB.setId("111");
								svB.setServCh(20.00);
								svB.setName("Build");
								svB.setDSold(Date.valueOf(dayFormat.format(dateNow)));
								objList.add(svB);
								SalesDao.saveServiceSales(svB);
								totServ+=20;
							}
							if(chckbxReWrap.isSelected()) {
								Sales sv = new Sales();
								sv.setTrans(TransDao.transTracker(0));
								sv.setId("333");
								sv.setServCh(10.00);
								sv.setName("ReWrap");
								sv.setDSold(Date.valueOf(dayFormat.format(dateNow)));
								objList.add(sv);
								SalesDao.saveServiceSales(sv);
								totServ+=10;
							}
							
								
							//FOR RECORDING TRANSACTION
							t.setID(TransDao.transTracker(0));
							t.setDate(Date.valueOf(dayFormat.format(dateNow)));
							t.setDiscount(deduc);
							t.setAmt(totServ);
							t.setQty(1);
							
							TransDao.saveProdTrans(t);
							
							//TransWindow.query();
							txtTransactionCode.setText(String.valueOf(TransDao.transTracker(0)));
							modelCart.setRowCount(0);
							lblTotAmt.setText("0.00");
							subTotal=0.00;
							totItems=0;
							lblTotItems.setText("0");
							rdbtnDiscount.setSelected(false);
							txtDiscount.setText("");
							chckbxBuild.setSelected(false);
							chckbxCharge.setSelected(false);
							chckbxReWrap.setSelected(false);
							
							
							ReceiptFrame frame = new ReceiptFrame(objList, disc);
							frame.setVisible(true);
							
							}//End of (msgDialog)
								else if(dialogResult == JOptionPane.NO_OPTION || dialogResult == JOptionPane.CLOSED_OPTION) {
									totAmt= 0.00;
								}
						}
						else {
							JOptionPane.showMessageDialog(orderPane, "Cart is Empty");
						}
						}
					});
					
				//TO HERE	
				btnCheckout.setForeground(Color.WHITE);
				btnCheckout.setFont(new Font("Tahoma", Font.BOLD, 15));
				btnCheckout.setBackground(new Color(51, 0, 102));
				btnCheckout.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(255, 255, 255),
						new Color(204, 204, 204), new Color(64, 64, 64), new Color(64, 64, 64)));
				btnCheckout.addMouseListener(new BtnColor(btnCheckout));
				btnCheckout.setBounds(611, 591, 372, 57);
				orderPane.add(btnCheckout);
	}
}

