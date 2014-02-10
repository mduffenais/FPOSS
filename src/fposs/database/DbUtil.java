/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fposs.database;

import fposs.Sales;
import java.awt.image.ImageObserver;
import java.sql.SQLException;
import javax.swing.JTextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.security.util.BigInt;
/**
 *
 * @author hirev
 */
public class DbUtil {
    protected static String userName;
    protected static String password;
   //protected static int categories;
     

    public DbUtil(String user, String Password) throws SQLException {
        DbUtil.userName=user;
        DbUtil.password=Password;
        logIn();
    }
  
  
    public static String[][] loadItems(String categories) throws SQLException{
        ResultSet rsItems;
        try (Connection conn = DbConnect.Connect()) {
         //   Statement sLoadI = conn.createStatement();
            String sql = "Select * from products where categoryid =" + categories;
            rsItems = conn.createStatement().executeQuery(sql);
            rsItems.last();
         String items[][] = new String[rsItems.getRow()][6];
         rsItems.beforeFirst();
         while(rsItems.next()){
         items[rsItems.getRow()-1][0] = rsItems.getString("barcode");
         items[rsItems.getRow()-1][1] = rsItems.getString("product_Name");
         items[rsItems.getRow()-1][2] = rsItems.getString("categoryid");
         items[rsItems.getRow()-1][3] = rsItems.getString("price");
         items[rsItems.getRow()-1][4] = rsItems.getString("taxable");
         items[rsItems.getRow()-1][5] = rsItems.getString("display_Order");
         }
           return items;
        }
  
  
    }
    public static void editItems(String barcode,String productName, int categoryid, double price, int taxable, int displayOrder) throws SQLException{
  String sql;  
    Connection conn = DbConnect.Connect();
    sql="UPDATE products SET product_Name='"+productName+"',categoryid="+categoryid+",price="+price+
            ",taxable="+taxable+",display_Order="+displayOrder+" where barcode="+barcode;
    System.out.println(sql);
conn.createStatement().executeUpdate(sql);

    }
    
    public static String[] loadCompanySetup() throws SQLException{
    String[] setup = new String[5];
    ResultSet rsSetup;
    try (Connection conn = DbConnect.Connect()) {
         //   Statement sLoadI = conn.createStatement();
            String sql = "Select * from company_setup limit 1";
            rsSetup = conn.createStatement().executeQuery(sql);
            rsSetup.next();
         setup[0]=rsSetup.getString("business_number");
         setup[1]=rsSetup.getString("company_name");
         setup[2]=rsSetup.getString("company_address");
         setup[3]=rsSetup.getString("company_phone");
         setup[4]=rsSetup.getString("tax_rate");
            
    return setup;
    }
    
    }
    
    public static String[][] loadCategories() throws SQLException{
        ResultSet rsCat;
        try (Connection conn = DbConnect.Connect()) {
            //Statement sLoadC = conn.createStatement();
            String sql = "Select * from categories order by displayOrder";
            rsCat = conn.createStatement().executeQuery(sql);
            rsCat.last();
            String categoriesLoad[][] = new String[rsCat.getRow()][3];
            rsCat.beforeFirst();
            while(rsCat.next()){
            categoriesLoad[rsCat.getRow()-1][0]=rsCat.getString("catagoryId");
            categoriesLoad[rsCat.getRow()-1][1]=rsCat.getString("category_name");
            categoriesLoad[rsCat.getRow()-1][2]=rsCat.getString("displayOrder");
             
            }
            System.out.println("this is in load " +rsCat);
           return categoriesLoad;
        }
      
    }
    
        public static String[] logIn() throws SQLException {
        String[] loggedIn = new String[3];
        
            try{
            try (Connection conn = DbConnect.Connect()) {
                //Statement stmt = conn.createStatement();
                String sql = "Select * from users where username='"+userName+ "' and password ='"+password+"'";
                ResultSet rs = conn.createStatement().executeQuery(sql);
                if ( rs.next() ) {
                    loggedIn[0]="true";
                    loggedIn[1] = userName;
                    loggedIn[2] = rs.getString("level");     
                }
                else{
                    loggedIn[0]="false";
                    
                    System.out.println("Nope didnt work");
                }   
                
            conn.close();
            }
            
        return loggedIn;
        }
        catch(SQLException e){}
           return loggedIn;

        }
        public static void salesUpdate(String userName,Double subTotal,Double taxTotalCalculated,Double discountEntry,Double amountPaidEntry) throws SQLException{
            try (Connection conn = DbConnect.Connect()) {
            String sql = "INSERT INTO saleslog (username,subtotal,tax,discount, amount_Paid)"
                    +"VALUES ('"+userName+"',"+subTotal+","+taxTotalCalculated+","+discountEntry+","+amountPaidEntry+")"; 
        System.out.println(sql);
            conn.createStatement().executeUpdate(sql);
            }
            
        }
        public static void deleteUser(String userName) throws SQLException{
         String sql;
            Connection conn = DbConnect.Connect();
        sql = "DELETE FROM users WHERE username ='"+userName+"'";
          conn.createStatement().executeUpdate(sql);
        }
        
