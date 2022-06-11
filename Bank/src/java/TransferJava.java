
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
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
@ManagedBean(name="kod")

@RequestScoped
public class TransferJava implements Serializable  {

    
    
    String bugun;
    private Double tutar;
    private String fullname,alantotal,alantece;
    private String gtutar, iban;
    String gndrntc= Login.tckn;
    String ban;
    int gg;
    String tip,hata;
    public Double getTutar() {
        return tutar;
    }

    public void setTutar(Double tutar) {
        this.tutar = tutar;
    }
        public String getGtutar() {
        return gtutar;
    }

    public void setGtutar(String gtutar) {
        this.gtutar = gtutar;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
    
    public String isle() throws SQLException, ClassNotFoundException{
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      //Getting the Connection object
      String URL = "jdbc:derby:sampleDB;create=true";
      Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/bank3");

      //Creating the Statement object
      Statement stmt = conn.createStatement();
String query;
      query = "SELECT * FROM accounts";
      ResultSet rs = stmt.executeQuery(query);
       while(rs.next()) {
           ban=iban;
    
         if(ban.equals(rs.getString("Iban")) ){
             tutar=rs.getDouble("Budget");
             alantece=String.valueOf(rs.getInt("Tc_no")) ;
             tip=String.valueOf(rs.getInt("Account_type")) ;
             gg=Integer.valueOf(gtutar);
          
             alantotal=String.valueOf(Double.valueOf(gg)+Double.valueOf(tutar));
             String ssql="UPDATE Accounts SET Budget="+alantotal+" WHERE Tc_no="+alantece+" AND Account_type="+tip;
         stmt.executeUpdate(ssql);
         
         
         break;
         }
         PayCredit.message="Bu ibana ait hesap bulunamadı";
        
         
      }
          
             String ww="SELECT * FROM accounts";
             ResultSet rr = stmt.executeQuery(ww);
             while(rr.next()){
                 if(gndrntc.equals(String.valueOf(rr.getInt("Tc_no")) ) && tip.equals(String.valueOf(rr.getInt("Account_type")) )){
             Double gndrnpara=rr.getDouble("Budget");
             if(Double.valueOf(gg)>gndrnpara){
                  alantotal=String.valueOf(Double.valueOf(tutar));
                 
             String ssql="UPDATE Accounts SET Budget="+alantotal+" WHERE Tc_no="+alantece+" AND Account_type="+tip;
         stmt.executeUpdate(ssql);
         hata="Bu gönderim için bakiye yetersiz";
         PayCredit.message =hata;
             break;
             
             }
             else{ 
             Double gndrnklnpara=gndrnpara-Double.valueOf(gg);
             String klnpara=String.valueOf(gndrnklnpara);
             String sssql="UPDATE Accounts SET Budget="+klnpara+" WHERE Tc_no="+gndrntc+" AND Account_type="+tip;
             stmt.executeUpdate(sssql);
              DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
          LocalDateTime now = LocalDateTime.now();  
         String ll =String.valueOf( dtf.format(now));
         String[] parts=ll.split("/");
         bugun=parts[0]+"-"+parts[1]+"-"+parts[2];
         String ic="INSERT INTO Actions (TC_NO, DATE, ACTION_TYPE, AMOUNT) VALUES ("+gndrntc+", '"+bugun+"', "+tip+", -"+gtutar+")";
      stmt.executeUpdate(ic);
       
      String iz="INSERT INTO Actions (TC_NO, DATE, ACTION_TYPE, AMOUNT) VALUES ("+alantece+", '"+bugun+"', "+tip+", "+gtutar+")";
      stmt.executeUpdate(iz);
      PayCredit.message="Gönderim başarılı";
                 break;
             }}
             }
        
	return "anasayfa";
    }
    
    
    
    
    
}