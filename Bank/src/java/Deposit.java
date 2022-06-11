import static java.awt.Event.INSERT;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static javafx.scene.input.KeyCode.INSERT;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import static javax.swing.DropMode.INSERT;



@ManagedBean(name="Deposit")
@RequestScoped
public class Deposit {

    private String dp;
    private String bt;
    public String tur;
    
    public String tcpp =Login.tckn;
    
    public String dps() throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

        String URL = "jdbc:derby:sampleDB;create=true";
        Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/bank3");

        Statement stmt = conn.createStatement();
        String query;
        query = "SELECT * FROM Accounts ";
        ResultSet rs = stmt.executeQuery(query);
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
        LocalDateTime now = LocalDateTime.now();
        String today =dtf.format(now);
        String[] split=today.split("/");
        today=split[0]+"-"+split[1]+"-"+split[2];
        
        if(bt.equals("TL")){tur="0";}
        else if(bt.equals("USD")){tur="1";}
        else{tur="2";}
        
        while(rs.next()) {
            
            if(String.valueOf(rs.getInt("Tc_no")).equals(tcpp)){
            
                if(String.valueOf(rs.getInt("Account_type")).equals(tur)){
                    
                    Double bg = rs.getDouble("Budget");
                    bg=Double.valueOf(Double.valueOf(bg)-Double.valueOf(dp));
                    if(bg>=0){
                        
                        String dplast=String.valueOf(Double.valueOf(dp)*1.3);
                        String ss ="INSERT INTO APP.DEPOSIT (TC_NO, DEPOSIT_TYPE, DEPOSITED_AMOUNT, FINAL_AMOUNT, DATE) VALUES ("+tcpp+", "+tur+", "+dp+", "+dplast+", '"+today+"')";
                        stmt.executeUpdate(ss);
                        
                        String cc ="UPDATE APP.ACCOUNTS SET BUDGET="+bg+" WHERE Account_type="+tur+"and Tc_no="+tcpp;
                        stmt.executeUpdate(cc);
                        
                        dp="-"+dp;
                        String dd ="INSERT INTO APP.ACTIONS (TC_NO, DATE, ACTION_TYPE, AMOUNT) VALUES ("+tcpp+", '"+today+"', "+tur+", "+dp+")";
                        stmt.executeUpdate(dd);
                        
                        PayCredit.message = "Mevduat işleminiz başarılı";
                        return "anasayfa";
                    }
                    
                    PayCredit.message = "Yatırmak istediğiniz miktar hesabınızda bulunmamaktır";
                    return "anasayfa";
                }
                
                
            }
        }
        
        PayCredit.message = "Şuanda mevduat işlemi gerçekleştiremiyorsunuz";
        return "anasayfa";
    }
    
    
     public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }
    
        public String getBt() {
        return bt;
    }

    public void setBt(String bt) {
        this.bt = bt;
    }
   
}
