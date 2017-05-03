<%-- 
    Document   : index
    Created on : 2 May, 2017, 12:08:56 PM
    Author     : sravs
--%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
         <style >
select {list-style: none;padding: 0px;margin: 0px; width: 500px; }
option {list-style: none;padding: 0px;margin: 0px; width: 500px; }
fieldset{ border: black thick solid ; border-width: 5px;width :880px; position: absolute;top: 40%;left: 15% ;alignment-adjust: middle; margin:0px auto;}
body {background-image:url("bg.jpg");background-repeat: no-repeat; background-size: cover}
         </style>
    </head>
 <body  >     
<%@page language="java" import="java.sql.*" %>
<%@page import="de.l3s.boilerpipe.BoilerpipeProcessingException"%>
<%@page import="de.l3s.boilerpipe.extractors.ArticleExtractor"%>
<%@page import="java.io.IOException"%>
<%@page import="java.net.URL"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.beans.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
          

        
        <%!   
       public static String url="",opt;
            public class getUrls
            {
                public ResultSet urls() throws Exception
                {
                    try
                    {
                        // Connecting to the database
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
                    return null;
            }
            }
        
        %>
        <%
            getUrls gurls = new getUrls();
        %>
        <form method="post" action="page2.jsp" >   <!-- Will be directed to page2,jsp after submitting-->     
            <fieldset >
     <span>   
         <font size = 4 > <b> SELECT link:</b></font>
    <select id="urlname" name="urlname"> 
                
    <%
        ResultSet st = gurls.urls(); 
        while(st.next())
        {
            opt = st.getString("link");
            
    %>
    <option value="<%=opt%>" ><%=opt %></option>

    <%
        }
    %>
            </select>
            
            <button TYPE=submit name=submit  Value="Submit"  style="height:20px;width:60px"> Submit </button>
  
            </span>
     </fieldset>
    </form>
            </body>
</html>