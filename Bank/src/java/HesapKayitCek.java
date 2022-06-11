import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name="HesapKayitCek")
@RequestScoped
public class HesapKayitCek{
    public List<Map> items = new ArrayList<>();
    public List<Map> Hesaplarim() throws ClassNotFoundException, SQLException {
        
        String iban;
        String budget;
        String money;

        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

        String URL = "jdbc:derby:sampleDB;create=true";
        Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/bank3");
        
        
        
        Statement stmt = (Statement) conn.createStatement();
        String query;
        query = "SELECT * FROM ACCOUNTS "
                + "WHERE ACCOUNTS.TC_NO=123";
        ResultSet rs = stmt.executeQuery(query);

        while(rs.next()) {

            
            Map<String, String> map = new HashMap<>();
            iban = rs.getString("IBAN");
            map.put("iban",iban);
            budget = String.valueOf(rs.getDouble("BUDGET"));
            map.put("budget",budget);
            money = String.valueOf(rs.getInt("ACCOUNT_TYPE"));
            if(money.equals("0")){
                money = "TL";
            }
            else if(money.equals("1")){
                money = "USD";
            }
            else if(money.equals("2")){
                money = "EUR";
            }
            map.put("money",money);
            
            items.add(map);

        }
        
       

        return items;
    }

}
