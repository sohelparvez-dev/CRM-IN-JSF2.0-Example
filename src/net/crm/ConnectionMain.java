package net.crm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionMain {
	
	private String class_name = "com.dbmanager.ConnectionMain";

	public Connection get_mysql_connection() {
		String method_name = "get_mysql_connection";
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			Global.LogServiceErrors(class_name, method_name, e.getMessage());
		}

		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://" + Global.DB_Server
					+ "/" + Global.DB_Name, net.crm.Global.DB_User, Global.DB_Password);
		} catch (SQLException e) {
			Global.LogServiceErrors(class_name, method_name, e.getMessage());
		}
		
		return conn;
	}
}
