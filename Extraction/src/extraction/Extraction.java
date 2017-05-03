/*
 To insert tuples in database
 */
package extraction;

import java.io.*;                       // Importing libraries
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;  
import org.jsoup.nodes.Document;  
import org.jsoup.nodes.Element;  
import org.jsoup.select.Elements;


public class Extraction {
    
    public static void extractLinks(String url,String common,String name,String password) throws Exception // Function to extract data of all links and insert them in the database
    {
        try
        {
            // Hashmap for storing months and their respective numbers
            Map<String,String> Months = new HashMap<String, String>(){{ put("Jan","01"); put("Feb","02");put("Mar","03");put("Apr","04");put("May","05");put("Jun","06");put("Jul","07");put("Aug","08");put("Sep","09");put("Oct","10");put("Nov","11");put("Dec","12");}};
            
            
            Class.forName("org.postgresql.Driver");
             
            Connection con= DriverManager.getConnection("jdbc:postgresql://localhost:5432/ProjectData",name, password);     // Establishing the connection to our database
        
            Statement stmt=(Statement)con.createStatement();

            int j=0,k = 0;
            try
            {
                Document doc = Jsoup.connect(url).timeout(0).userAgent("Mozilla").get();   
                Elements links = doc.select("link");        //Get various links
                Elements pubdate= doc.select("pubdate");    // Get published date
                Elements titles = doc.select("title");      // Get titles
                
                System.out.println(url+ " " + links.size() + " "+ pubdate.size() + " " + titles.size() );
                
                for (Element link : links) 
                {
                    if(link.toString().contains(".com/"+common) )
                    {
                        String s= link.toString();
                        StringTokenizer st=new StringTokenizer(pubdate.get(j).toString()," ,");
                        st.nextToken();
                        st.nextElement();
                        String date=st.nextToken();
                        String month=st.nextToken();
                        String year=st.nextToken();
   
                        j++;
                        
                        String title = titles.get(k).toString().replaceAll("\\<[^>]*>","").replaceAll("'", "''");
                        k++;
                        
                        String insert = null;
                       
                        if(link.text().contains("newindianexpress"))
                        {
                           insert="insert into link_data_info values('"+link.text()+"','"+title+"','"+ year + "-" + Months.get(date) + "-" + month +"')";
                          // insert = "update link_data_info set day = '"+ year + "-" + Months.get(date) + "-" + month +"' where link = '"+link.text()+"'";
                        }
                        else
                        {
                            insert="insert into link_data_info values('"+link.text()+"','"+title+ "','"+ year + "-" + Months.get(month) + "-" + date +"')";
                           //insert = "update link_data_info set day = '"+ year + "-" + Months.get(month) + "-" + date +"' where link = '"+link.text()+"'";
                        }
                        try
                        {
                            stmt.executeUpdate(insert);     //Execute the insert command
                        }
                        catch(Exception e)
                        {
                            System.out.println(e.getCause()  + e.getMessage() + insert);
                        }
                    }
                    
                }
            }
            catch(Exception e)
            {
                System.err.println(e.getClass()+ " Message : "  + e.getMessage() );
            }
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.err.println("Connection Problem : " + e.getMessage());
        }
    }
    public static void main(String[] args) throws IOException
    {
        Scanner sc=new Scanner(System.in); 
            System.out.println("Enter the user name : ");
            String name=sc.next();  
            System.out.println("Enter the password : ");
            String password=sc.next(); 
        try 
        {
            extractLinks("http://www.thehindu.com/news/national/?service=rss","news",name,password);
            
            extractLinks("http://www.deccanherald.com/rss/national.rss","content",name,password);
            
            extractLinks("http://timesofindia.indiatimes.com/rssfeeds/-2128936835.cms","india",name,password);
            
            extractLinks("http://economictimes.indiatimes.com/rssfeeds/1052732854.cms","news",name,password); 
            
            extractLinks("http://www.newindianexpress.com/Nation/rssfeed/?id=170&getXmlFeed=true","nation",name,password);
            
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(Extraction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
    