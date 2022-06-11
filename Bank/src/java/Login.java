
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name="Login")
@RequestScoped
public class Login {
    
    public static String tckn;
    public static String password;
    public static String newPassword;

    public String asd() throws ClassNotFoundException, SQLException {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

      String URL = "jdbc:derby:sampleDB;create=true";
      Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/bank3");


        Statement stmt = conn.createStatement();
        String query;
        query = "SELECT * FROM customers ";
        ResultSet rs = stmt.executeQuery(query);
        while(rs.next()) {
          
          String nm=(rs.getString("Tc_no"));
          String ps =rs.getString("Password");
          if(nm.equals(tckn) && ps.equals(password)){
          dbTc=rs.getString("Tc_no");
          dbPw=rs.getString("Password");
          return "anasayfa";
            }
        }
        return "login";
        
    }

    
    public static String dbTc;
    public static String dbPw;

    public String getTckn() {
        return tckn;
    }

    public void setTckn(String tckn) {
        this.tckn = tckn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}