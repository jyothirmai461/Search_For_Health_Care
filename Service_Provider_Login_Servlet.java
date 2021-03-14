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
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Service_Provider_Login_Servlet
 */
@WebServlet("/Service_Provider_Login_Servlet")
public class Service_Provider_Login_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		String userid=request.getParameter("username");
		String password=request.getParameter("password");
		HttpSession session=request.getSession();
		boolean flag=false;
		//JDBC code for connecting mysql
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/search_for_health_care1","root","Jyothi@25");
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select * from service_provider_login_credentials");
			while(rs.next())
			{
				String x=rs.getString(3);
				if(userid.equals(rs.getString(1)) && password.equals(rs.getString(2)))
				{
					session.setAttribute("user",userid);
					flag=true;
					if(x.equals("accepted"))
					{
						response.sendRedirect("./Service_Provider_Action.html");
					}
					else if(x.equals("rejected"))
					{
						out.print("Sorry! Your request was declined by admin...");
					}
					else
					{
						out.print("Your login request is qued to admin. Try to log in after some time.....");
					}
				}
			}
			if(flag=false)
			{
				out.print("invalid userid or password");
			}
		}
		catch(Exception p)
		{
			out.print(p);
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
