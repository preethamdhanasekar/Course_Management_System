package net.coursemanagement.courses;



import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Servlet1")
public class Servlet1 extends HttpServlet {

  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		String course_rating[]= request.getParameterValues("rating");
		String course_category[]= request.getParameterValues("category");
		String course_duration=request.getParameter("duration");
		String course_author=request.getParameter("author");
		String course_popularity=request.getParameter("popularity");
       
	    try {
	    	

	    	Class.forName("com.mysql.jdbc.Driver");
	    	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/coursemanagementsystem","root","Akshu123*");
	    	
	    	
	    	
	    	String rating = "" ;
	    	if(course_rating !=null) {
	    	for(int i=0; i < course_rating.length; ++i) 
	    	{				
				if (course_rating[i] != "")
					{
					  rating+= "'" + course_rating[i] + "',";
					}
	    	}
	    	rating =  rating.substring(0, rating.length() - 1);
	    	 }
  	    	
	     	String category = "";
	     	if(course_category != null ) 
	     	{
		    	for(int j=0; j < course_category.length; ++j) 
		    	{								
						category+= "'" + course_category[j] + "',";						 
		    	}
		    	category =  category.substring(0, category.length() - 1); 
	     	}
			
	    	Statement stmt = con.createStatement();
	    	
	    		
	    	 
	    	String SQLString= "select A.*,B.course_category,B.course_description,B.course_author,B.course_rating,B.course_duration_hrs,B.course_popularity"
	    	+" from course A join course_details B on A.course_id=B.course_id  where 1=1 " ;
	    	
	     	
	    	
	    	if(rating != "")
	    		SQLString=SQLString+" AND (B.course_rating in ("+rating+"))"; 
	    	 
	    	 if(category != "")
	    	 	SQLString=SQLString+" AND (B.course_category in ("+category+"))"; 
	    	 
	    	
	    
	    	
	    	if (!course_duration.equals("0"))
	    		SQLString=SQLString+" AND (B.course_duration_hrs in("+course_duration+"))";
	    	

	    	if(!course_author.equals("0"))
	    		SQLString=SQLString+" AND (B.course_author in('"+course_author+"'))";
	    	
	    	if(!course_popularity.equals("0"))
	    		SQLString=SQLString+" AND (B.course_popularity in('"+course_popularity+"'))";
	    	
	    	
	
	    
	        ResultSet rs=stmt.executeQuery(SQLString);
	        
	        out.println("<html><body><h3>Courses Based On Your Choice</h3><table border='2' style=background-color:lightblue;>");
	        
	        out.println("<tr><td>ID</td><td>Course Name</td><td>Course Category</td><td>Course Description</td>"
	        		+ "<td>Author</td><td>Rating</td><td>Duration</td><td>Popularity</td></tr>");

		    while(rs.next())
		    {
		    	
		    out.println("<tr style=background-color:grey;><td>'"+rs.getString(1)+"'</td><td>'"+rs.getString(2)+"'</td><td>'"+rs.getString(3)+"'</td>"
		    		+ "<td>'"+rs.getString(4)+"'</td><td>'"+rs.getString(5)+"'</td><td>'"+rs.getString(6)+"'</td><td>'"+rs.getString(7)+"'</td><td>'"+rs.getString(8)+"'</td></tr>");
		    	
		   }
		    out.println("</table></body><html>");
		    out.println(" <input type='button' value='Back to Courses' onclick='history.back()'>");
		   
		    }
	    	
	    catch(Exception e)
	    {
	    	 e.printStackTrace();
	    	
	    }
	  
	}
	 public static String removeLastChars(String str, int chars) 
	 {
	        return str.substring(0, str.length() - chars);
	   }
}
