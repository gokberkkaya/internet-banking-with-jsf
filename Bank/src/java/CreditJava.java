
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
@ManagedBean(name="Kredi")
@RequestScoped
public class CreditJava {
    private String cekilenkredi,kredituru,tur,bugun,odenecekgun,odenecektutar;
    Boolean borc;
    String tcno="123";
    Double cekilebilirkredi=5000.0;
    Double hesapparasi;
    

    public String getCekilenkredi() {
        return cekilenkredi;
    }

    public void setCekilenkredi(String cekilenkredi) {
        this.cekilenkredi = cekilenkredi;
    }

    public String getKredituru() {
        return kredituru;
    }

    public void setKredituru(String kredituru) {
        this.kredituru = kredituru;
    }
    public String isle() throws ClassNotFoundException, SQLException{
        if(kredituru.equals("TL")){tur="0";}
        else if(kredituru.equals("USD")){tur="1";}
        else{tur="2";}
         Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      //Getting the Connection object
      String URL = "jdbc:derby:sampleDB;create=true";
      Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/bank3");

      //Creating the Statement object
      Statement stmt = conn.createStatement();
String query;
      query = "SELECT * FROM customers";
      ResultSet rs = stmt.executeQuery(query);
       while(rs.next()) {
       if(tcno.equals(String.valueOf(rs.getString("Tc_no")))){
       if(Integer.valueOf(cekilenkredi)<cekilebilirkredi && !rs.getBoolean("Debt")){
           String cc="SELECT * FROM Accounts";
           ResultSet rr=stmt.executeQuery(cc);
           while(rr.next()){
               if(tcno.equals(String.valueOf(rr.getInt("Tc_no"))) && tur.equals(rr.getString("Account_type"))){
           hesapparasi=rr.getDouble("Budget");
               break;}}
           int hg=Integer.valueOf(cekilenkredi);
           Double kc=Double.valueOf(hg);
           String total=String.valueOf(kc+hesapparasi );
           String sql="UPDATE Accounts SET Budget="+total+" WHERE Tc_no="+tcno+" AND Account_type="+tur;
 String ssql="UPDATE Customers SET Debt=true WHERE Tc_no="+tcno;
       stmt.executeUpdate(sql);
    stmt.executeUpdate(ssql);
       System.out.print(tur);
       System.out.println(hesapparasi);
       System.out.print(total);
       System.out.print(tcno);
       System.out.print(kc);
          DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
          LocalDateTime now = LocalDateTime.now();  
         bugun =String.valueOf( dtf.format(now));
         String[] parts=bugun.split("/");
         int year=Integer.valueOf(parts[0]);
         int nextyear=year+1;
         odenecekgun=String.valueOf(nextyear)+"-" +parts[1]+"-"+parts[2];
       System.out.print(bugun);
       System.out.print(year);
       System.out.print(odenecekgun);
       odenecektutar=String.valueOf(kc*1.4);
       String stcno=String.valueOf(tcno);
       String scredittype=String.valueOf(tur);
       String date=odenecekgun;
       String receivedamount=cekilenkredi;
       String remaining_debt=odenecektutar;
      String in="INSERT INTO Received_credits (TC_NO, CREDIT_TYPE, DATE, RECEIVED_AMOUNT, REMAINING_DEBT) VALUES ("+stcno+", "+scredittype+", '"+date+"', "+receivedamount+", "+remaining_debt+")";
      String ic="INSERT INTO Actions (TC_NO, DATE, ACTION_TYPE, AMOUNT) VALUES ("+stcno+", '"+date+"', "+scredittype+", +"+receivedamount+")";
      stmt.executeUpdate(ic);
      stmt.executeUpdate(in);
              PayCredit.message="Kredi çekme işlemi başarılı";
       return "anasayfa";
       
       }
       PayCredit.message="Çekilebilir tutarın üstündesiniz veya ödenmemiş borcunuz bulunmakta";
       
       }

       }

        return "anasayfa";
    
    }
    
}
