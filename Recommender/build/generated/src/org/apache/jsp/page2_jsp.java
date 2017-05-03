package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import java.net.URL;
import de.l3s.boilerpipe.BoilerpipeDocumentSource.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.io.*;
import java.util.*;

public final class page2_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {


        public void we(String k,HttpServletRequest request) {
                //function to get parameters from index page and store in the newly defined variables
            
                url = request.getParameter("urlname");
                ss= request.getParameter("of");

            }
         public HashSet gethashset(String hs)
                 {
                     try  
                    {
                        FileReader fr1 = new FileReader("/home/sravs/NetBeansProjects/Recommender/web/stop_words.txt");
                        BufferedReader br1 = new BufferedReader(fr1);
                        stopwords = new ArrayList();
                        String line1;
                        while ((line1 = br1.readLine()) != null)
                        {
                            stopwords.add(line1);
                        }
                }catch(Exception e){}
                     HashSet h = new HashSet();
                     StringTokenizer st1 = new StringTokenizer(hs,":,[ ]-/'");
                     while(st1.hasMoreTokens())
                     {
                         String word = st1.nextToken();
                         word=word.toLowerCase();
                         if(!stopwords.contains(word))
                         h.add(word);
                     }
                     return h;
                 }
        
       public static String url="",opt,text="",hsmain,hse,maint,ss;
       List str;
       List stopwords;
       
            public class getUrls
            {
                public String mode()
                {
                    try
                        {
                           URL u=new URL(url);
                           text = ArticleExtractor.INSTANCE.getText(u);
                        }
                        catch (Exception e )
                        {
                            System.err.println("we"+e.getLocalizedMessage());
                        }
                        
                        return text;
                }
                
                public void article() throws Exception
                {
                    try
                    {
                        Class.forName("org.postgresql.Driver");
                        Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/ProjectData","sravani", "1234");
                        String select="select hashset,title from link_data_info where link=?";
                        try
                        {
                            PreparedStatement preparedStatement = con.prepareStatement(select);
                            preparedStatement.setString(1,url);
                            ResultSet st = preparedStatement.executeQuery();
                            if(st.next())
                            {
                                hsmain=st.getString("hashset");
                                maint=st.getString("title");
                            }
                        }
                        catch(Exception e) { }
                    }
                    catch(Exception e){}
                  
                       
                }
                
                public List RealatedArticle()
                {
                    String [] articles=null;
                    List urls = new ArrayList();
                    List comparison = new ArrayList();
                    Set mtlist=new HashSet();
                    Set tlist=new HashSet();
                    mtlist=gethashset(maint);
                    try
                    {
                        Class.forName("org.postgresql.Driver");
                        Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/ProjectData","sravani", "1234");
                        String select="select link,hashset,title from link_data_info where link != ?";
                        try
                        {
                            ResultSet st=null;
                            PreparedStatement preparedStatement = con.prepareStatement(select);
                            preparedStatement.setString(1,url);
                            try
                            {
                                st = preparedStatement.executeQuery();
                            }
                            catch(Exception e)
                            {
                                urls.add("preparedstatement error");
                                return urls;
                            }
                            
                            String hs;
                            while(st.next())
                            {
                                String hstitle="";   
                                hs = st.getString("hashset");
                                hstitle=st.getString("title");
                                HashSet H= gethashset(hs);
                                HashSet hsm = gethashset(hsmain);
                                Set intersection = new HashSet(H);
                                intersection.retainAll(hsm);
                                Set union = new HashSet(H);
                                union.addAll(hsm);                             
                                tlist=gethashset(hstitle);
                                Set tintersection=new HashSet(mtlist);   
                                tintersection.retainAll(tlist);
                                double comp = (intersection.size()*1000)/union.size();
                                int m=Math.max(tlist.size(),mtlist.size() );
                                                          double tcomp=tintersection.size()*1000/m;
                                //int tcomp =tlist.size();
                                comp=comp+tcomp;
                                System.out.println(comp);
                                //urls.add("jkl"+intersection.size());
                                if(comp > 0)
                                {
                                   // str.add(intersection.toString());
                                    comparison.add(comp);
                                    Collections.sort(comparison);
                                    Collections.reverse(comparison);
                                    int p;
                                    p=comparison.indexOf(comp);
                                    urls.add(p,st.getString("url"));

                                    //urls.add(st.getString("url"));
                                   // urls.add(p,comp);
                                    //urls.add(tcomp);
                                    // urls.add(tintersection);
                                }
                            }
                        }
                        catch(Exception e) { }
                    }
                    catch(Exception e){}
                                  return urls;
                        }
                    
        }
        
  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("       <title>JSP Page</title>\n");
      out.write("        <style >\n");
      out.write("select {list-style: none;padding: 0px;margin: 0px; width: 830px; }\n");
      out.write("option {list-style: none;padding: 0px;margin: 0px; width: 830px; }\n");
      out.write("\n");
      out.write("         </style>\n");
      out.write("       \n");
      out.write("    ");
      out.write("\n");
      out.write("        </head>\n");
      out.write("        <body>\n");
      out.write("        \n");
      out.write("        ");

           
            we("1",request);
            getUrls gurls=new getUrls();
            gurls.article();
            opt=gurls.mode();
            // out.println("tile is "+ maint+"fvdjbv");
            if(url != "")
            {
                HashSet a = gethashset(hsmain);
                List S = gurls.RealatedArticle();
                
        
      out.write("\n");
      out.write("        <fieldset style=\"border: black thick solid ; border-width: 5px;width :700px; position: absolute;top: 1%;left: 13% ;alignment-adjust: middle; margin:0px auto;\">\n");
      out.write("        <H3>");
out.println(maint);
      out.write("</H3>\n");
      out.write("        <textarea readonly name=\"textboxn\" style=\" font-family: cursive; font-size:12pt;height:350px; width:900px\" id=\"textboxn\" >");
out.println(opt);
      out.write("</textarea>\n");
      out.write("        <br>\n");
      out.write("        \n");
      out.write("        <h3>Related articles are : <sub>(select and submit to view article)</sub> </h3> \n");
      out.write("       \n");
      out.write("           \n");
      out.write("        ");

           for(int i=0;i<S.size();i++)
           {
               String p=S.get(i).toString();
               String s = "related " + i ;
                out.println(s);
         
      out.write("\n");
      out.write("         <option value=\"");
      out.print(p);
      out.write("\" > ");
      out.print(p);
      out.write("</option>\n");
      out.write("         ");

           }
            }
           
        
      out.write("\n");
      out.write("        \n");
      out.write("        \n");
      out.write("        <input type=\"text\" name=\"ss\" value=\"");
      out.print(ss);
      out.write("\" hidden>\n");
      out.write("        <button TYPE=submit name=submit Value=\"Submit\"></button>\n");
      out.write("        \n");
      out.write("        </fieldset>\n");
      out.write("    </body>\n");
      out.write("    \n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
