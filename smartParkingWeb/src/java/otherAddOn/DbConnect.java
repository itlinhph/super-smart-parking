/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otherAddOn;

import com.mysql.cj.api.jdbc.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author linhph
 */
public class DbConnect {
     //properties
    public Connection con;
    public Statement st;
    
    //contructor
    public DbConnect() throws SQLException,ClassNotFoundException{
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties props = new Properties();
            props.setProperty("connectionCollation", "utf8_unicode_ci");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_parking?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false","root","cpa");
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/name",props);
           
            st = (Statement) con.createStatement();
            st.executeUpdate("SET CHARACTER SET UTF8");
            
    }
}
