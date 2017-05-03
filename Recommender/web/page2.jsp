<%-- 
    Document   : page2
    Created on : 2 May, 2017, 12:37:36 PM
    Author     : sravs
--%>

<%@page import="org.jsoup.Jsoup"%>
<%@page import="org.jsoup.nodes.Document"%>
<%@page import="de.l3s.boilerpipe.extractors.ArticleExtractor"%>
<%@page import="java.net.URL"%>
<%@page import="de.l3s.boilerpipe.BoilerpipeDocumentSource.*"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       <title>JSP Page</title>
        <style >
select {list-style: none;padding: 0px;margin: 0px; width: 830px; }
option {list-style: none;padding: 0px;margin: 0px; width: 830px; }
body {background-image:url("bg.jpg");background-repeat: no-repeat; background-size: cover}

         </style>
       
    <%!
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
                     while(st1.hasMoreTokens())     //Removing stopwords
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
                           text = ArticleExtractor.INSTANCE.getText(u);     //Get content of article
                        }
                        catch (Exception e )
                        {
                            System.err.println(e.getLocalizedMessage());
                        }
                        
                        return text;
                }
                
                public void article() throws Exception      //Selects hashset and title of link
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
                
                public List RealatedArticle()       // Function for finding the related articles
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
                            // Comp(A,B) =( A_content ∩ B_content / A_content U B_content ) + (A_title ∩ B_title / max(A_title , B_title )
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
                                
                                comp=comp+tcomp;
                                System.out.println(comp);
                                if(comp > 100)      // Threshold is fixed
                                {
                                    comparison.add(comp);
                                    Collections.sort(comparison);
                                    Collections.reverse(comparison);
                                    int p;
                                    p=comparison.indexOf(comp);
                                    urls.add(p,st.getString("link"));
                                }
                            }
                        }
                        catch(Exception e) { }
                    }
                    catch(Exception e){}
                                  return urls;
                        }
                    
        }
        %>
        </head>
        <body>
        
        <%
           
            we("1",request);
            getUrls gurls=new getUrls();
            gurls.article();
            opt=gurls.mode();
            if(url != "")
            {
                HashSet a = gethashset(hsmain);
                List S = gurls.RealatedArticle();
                
        %>
        <form method="post" action="page3.jsp" >
        <fieldset style="border: black thick solid ; border-width: 5px;width :700px; position: absolute;top: 1%;left: 13% ;alignment-adjust: middle; margin:0px auto;">
            <H3><%out.println(maint);%></H3>
            <textarea readonly name="textboxn" style=" font-family: cursive; font-size:12pt;height:350px; width:900px" id="textboxn" ><%out.println(opt);%></textarea>
        
            <span>  
        
        
        <br>
        
        <h3>Related articles are : <sub>(select and submit to view article)</sub> </h3> 
        <select id="urlname" name="urlname"> 
           
        <%
           for(int i=0;i<S.size();i++)
           {
               String p=S.get(i).toString();
               
         %>
         <option value="<%=p%>" > <%=p%></option>
         <%
           }
            }
           
        %>
        </select>
        
        <button TYPE=submit name=submit  Value="Submit"  style="height:20px;width:60px"> Submit </button>
        </span>
        </fieldset>
        </form>
    </body>
    
</html>