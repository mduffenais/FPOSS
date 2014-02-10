/*
 *
Developer : Mike Duffenais and Chad Paquet 
Since : Feb 10 2014
Sales screen requires log in and also DbUtil - builds cat and items buttons from DB 
 */

package fposs;

import fposs.database.DbConnect;
import fposs.database.DbUtil;
import java.awt.Button;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 *
 * @author hirev
 */
public class Sales extends javax.swing.JFrame {
    // create vars and arrays 
    private String bsNumber;
    private String cName;
    private String cAddress;
    private String cPhone;
    private Double taxRate;
    private JLayeredPane layeredPane;
    private Double subTotal = 0.00;
    private Double taxSubtotal = 0.00;
    private Double taxTotalCalculated=0.00;
    private Double amountDueCalculated=0.00;
    private Double amountPaidEntry=0.00;
    private Double discountEntry=0.00;
   String itemsArray[][]=new String[99][6];
   private static String userName;
   private static int level;
   DecimalFormat decim = new DecimalFormat("#.00");
  private ArrayList<String[][]> action = new ArrayList<String[][]>();
    /**
     * Creates new form Sales
     * @throws java.sql.SQLException
     */
   // constructor 
    public Sales(String[] loggedin) throws SQLException {
        //this.userName = userName;
        //this.level = level;
        this.level=Integer.parseInt(loggedin[2]);
        this.userName = loggedin[1];
        initComponents();
        onCreate();
        companySetup();
    }
    
public void companySetup() throws SQLException{
    // gets tax rate and company set up info 
String[] setup =new String[5];
setup = DbUtil.loadCompanySetup();
bsNumber= setup[0];
cName = setup[1];
cAddress = setup[2];
cPhone = setup[3];
taxRate = Double.parseDouble(setup[4]);

}    
    
public void onCreate() throws SQLException{
    // set close option 
     setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
 // set access to admin panel 
    if(level!=1){jPanel1.setEnabled(false);
            jButton2.setEnabled(false);
            jButton3.setEnabled(false);
            jButton4.setEnabled(false);
            jButton6.setEnabled(false);}
  // load catagories 
  double catNumeber = DbUtil.loadCategories().length;
  // rows to set grid layout 
  int rows = (int) Math.ceil(catNumeber/5);
  // set grid layout 
 jPanel2.setLayout(new GridLayout(rows, 5, 4, 4));
 jPanel3.setVisible(false);
 String array[][] = DbUtil.loadCategories();
  // create categories button 
 for(int i=0;i<catNumeber;i++)
        {   
            final JButton btn=new JButton(String.valueOf(array[i][1]));
                JPanel buttonPane = new JPanel();
                btn.setName(String.valueOf(array[i][0]));
                btn.addActionListener(new ActionListener() {
                    // set onclick lisitner 
        public void actionPerformed(ActionEvent ae2) {
           jPanel2.setVisible(false);
           JPanel itemPane = new JPanel();
           itemPane.setLayout(new GridLayout(5,5,5,5));
           itemPane.setVisible(true);
            try {
                loadItems(btn.getName());
            } catch (SQLException ex) {
                Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    });
                buttonPane.add(btn);
                jPanel2.add(buttonPane);
            
        }
        add(jPanel2);
        pack();
        setVisible(true);
    
    //this.add(panel);
  //  }
}
public void loadItems(String category) throws SQLException{
    
  double items = DbUtil.loadItems(category.toString()).length;

  int rows = (int) Math.ceil(items/5);

 jPanel3.setLayout(new GridLayout(rows, 5, 4, 4));
 String array[][] = DbUtil.loadItems(category);
  for(int i=0;i<items;i++)
        {   
  // create item buttons 
             JButton btn=new JButton(String.valueOf(array[i][1]));
                JPanel buttonPane = new JPanel();
                btn.setName(String.valueOf(array[i][1]));
                // load array 
                itemsArray[i][0]=String.valueOf(array[i][0]);
                itemsArray[i][1]=String.valueOf(array[i][1]);
                itemsArray[i][2]=String.valueOf(array[i][2]);
                itemsArray[i][3]=String.valueOf(array[i][3]);
                itemsArray[i][4]=String.valueOf(array[i][4]);
                itemsArray[i][5]=String.valueOf(array[i][5]);
                final int buttonIndex = i;
                btn.addActionListener(new ActionListener() {
        // set lisitner 
        public  void actionPerformed(ActionEvent ae2) {
           list1.add(String.valueOf(itemsArray[buttonIndex][1]));  
          subTotal += Double.parseDouble(itemsArray[buttonIndex][3]);
    
           // set taxable flag 
          if(itemsArray[4].equals("1")){
        taxSubtotal+= Double.parseDouble(itemsArray[buttonIndex][3]);
     
          }
          // calculate tax and update tax 
            taxTotalCalculated =taxSubtotal*(taxRate/100);
        amountDueCalculated = (taxTotalCalculated+subTotal)-(amountPaidEntry+discountEntry);
        amountDue.setText(decim.format(amountDueCalculated).toString());
           subTotals.setText(decim.format(subTotal).toString());
           taxTotal.setText(decim.format(taxSubtotal*taxRate/100).toString());
           total.setText(decim.format(subTotal+taxTotalCalculated).toString());
           action.add(itemsArray);
           jPanel2.setVisible(true);
           jPanel3.setVisible(false);
           jPanel3.removeAll();
           // populate oanel 
           JPanel itemPane = new JPanel();
           itemPane.setLayout(new GridLayout(5,5,5,5));
           itemPane.setVisible(true);
            
        }
    });
                buttonPane.add(btn);
                jPanel3.add(buttonPane);
            
        }
  jPanel3.setVisible(true);
        add(jPanel3);
        pack();
        setVisible(true);
 
}

 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 100), new java.awt.Dimension(0, 100), new java.awt.Dimension(32767, 100));
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        list1 = new java.awt.List();
        subTotals = new javax.swing.JFormattedTextField();
        taxTotal = new javax.swing.JFormattedTextField();
        total = new javax.swing.JFormattedTextField();
        amountPaid = new javax.swing.JFormattedTextField();
        discount = new javax.swing.JFormattedTextField();
        amountDue = new javax.swing.JFormattedTextField();
        buttonApplyDiscount = new javax.swing.JButton();
        buttonAmountPaid = new javax.swing.JButton();
        buttoonNewOrder = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Subtotal");

