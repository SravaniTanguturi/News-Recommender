// To insert hashset of various tuples in database
package extraction;
import de.l3s.boilerpipe.BoilerpipeProcessingException;     //Import libraries
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import org.tartarus.martin.*;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Hash {

    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException 
    {
            try
            {
                FileReader fr1 = new FileReader("/home/sravs/NetBeansProjects/Extraction/src/extraction/stop-words-english.txt");
                try (BufferedReader br1 = new BufferedReader(fr1)) 
                {
                    HashSet<String> stopwords = new HashSet<>();
                    String line1;
                    while ((line1 = br1.readLine()) != null)
                    {
                        line1 = line1.toLowerCase();
                        stopwords.add(line1);
                    }
                    Stemmer ab = new Stemmer();
                    try
                    {
                        Class.forName("org.postgresql.Driver");
                        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ProjectData","sravani", "1234");
                        Statement stmt=(Statement)con.createStatement();
                        String regex = "[0-9]+";
                        String select="select link from link_data_info";
                        String article="";
                        try
                        {
                            ResultSet st=stmt.executeQuery(select);
                            List <String> list = new ArrayList<>();
                            while(st.next())
                            {
                               list.add(st.getString(1));
                            }
                            for(String url1:list)
                            {
                                article = "";
                                try
                                {
                                    URL u=new URL(url1);
                                    article = ArticleExtractor.INSTANCE.getText(u); // Get content of article
                                }
                                catch (BoilerpipeProcessingException | IOException e )
                                {
                                    System.err.println(e.getLocalizedMessage());
                                }
                                Set<String> s = null;
                                StringTokenizer st1 = new StringTokenizer(article," :><=;\\!/.,    \n&@#$%^*|~?'\"(){}[]");
                                s = new HashSet<>();
                                while(st1.hasMoreTokens())
                                {
                                    String word = st1.nextToken();
                                    word = word.toLowerCase();
                                    if(!stopwords.contains(word) && !word.matches(regex))       //Remove the stopwords
                                    {
                                        char[] c=word.toCharArray();
                                        ab.add(c,word.length());
                                        ab.stem();                                              // Stemming all the words
                                        String mod = ab.toString();
                                        s.add(mod);
                                    }
                                }
                                String update="update link_data_info set hashset='"+s+"' where link='"+url1+"'";
                                stmt.executeUpdate(update);                                     //Update table by inserting the hashset column
                            }
                        }
                        catch(Exception e)
                        {
                            System.out.println(e.getMessage());
                        }
                    	
                    }
                    catch(ClassNotFoundException | SQLException e)
                    {
                        System.err.println(e.getMessage());
                    }
                }catch(Exception e)
                {
                    System.err.println(e.getMessage());
                }
            }catch(Exception e)
            {
                System.err.println(e.getMessage());
            }
       
    }
}
