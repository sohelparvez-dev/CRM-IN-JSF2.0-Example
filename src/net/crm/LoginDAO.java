package net.crm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

	public static String validate(String user, String password) {
		Connection conn = null;
		PreparedStatement ps = null;
		String SQL = "";

		try {
			//get database connection
			conn = new ConnectionMain().get_mysql_connection();
			
			if(conn==null)
				throw new SQLException("Can't get database connection");
			
			SQL = SQL + "select user_id, username, user_role from users";
			SQL = SQL + " where username = ? and password = ?";
			
			ps  = conn.prepareStatement(SQL); 
			
			ps.setString(1, user);
			ps.setString(2, password);

			ResultSet result = ps.executeQuery();

			if (result.next()) {
				String role = result.getString("user_role");
				return role;
			}

			result.close();
			conn.close();
			
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return "";
		}
		return "";
	}
}
