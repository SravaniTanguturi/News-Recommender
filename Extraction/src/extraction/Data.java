/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extraction;


import de.l3s.boilerpipe.BoilerpipeProcessingException;
import java.net.URL;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Data {

    //private static final String output_file = "/home/deepika/Desktop";

    public static void main(String[] args) throws IOException {
        
        try
        {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ProjectData","sravani", "1234");
        Statement stmt=(Statement)con.createStatement();
        //con.setNetworkTimeout(null, 0);
        
        int j=0;
            try
            {  
                    String s="";
                        //System.out.println("pub date "+year+month+date);
                        j++;
                        String select="select * from link_data_info values";
                        
                        try
                        {
                            ResultSet st=stmt.executeQuery(select);
                            while(st.next())
                            {
                            String url=st.getString(1);
                            String np_name;
                            if(url.contains("thehindu"))
                                {
                                    np_name="TheHindu";
                                }
                                else if(url.contains("economictimes"))
                                {
                                    np_name="EconomicTimes";
                                }
                                else if(url.contains("timesofindia"))
                                {
                                    np_name="TimesofIndia";
                                }
                                else if(url.contains("thehansindia"))
                                {
                                    np_name="TheHansIndia";
                                }
                                else
                                {
                                    np_name="DeccanHerald";
                                }
                            String h= "/"+url.replace("/", "-")+".txt";
                            int year=st.getInt(2);
                            String month = st.getString(3);
                            int date=st.getInt(4);
                            BufferedWriter output;
                            File dir1 = new File("/home/sravs/NetBeansProjects/Extraction/"+year+month+date);
                            dir1.mkdir();
                            File dir2 = new File(dir1+"/"+np_name);
                            dir2.mkdir();
                            try
                            {
                            File dir= new File(dir2+h);
                            dir.createNewFile();
                            String article;
                            try {
                                URL u=new URL(url);
                                article = ArticleExtractor.INSTANCE.getText(u);
                                output = new BufferedWriter(new FileWriter(dir));
                                //output.newLine();
                                output.write(article);
                                output.close();
                                //return ;
                            }
                            catch (BoilerpipeProcessingException | IOException e )
                            {
                            System.err.println("werfvf"+e.getLocalizedMessage());
                            }
                            }
                            catch(Exception e)
                            {
                                System.out.println("poi"+e.getLocalizedMessage());
                            }
                            }
                        }
                        catch(Exception e)
                        {
                            System.err.println("werfvf123"+e.getMessage());
                        }
            }
            catch(Exception e)
            {
                System.err.println(e.getMessage());
            }
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.err.println(e.getMessage());
        }   
    }
}