        public static void deleteItem(String barcode) throws SQLException{
         String sql;
         
            Connection conn = DbConnect.Connect();
        sql = "DELETE FROM products WHERE barcode ="+barcode;
          conn.createStatement().executeUpdate(sql);
        }
        
        public static void addUser(String userName, String password, int level) throws SQLException{
           String sql;
            Connection conn = DbConnect.Connect();
        sql = "INSERT INTO users (`username`, `password`, `level`) VALUES('"+userName+"','"+password+"','"+level+"')";
               conn.createStatement().executeUpdate(sql);
        
        }
        
        
        public static void userUpdate(String editUserName, String editPassword, int level) throws SQLException{
            String sql;
            Connection conn = DbConnect.Connect();
            System.out.println(editPassword);
        if(editPassword.equals("*********")){
            
        sql = "UPDATE users SET level="+level+" WHERE username ='"+editUserName+"'";
        
        }
        else{
        sql="UPDATE users SET password='"+editPassword+"',level="+level+" WHERE username ='"+editUserName+"'";
                

        }
            System.out.println(sql);
         conn.createStatement().executeUpdate(sql);
        }
        public static String[][] getUsers() throws SQLException{
  ResultSet rsUsers;
        try (Connection conn = DbConnect.Connect()) {
            //Statement sLoadC = conn.createStatement();
            String sql = "Select * from users";
            rsUsers = conn.createStatement().executeQuery(sql);
            rsUsers.last();
            String users[][] = new String[rsUsers.getRow()][2];
            rsUsers.beforeFirst();
            while(rsUsers.next()){
            users[rsUsers.getRow()-1][0]=rsUsers.getString("username");
            users[rsUsers.getRow()-1][1]=rsUsers.getString("level");
            }
        return users;
        } }
        public static  String[][] loadItems() throws SQLException{
            ResultSet rsItems;
            try (Connection conn = DbConnect.Connect()) {
            //Statement sLoadC = conn.createStatement();
            String sql = "Select * from products";
            rsItems = conn.createStatement().executeQuery(sql);
            rsItems.last();
            String items[][] = new String[rsItems.getRow()][6];
            rsItems.beforeFirst();
            while(rsItems.next()){
            items[rsItems.getRow()-1][0]=rsItems.getString("barcode");
            items[rsItems.getRow()-1][1]=rsItems.getString("product_Name");
            items[rsItems.getRow()-1][2]=rsItems.getString("categoryid");
            items[rsItems.getRow()-1][3]=rsItems.getString("price");
            items[rsItems.getRow()-1][4]=rsItems.getString("taxable");
            items[rsItems.getRow()-1][5]=rsItems.getString("display_Order");
            }
        return items;    
        }}
  public static void delCat (String selected) throws SQLException{
            try (Connection conn = DbConnect.Connect()) {
                String sql = "DELETE FROM categories WHERE catagoryid="+Integer.parseInt(selected);
                //System.out.println(sql);
                conn.createStatement().executeUpdate(sql);
                conn.close();
            }
        }
        
        public static void editCat(String dispDesc, String newCat, int catOrd) throws SQLException{
            try (Connection conn = DbConnect.Connect()) {
                String sql = "UPDATE categories"
                        +" SET category_name='"+newCat+"', displayOrder="+catOrd+
                        " WHERE category_name='"+dispDesc+"'";
                conn.createStatement().executeUpdate(sql);
                conn.close();
            }
        }
         //These are the methods for adding, deleteing, and updating the entries on the categories screen
        public static void addCat(String newCat, int orderCat) throws SQLException{
            try (Connection conn = DbConnect.Connect()) {
            String sql = "INSERT INTO categories(category_name,displayOrder)" 
                    +"VALUES('"+newCat+"',"+orderCat+")";
                //System.out.println(sql);
                conn.createStatement().executeUpdate(sql);
            conn.close();
        }
        }
            
        public static void addItem(String barcode,String productName, int categoryid, double price, int taxable, int displayOrder) throws SQLException{
  String sql;  
    Connection conn = DbConnect.Connect();
    sql="INSERT INTO `products`(`barcode`, `product_Name`, `categoryid`, `price`, `taxable`, `display_Order`)"
    +"VALUES ("+barcode+",'"+productName+"',"+categoryid+","+price+","+taxable+","+displayOrder+")";
    
    System.out.println(sql);
conn.createStatement().executeUpdate(sql);
        }
    
}
