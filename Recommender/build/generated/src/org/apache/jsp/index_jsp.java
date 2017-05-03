package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.sql.*;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.beans.Statement;
import java.sql.DriverManager;
import java.sql.Connection;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

   
       public static String url="",opt;
            public class getUrls
            {
                public ResultSet urls() throws Exception
                {
                    try
                    {
                        Class.forName("org.postgresql.Driver");
                        Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/ProjectData","sravani", "1234");
                        String select="select link from link_data_info";
                       
                        try
                        {
                            
                            PreparedStatement preparedStatement = con.prepareStatement(select);
                            ResultSet st = preparedStatement.executeQuery();
                            return st;
                        }
                        catch(Exception e)
                        {           
                            System.out.println("statement : "+ e);
                        }
                    }
                    catch(Exception e)
                    { 
                        System.out.println("Connection : "+ e);
                    }
                    System.out.print("null return");
                    return null;
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
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>JSP Page</title>\n");
      out.write("         <style >\n");
      out.write("select {list-style: none;padding: 0px;margin: 0px; width: 500px; }\n");
      out.write("option {list-style: none;padding: 0px;margin: 0px; width: 500px; }\n");
      out.write("fieldset{ border: black thick solid ; border-width: 5px;width :880px; position: absolute;top: 40%;left: 15% ;alignment-adjust: middle; margin:0px auto;}\n");
      out.write("body {background-image:url(\"bg.jpg\");background-repeat: no-repeat; background-size: cover}\n");
      out.write("         </style>\n");
      out.write("    </head>\n");
      out.write(" <body  >     \n");
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
      out.write("          \n");
      out.write("\n");
      out.write("        \n");
      out.write("        ");
      out.write("\n");
      out.write("        ");

            getUrls gurls = new getUrls();
        
      out.write("\n");
      out.write("        <form method=\"post\" action=\"page2.jsp\" >        \n");
      out.write("            <fieldset >\n");
      out.write("     <span>   \n");
      out.write("         <font size = 4 > <b> SELECT link:</b></font>\n");
      out.write("    <select id=\"urlname\" name=\"urlname\"> \n");
      out.write("                \n");
      out.write("    ");

        ResultSet st = gurls.urls(); 
        while(st.next())
        {
            opt = st.getString("link");
            
    
      out.write("\n");
      out.write("    <option value=\"");
      out.print(opt);
      out.write("\" >");
      out.print(opt );
      out.write("</option>\n");
      out.write("\n");
      out.write("    ");

        }
    
      out.write("\n");
      out.write("            </select>\n");
      out.write("            \n");
      out.write("            <button TYPE=submit name=submit  Value=\"Submit\"  style=\"height:20px;width:60px\"> Submit </button>\n");
      out.write("  \n");
      out.write("            </span>\n");
      out.write("     </fieldset>\n");
      out.write("    </form>\n");
      out.write("            </body>\n");
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
