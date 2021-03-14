package Servlet;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class View_Service_Provider_Requests_Servlet
 */
@WebServlet("/View_Service_Provider_Requests_Servlet")
public class View_Service_Provider_Requests_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();  
        response.setContentType("text/html");  
        out.println("<html><body><form method='post'>");
        try 
        {  
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/search_for_health_care1", "root", "Jyothi@25");    
            Statement stmt = con.createStatement();  
            ResultSet rs = stmt.executeQuery("select * from service_provider_registration_details r, service_provider_login_credentials l where r.Service_Provider_Id=l.serviceprovider_id and l.status1='pending'");  
            out.println("<table border=1 width=50% height=50%>");  
            out.println("<tr><th>Service Id</th><th>Service Provider Name</th><th>Accept</th><th>Reject</th></tr>");
            while (rs.next()) 
            {  
                String n = rs.getString("Service_Provider_Id");
                String nm = rs.getString("First_Name");
                
                String s="<input type='submit' name='Accept' value='Accept' onclick='accept(request,response,n);'>";
                String p="<input type='submit' name='Reject' value='Reject' onclick='reject(request,response,n);'>";
                out.println("<tr><td>" + n + "</td><td>" + nm + "</td><td>"+ s + "</td><td>" + p +"</td></tr>");
                out.println("<a href='Admin_Action.html'>Click here to redirect to admin_action page</a>");
            }  
            out.println("</table>");  
            out.println("</form></body></html>");  
            con.close();  
           }  
            catch (Exception e) 
           {  
            out.println("e");  
        }  
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void reject(HttpServletRequest request, HttpServletResponse response,String id)throws SQLException, IOException {
		PrintWriter out = response.getWriter();
        try 
        {  
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/search_for_health_care", "root", "root");    
            Statement stmt = con.createStatement();  
            stmt.executeUpdate("update service_provider_login_credentials set status1='rejected' where serviceprovider_id='"+id+"'");
        }
        catch(Exception e)
        {
        	out.println(e);
        }
        response.sendRedirect("View_Service_Provider_Requests_Servlet");
    }
	
	private void accept(HttpServletRequest request, HttpServletResponse response,String id)throws SQLException, ServletException, IOException {
		PrintWriter out = response.getWriter();
        try 
        {  
            Class.forName("com.mysql.jdbc.Driver");  
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/search_for_health_care", "root", "root");    
            Statement stmt = con.createStatement();  
            stmt.executeUpdate("update service_provider_login_credentials set status1='accepted' where serviceprovider_id='"+id+"'");
        }
        catch(Exception e)
        {
        	out.println(e);
        }
        response.sendRedirect("View_Service_Provider_Requests_Servlet");
 
    }
}