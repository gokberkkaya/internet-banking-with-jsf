
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
@ManagedBean(name="PayCredit")
@RequestScoped
public class PayCredit {
    public static String bugun,kalanborc , borcturu, sonodemetarihi, totalhtmltext, odenecektutar,dt, hesapbakiyesi,kalanhesapbakiyesi,message;


    public static String kullanicitc = Login.tckn;
    

   
    public String borccek() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
       Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      //Getting the Connection object
      String URL = "jdbc:derby:sampleDB;create=true";
      Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/bank3");

      //Creating the Statement object
      Statement st = conn.createStatement();
String query;
      query = "SELECT * FROM customers";
      ResultSet rs = st.executeQuery(query);
while(rs.next()){
if(kullanicitc.equals(String.valueOf(rs.getInt("Tc_no"))) && rs.getBoolean("Debt")){
String sqql="SELECT * FROM Received_credits WHERE Tc_no="+kullanicitc;
ResultSet rr=st.executeQuery(sqql);
while(rr.next()){
kalanborc=String.valueOf(rr.getDouble("Remaining_debt"));
dt=String.valueOf(rr.getInt("Credit_type"));
if(dt.equals("0")){borcturu="TL";}
else if(dt.equals("1")){borcturu="USD";}
else{borcturu="EURO";}
sonodemetarihi=String.valueOf(rr.getDate("Date"));
break;
}
break;}
}

conn.close();
if(kalanborc==null){kalanborc="YOK"; sonodemetarihi="YOK"; borcturu="";}
totalhtmltext=sonodemetarihi;
        System.out.println(totalhtmltext);
        return kalanborc+" "+borcturu;
    }
     public String ode() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
         Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      //Getting the Connection object
      String URL = "jdbc:derby:sampleDB;create=true";
      Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/bank3");

      //Creating the Statement object
      Statement st = conn.createStatement();
String query;
      query = "SELECT * FROM customers";
      ResultSet rs = st.executeQuery(query);
     while(rs.next()){
         if(kullanicitc.equals(String.valueOf(rs.getInt("Tc_no"))) && rs.getBoolean("Debt")){
          String ssql="SELECT * FROM Accounts WHERE Tc_no="+kullanicitc+" AND Account_type="+dt;
          ResultSet rr=st.executeQuery(ssql);
          while(rr.next()){
          Double cp=rr.getDouble("Budget");
          if(cp>Double.valueOf(odenecektutar)){
          kalanhesapbakiyesi=String.valueOf(cp-Double.valueOf(odenecektutar));
          kalanborc=String.valueOf(Double.valueOf(kalanborc)-Double.valueOf(odenecektutar));
          String RCupdate="UPDATE Received_credits SET Remaining_debt="+kalanborc+" WHERE Tc_no="+kullanicitc;
          String Aupdate="UPDATE Accounts SET Budget="+kalanhesapbakiyesi+" WHERE Tc_no="+kullanicitc+" AND Account_type="+dt;
          st.executeUpdate(RCupdate);
          st.executeUpdate(Aupdate);
          DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
          LocalDateTime now = LocalDateTime.now();  
         String ll =String.valueOf( dtf.format(now));
         String[] parts=ll.split("/");
         bugun=parts[0]+"-"+parts[1]+"-"+parts[2];
         String ic="INSERT INTO Actions (TC_NO, DATE, ACTION_TYPE, AMOUNT) VALUES ("+kullanicitc+", '"+bugun+"', "+dt+", -"+odenecektutar+")";
      st.executeUpdate(ic);
          if(kalanborc.equals("0.0")){
          String UpdateDebt="UPDATE Customers SET Debt=false WHERE Tc_no="+kullanicitc;
          String DeleteRCLine="DELETE FROM RECEIVED_CREDITS WHERE TC_NO ="+kullanicitc;
          st.executeUpdate(UpdateDebt);
          st.executeUpdate(DeleteRCLine);
          message="Borcunuz Kalmamıştır";
          return "anasayfa";}
          
          
          message="Ödeme işleminiz tamamlandı";
          return "anasayfa";}
          
          
          
          message="Hesapta yeterli bakiye yok";
          return "anasayfa";
         
          }}
         
         message="Kredi borcunuz olmadığı için ödeme işlemi gerçekleştirilemedi";
      return "anasayfa";
      
      
      
      
      
      
      
      }
         
         
         
         
         
         
         
         
         
         
         
         return "message";
         
     }
   public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        PayCredit.message = message;
    }
    public String getBorcturu() {
        return borcturu;
    }

    public void setBorcturu(String borcturu) {
        PayCredit.borcturu = borcturu;
    }

    public String getSonodemetarihi() {
        return sonodemetarihi;
    }

    public void setSonodemetarihi(String sonodemetarihi) {
        PayCredit.sonodemetarihi = sonodemetarihi;
    }

    public String getTotalhtmltext() {
        return totalhtmltext;
    }

    public void setTotalhtmltext(String totalhtmltext) {
        PayCredit.totalhtmltext = totalhtmltext;
    }

    public String getKullanicitc() {
        return kullanicitc;
    }

    public void setKullanicitc(String kullanicitc) {
        PayCredit.kullanicitc = kullanicitc;
    }
     public String getKalanborc() {
        return kalanborc;
    }

    public void setKalanborc(String kalanborc) {
        this.kalanborc = kalanborc;
    }
    
    public String getOdenecektutar() {
        return odenecektutar;
    }

    public void setOdenecektutar(String odenecektutar) {
        PayCredit.odenecektutar = odenecektutar;
    }
    
}
