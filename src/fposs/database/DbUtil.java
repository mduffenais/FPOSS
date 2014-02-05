/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fposs.database;

import java.awt.image.ImageObserver;
import java.sql.SQLException;
import javax.swing.JTextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
            categoriesLoad[rsCat.getRow()-1][0]= rsCat.getString("catagoryId");
            categoriesLoad[rsCat.getRow()-1][1]=rsCat.getString("category_name");
            categoriesLoad[rsCat.getRow()-1][2]=rsCat.getString("displayOrder");
             
            }
            System.out.println("this is in load " +rsCat);
           return categoriesLoad;
        }
      
    }
    
        public static boolean logIn() throws SQLException {
        boolean loggedIn = false;
            try{
            try (Connection conn = DbConnect.Connect()) {
                //Statement stmt = conn.createStatement();
                String sql = "Select * from users where username='"+userName+ "' and password ='"+password+"'";
                ResultSet rs = conn.createStatement().executeQuery(sql);
                if ( rs.next() ) {
                    System.out.println("This is a log in");
                    loggedIn=true;
                }
                else{
                    System.out.println("Nope didnt work");
                }   
            conn.close();
            }
            
        return loggedIn;
        }
        catch(SQLException e){}
           return loggedIn;

        }
    
}
