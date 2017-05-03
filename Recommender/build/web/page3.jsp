<%-- 
    Document   : page3
    Created on : 3 May, 2017, 12:04:36 PM
    Author     : sravs
    Extract title and content of selected related article
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.io.LineNumberReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.File"%>
<%@page import="org.jsoup.nodes.Document"%>
<%@page import="org.jsoup.Jsoup"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="de.l3s.boilerpipe.extractors.ArticleExtractor"%>
<%@page import="java.net.URL"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style> body {background-image:url("bg.jpg");background-repeat: no-repeat; background-size: cover}
            button{font-weight: bold;background-color:transparent;color:black;height: 40px;width: 150px;}
            button:hover{font-weight: 900;background-color:transparent;color:black;height: 60px;width: 180px;}
            body {background-image:url("bg.jpg");background-repeat: no-repeat; background-size: cover}
        </style>
        <%!
        public void we(String k,HttpServletRequest request)
        {
                //function to get parameters from index page and store in the newly defined variables
            
            url = request.getParameter("urlname");
         }
        
       public static String url="",opt,text="",title,hse,urlname;
       
            public class getArticle
            {

                public String article() throws Exception
                {
                    
                    try 
                    {
                        URL u=new URL(url);
                        text = ArticleExtractor.INSTANCE.getText(u);
                        Document doc = Jsoup.connect(url).get();
                        title = doc.title();
                    }
                    catch (Exception e )
                    {
                        System.err.println("werfvf"+e.getLocalizedMessage());
                    }                       
                    return text;
                    
            }
            }
        %>
    </head>
    <body>
        <table>
            <tr>
                <td>
        <%
            we("qw",request);
            getArticle ga = new getArticle();
            String art = ga.article();
        %>
        <h3><%=title%></h3>
        <textarea readonly name="textboxn"   id="textboxid" style="font-size:12pt;height:420px;width:900px;"><%out.println(art);%></textarea>
        <br><br>
        
                </td>
            <td>
        <form  method="post" action="index.jsp">
            <button TYPE=submit name=submit Value="Submit">Home Page</button> 
        </form>
            </td>
            <td>
        <form method="post" action="javascript:window.history.back();" >
         <button TYPE=submit name=submit Value="Submit"> Previous Page</button>  
        </form>
            </td>
            </tr>
        </table>
    </body>
</html>
</html>