        jLabel2.setText("Tax");

        jLabel3.setText("Total");

        jLabel4.setText("Amount Paid");

        jLabel5.setText("Discount");

        jLabel6.setText("Amount Due");

        jLabel7.setText("Admin controls");

        jButton2.setText("User Control");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Categories");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Items");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton6.setText("Reports");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6)
                .addGap(0, 72, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton6))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 102));

        jPanel3.setBackground(new java.awt.Color(102, 255, 204));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 412, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 255, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        subTotals.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("¤#,##0.00"))));
        subTotals.setText("0.00");
        subTotals.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);

        taxTotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("¤#,##0.00"))));
        taxTotal.setText("0.00");
        taxTotal.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);

        total.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("¤#,##0.00"))));
        total.setText("0.00");
        total.setFocusCycleRoot(true);
        total.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);

        amountPaid.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("¤#,##0.00"))));
        amountPaid.setText("0.00");
        amountPaid.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);

        discount.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("¤#,##0.00"))));
        discount.setText("0.00");
        discount.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);

        amountDue.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("¤#,##0.00"))));
        amountDue.setText("0.00");
        amountDue.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
        amountDue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountDueActionPerformed(evt);
            }
        });

        buttonApplyDiscount.setText("Apply Discount");
        buttonApplyDiscount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonApplyDiscountActionPerformed(evt);
            }
        });

        buttonAmountPaid.setText("Amount PAid");
        buttonAmountPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAmountPaidActionPerformed(evt);
            }
        });

        buttoonNewOrder.setText("New Order");
        buttoonNewOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttoonNewOrderActionPerformed(evt);
            }
        });

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5))
                                .addGap(1, 1, 1))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(taxTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(total, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(amountDue)
                            .addComponent(subTotals)
                            .addComponent(discount, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(amountPaid))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buttonAmountPaid)
                            .addComponent(buttoonNewOrder)
                            .addComponent(buttonApplyDiscount))
                        .addGap(38, 38, 38)
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(list1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(list1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(subTotals, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(taxTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(discount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonApplyDiscount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(amountPaid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(buttonAmountPaid))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(amountDue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttoonNewOrder))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
// open reports screen 
        Reports reports = new Reports();
            reports.pack();
            reports.setVisible(true);    }//GEN-LAST:event_jButton6ActionPerformed

    private void amountDueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountDueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountDueActionPerformed

    private void buttonApplyDiscountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonApplyDiscountActionPerformed
          // apply discount 
        discountEntry = Double.parseDouble(discount.getText());
     amountDueCalculated = (taxTotalCalculated+subTotal)-(amountPaidEntry+discountEntry);
     amountDue.setText(decim.format(amountDueCalculated).toString());// TODO add your handling code here:
    }//GEN-LAST:event_buttonApplyDiscountActionPerformed

    private void buttonAmountPaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAmountPaidActionPerformed
  // apply amount paid 
        amountPaidEntry = Double.parseDouble(amountPaid.getText());
     amountDueCalculated = (taxTotalCalculated+subTotal)-(amountPaidEntry+discountEntry);
     amountDue.setText(decim.format(amountDueCalculated).toString());    }//GEN-LAST:event_buttonAmountPaidActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            // open category panel 
            Cat cat = new Cat();
            cat.pack();
            cat.setVisible(true);
         //      this.setVisible(false);
        }
        catch(Exception e){}
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            // open user control 
            UserControl  control = new UserControl();
            control.pack();
            control.setVisible(true);
            // this.setVisible(false);  
        } catch (SQLException ex) {
            Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void buttoonNewOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttoonNewOrderActionPerformed
        try {
            // new order sales update 
            DbUtil.salesUpdate(userName,subTotal,taxTotalCalculated,discountEntry,amountPaidEntry);
        } catch (SQLException ex) {
            Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
        }
        // set vars 
        subTotal = 0.00;
      taxSubtotal = 0.00;
      taxTotalCalculated=0.00;
      amountDueCalculated=0.00;
      amountPaidEntry=0.00;
      discountEntry=0.00;
     list1.removeAll();
     taxTotal.setText("0.00");
     subTotals.setText("0.00");
     total.setText("0.00");
     discount.setText("0.00");
     amountPaid.setText("0.00");
     amountDue.setText("0.00");
     
      }//GEN-LAST:event_buttoonNewOrderActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
try {
    // open items screen 
            Items  items = new Items();
            items.pack();
            items.setVisible(true);
            // this.setVisible(false);  
        } catch (SQLException ex) {
            Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
        }    }//GEN-LAST:event_jButton4ActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    new Sales().setVisible(true);
//                } catch (SQLException ex) {
//                    Logger.getLogger(Sales.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField amountDue;
    private javax.swing.JFormattedTextField amountPaid;
    private javax.swing.JButton buttonAmountPaid;
    private javax.swing.JButton buttonApplyDiscount;
    private javax.swing.JButton buttoonNewOrder;
    private javax.swing.JFormattedTextField discount;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private java.awt.List list1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JFormattedTextField subTotals;
    private javax.swing.JFormattedTextField taxTotal;
    private javax.swing.JFormattedTextField total;
    // End of variables declaration//GEN-END:variables

}
