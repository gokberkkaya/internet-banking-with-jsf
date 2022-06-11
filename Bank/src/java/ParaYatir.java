
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author OSMAN
 */
@ManagedBean(name="ParaYatir")
@RequestScoped
public class ParaYatir {
    public static String paraturu,paramiktari,tur, totalpara,bugun;
public static String kullanicitc=Login.tckn;
    public String getParaturu() {
        return paraturu;
    }

    public void setParaturu(String paraturu) {
        ParaYatir.paraturu = paraturu;
    }

    public String getParamiktari() {
        return paramiktari;
    }

    public void setParamiktari(String paramiktari) {
        ParaYatir.paramiktari = paramiktari;
    }
    public String yatir() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
         if(paraturu.equals("TL")){tur="0";}
        else if(paraturu.equals("USD")){tur="1";}
        else{tur="2";}
              Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      //Getting the Connection object
      String URL = "jdbc:derby:sampleDB;create=true";
      Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/bank3");

      //Creating the Statement object
      Statement st = conn.createStatement();
String query;
      query = "SELECT * FROM Accounts WHERE Tc_no="+kullanicitc+" AND Account_type="+tur;
      ResultSet rs = st.executeQuery(query);
      while(rs.next()){
      Double anapara=rs.getDouble("Budget");
      totalpara=String.valueOf(anapara+Double.valueOf(paramiktari));
       DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
          LocalDateTime now = LocalDateTime.now();  
         String ll =String.valueOf( dtf.format(now));
         String[] parts=ll.split("/");
         bugun=parts[0]+"-"+parts[1]+"-"+parts[2];
      String sql="UPDATE Accounts SET Budget="+totalpara+" WHERE Tc_no="+kullanicitc+" AND Account_type="+tur;
      String ic="INSERT INTO Actions (TC_NO, DATE, ACTION_TYPE, AMOUNT) VALUES ("+kullanicitc+", '"+bugun+"', "+tur+", +"+paramiktari+")";
      st.executeUpdate(ic);
      st.executeUpdate(sql);
      PayCredit.message="PARA YATIRMA BAŞARILI";
      return "anasayfa";
      
      }
        
        
        
        
        
        
        
        
        return "anasayfa";
}
}
