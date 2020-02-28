package net.crm;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "customerContainer", eager = true)
@ApplicationScoped
public class CustomerContainer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public List<CustomerEntity> getCustomerList() throws SQLException{
		
		try {
			//get database connection
			Connection conn = new ConnectionMain().get_mysql_connection();
			String SQL = "";
			if(conn==null)
				throw new SQLException("Can't get database connection");
			
			SQL = SQL + "select customer_id, first_name, last_name, email, work_phone, mobile_phone,";
			SQL = SQL + "company, job_title, address, city, state, zip ";
			SQL = SQL + "from customers";
			
			PreparedStatement ps  = conn.prepareStatement(SQL); 
			
			//get user data from database
			ResultSet result =  ps.executeQuery();
			
			List<CustomerEntity> list = new ArrayList<CustomerEntity>();
			
			while(result.next()){
				CustomerEntity customer = new CustomerEntity();
				
				customer.setCustomerId(result.getInt("customer_id"));
				customer.setFirstName(result.getString("first_name"));
				customer.setLastName(result.getString("last_name"));
				customer.setEmail(result.getString("email"));
				customer.setWorkPhone(result.getString("work_phone"));
				customer.setMobilePhone(result.getString("mobile_phone"));
				customer.setCompany(result.getString("company"));
				customer.setJobTitle(result.getString("job_title"));
				customer.setAddress(result.getString("address"));
				customer.setCity(result.getString("city"));
				customer.setState(result.getString("state"));
				customer.setZipCode(result.getString("zip"));
				
				//String imageString= new String(Base64.encodeBase64(byte array fetched from database));
				//customer.setContracts(contracts);
				
				//store all data into a List
				list.add(customer);
			}
			result.close();
			conn.close();
				
			return list;
	
		} catch (SQLException ex) {
			System.out.println("Customer added error -->" + ex.getMessage());
			return null;
		}
	}
}
