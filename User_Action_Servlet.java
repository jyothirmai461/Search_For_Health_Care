package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class User_Action_Servlet
 */
@WebServlet("/User_Action_Servlet")
public class User_Action_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String location=request.getParameter("location");
		String hospitaltype=request.getParameter("hospitaltype");
		String ambulance=request.getParameter("ambulanceavailability");
        response.setContentType("text/html");  
        out.println("<html><body>");  
        try 
        {  
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/search_for_health_care1", "root", "Jyothi@25");    
            Statement stmt = con.createStatement();  
            ResultSet rs = stmt.executeQuery("select hospital_name,url from hospital_details where "+hospitaltype+"='yes' and hospital_location='"+location+"' and ambulanceavailability='"+ambulance+"'");  
            out.println("<table border=1 width=50% height=50%>");  
            out.println("<tr><th>Hospital Name</th><th>URL</th></tr>");  
            while (rs.next()) 
            {  
                String n = rs.getString("hospital_name");  
                String nm = rs.getString("url");
                String s="<a href='"+nm+"'>"+"Click here"+"</a>";
                out.println("<tr><td>" + n + "</td><td>" + s+"</td></tr>");   
            }  
            out.println("</table>");  
            out.println("</html></body>");  
            con.close();  
           }  
            catch (Exception e) 
           {  
            out.println("error");  
        }  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}