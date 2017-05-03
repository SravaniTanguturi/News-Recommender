
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateDatabase {
    public static void main(String[] args) throws IOException
    {
        try {
            
            Class.forName("org.postgresql.Driver");
            Connection con= DriverManager.getConnection("jdbc:postgresql://localhost:5432/as22","sravani", "1234");
        
            Statement stmt=(Statement)con.createStatement();
            
            try {
                stmt.executeUpdate(" create database Project");
                
            } catch (Exception ex) {
                System.err.println(ex);
            }
            
        } catch (Exception ex) {
           System.err.println(ex);
        }
        
    }
    
}
