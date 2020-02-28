package net.crm;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SuppressWarnings("serial")
@ManagedBean(name="user")
@SessionScoped
public class UserBean implements Serializable {
			
		public List<User> getUserList() throws SQLException{
			
			//get database connection
			Connection conn = new ConnectionMain().get_mysql_connection();
			
			if(conn==null)
				throw new SQLException("Can't get database connection");
			
			PreparedStatement ps  = conn.prepareStatement("select user_id, username, password, user_role from users"); 
			
			//get user data from database
			ResultSet result =  ps.executeQuery();
			
			List<User> list = new ArrayList<User>();
			
			while(result.next()){
				User user = new User();
				
				user.setUserId(result.getInt("user_id"));
				user.setUsername(result.getString("username"));
				user.setPassword(result.getString("password"));
				user.setUserRole(result.getString("user_role"));
				
				//store all data into a List
				list.add(user);
			}
			result.close();

			conn.close();
				
			return list;
		}

}
