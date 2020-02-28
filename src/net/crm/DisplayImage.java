package net.crm;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DisplayImage extends HttpServlet {

	/**
	*
	*/
	private static final long serialVersionUID = 4593558495041379082L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String SQL = "";
		try{
		
			String id = request.getParameter("Image_id");
			System.out.println("inside servlet–>"+id);
			

			//get database connection
			Connection conn = new ConnectionMain().get_mysql_connection();
			if(conn==null)
				throw new SQLException("Can't get database connection");
			
			SQL = SQL + "select contract_file ";
			SQL = SQL + "from contract_files where file_id = "+id;

			PreparedStatement ps  = conn.prepareStatement(SQL); 
			
			//get user data from database
			ResultSet result =  ps.executeQuery();
			System.out.println("nside servlet Sql–>"+SQL);
			result=ps.executeQuery(SQL);
			if(result.next()) {
				byte[] bytearray = null;

				Blob blob = result.getBlob("contract_file");
				if(blob != null) {
				       int pos = 1; // position is 1-based
				       int len = (int)blob.length();
				       bytearray = blob.getBytes(pos, len);
				}
				response.reset();
				response.setContentType("image/jpeg");
				response.getOutputStream().write(bytearray);
				response.getOutputStream().close();
			}
			result.close();
			ps.close();
			conn.close();
		
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
