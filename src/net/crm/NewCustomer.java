package net.crm;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

@ManagedBean
@RequestScoped
public class NewCustomer {

	private int customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String workPhone;
    private String mobilePhone;
    private String company;
    private String jobTitle;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String message;
    private UploadedFile file;
    private List<Contract> imageContent;
    
    public NewCustomer() {
    	imageContent = new ArrayList<Contract>();  
    	
    }
    
    // Get All Customers 
    public String loadCustomer() throws SQLException {
		try {
		    Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	
		    int customerId = Integer.valueOf(params.get("customerId"));
		    
			    
			    System.out.println(customerId);
			    
			    Connection conn = new ConnectionMain().get_mysql_connection();
				String SQL = "";
				PreparedStatement ps = null;
				if(conn==null)
					throw new SQLException("Can't get database connection");

				SQL = " select customer_id, first_name, last_name, email, work_phone, mobile_phone, ";
				SQL = SQL + " company, job_title, address, city, state, zip ";
				SQL = SQL + " from customers where customer_id = ?";
			    ps  = conn.prepareStatement(SQL); 
			    
			    ps.setInt(1, customerId);

				//get customer data from database
				ResultSet result =  ps.executeQuery();
				
				while(result.next()){
					
					this.customerId = result.getInt("customer_id");
					this.firstName = result.getString("first_name");
					this.lastName = result.getString("last_name");
					this.email = result.getString("email");
					this.workPhone = result.getString("work_phone");
					this.mobilePhone = result.getString("mobile_phone");
					this.company = result.getString("company");
					this.jobTitle = result.getString("job_title");
					this.address = result.getString("address");
					this.city = result.getString("city");
					this.state = result.getString("state");
					this.zipCode = result.getString("zip");
				}
			
				// Update data all fields jsf page 
				//RequestContext.getCurrentInstance().update("form:contractsimg");
				RequestContext.getCurrentInstance().update("form:customerid");
				RequestContext.getCurrentInstance().update("form:firstname");
				RequestContext.getCurrentInstance().update("form:lastname");
				RequestContext.getCurrentInstance().update("form:email");
				RequestContext.getCurrentInstance().update("form:workphone");
				RequestContext.getCurrentInstance().update("form:mobilephone");
				RequestContext.getCurrentInstance().update("form:company");
				RequestContext.getCurrentInstance().update("form:jobtitle");
				RequestContext.getCurrentInstance().update("form:address");
				RequestContext.getCurrentInstance().update("form:city");
				RequestContext.getCurrentInstance().update("form:state");
				RequestContext.getCurrentInstance().update("form:zip");
				

				SQL = " select file_id, contract_name ";
				SQL = SQL + " from contract_files where customer_id = ?";
			    ps  = conn.prepareStatement(SQL); 
			    
			    ps.setInt(1, customerId);

				//get customer data from database
				ResultSet rs2 =  ps.executeQuery();
				List<Contract> images_info = new ArrayList<Contract>();
				while(rs2.next()){
					Contract contract = new Contract();
					contract.setContractName(rs2.getString("contract_name"));
					contract.setContractFileId(rs2.getInt("file_id"));
					images_info.add(contract);
				}
				this.setImageContent(images_info);
				RequestContext.getCurrentInstance().update("form:contractsimg");
				
				ps.close();
				conn.close();
				return "setcustomer ok";
			} catch (SQLException ex) {
				System.out.println("Customer loading error -->" + ex.getMessage());
				return ex.getMessage();
			} catch (Exception ex) {
				System.out.println("Customer loading error -->" + ex.getMessage());
				return ex.getMessage();
			}
	}
    
    public void resetForm() {

		this.customerId = 0;
		this.firstName = "";
		this.lastName = "";
		this.email = "";
		this.workPhone = "";
		this.mobilePhone = "";
		this.company = "";
		this.jobTitle = "";
		this.address = "";
		this.city = "";
		this.state = "";
		this.zipCode = "";
		
		RequestContext.getCurrentInstance().update("form:customerid");
		RequestContext.getCurrentInstance().update("form:firstname");
		RequestContext.getCurrentInstance().update("form:lastname");
		RequestContext.getCurrentInstance().update("form:email");
		RequestContext.getCurrentInstance().update("form:workphone");
		RequestContext.getCurrentInstance().update("form:mobilephone");
		RequestContext.getCurrentInstance().update("form:company");
		RequestContext.getCurrentInstance().update("form:jobtitle");
		RequestContext.getCurrentInstance().update("form:address");
		RequestContext.getCurrentInstance().update("form:city");
		RequestContext.getCurrentInstance().update("form:state");
		RequestContext.getCurrentInstance().update("form:zip");
    }
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWorkPhone() {
		return workPhone;
	}
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}	
	
	public List<Contract> getImageContent() {
		return imageContent;
	}

	public void setImageContent(List<Contract> imageContent) {
		this.imageContent = imageContent;
	}

	// Delete customer
	public String deleteCustomer() throws SQLException {
		try {
		    Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	
		    int customerId = Integer.valueOf(params.get("customerId"));
		    
		    System.out.println(customerId);
		    
		    Connection conn = new ConnectionMain().get_mysql_connection();
			String SQL = "";
			PreparedStatement ps = null;
			if(conn==null)
				throw new SQLException("Can't get database connection");
			SQL = "delete from contract_files where customer_id = ?";
		    ps  = conn.prepareStatement(SQL); 
		    ps.setInt(1, customerId);
			ps.execute();

			SQL = "delete from customers where customer_id = ?";
		    ps  = conn.prepareStatement(SQL); 
		    ps.setInt(1, customerId);
			ps.execute();
			
			ps.close();
			conn.close();

			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
		    
		    return "Successfully customer deleted";
		} catch (SQLException ex) {
			System.out.println("Customer deleted error -->" + ex.getMessage());
			return "Sorry to delete data.";
		} catch (Exception ex) {
			System.out.println("Customer deleted error -->" + ex.getMessage());
			return "Sorry to delete data.";
		}
	}
	
	public String addCustomer() throws SQLException, IOException{
		try {
			/*
			 * InputStream input = null; if(file != null) { //input = file.getInputstream();
			 * }
			 */
			InputStream iss = file.getInputstream();
			List<InputStream> files = new ArrayList<InputStream>();
			List<String> fnames = new ArrayList<String>();
			File folder = new File("/home/sohel/crm");
			for (final File file : folder.listFiles()) {
		        if (!file.isDirectory()) {
		        	BufferedImage bfi = ImageIO.read(file);

		        	ByteArrayOutputStream baos = new ByteArrayOutputStream();
		        	ImageIO.write(bfi, "png", baos);
		        	InputStream is = new ByteArrayInputStream(baos.toByteArray());
		        	files.add(is);
		        	fnames.add(FilenameUtils.removeExtension(file.getName())+".png");
		        	file.delete();
		        }
		    } 
	        
			Connection conn = new ConnectionMain().get_mysql_connection();
			String SQL = "";
			PreparedStatement ps = null;
			if(conn==null)
				throw new SQLException("Can't get database connection");

			if(customerId == 0) {

				SQL = "insert into customers (first_name, last_name, email, work_phone, mobile_phone,";
				SQL = SQL + "company, job_title, address, city, state, zip) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				
			    ps  = conn.prepareStatement(SQL); 
			    
			    ps.setString(1, firstName);
			    ps.setString(2, lastName);
			    ps.setString(3, email);
			    ps.setString(4, workPhone);
			    ps.setString(5, mobilePhone);
			    ps.setString(6, company);
			    ps.setString(7, jobTitle);
			    ps.setString(8, address);
			    ps.setString(9, city);
			    ps.setString(10, state);
			    ps.setString(11, zipCode);
			} else {

				SQL = "update customers set first_name = ?, last_name = ?, email = ?, work_phone = ?, mobile_phone = ?,";
				SQL = SQL + "company = ?, job_title = ?, address = ?, city = ?, state = ?, zip = ? ";
				SQL = SQL +" where customer_id = ?";
				
			    ps  = conn.prepareStatement(SQL); 
			    
			    ps.setString(1, firstName);
			    ps.setString(2, lastName);
			    ps.setString(3, email);
			    ps.setString(4, workPhone);
			    ps.setString(5, mobilePhone);
			    ps.setString(6, company);
			    ps.setString(7, jobTitle);
			    ps.setString(8, address);
			    ps.setString(9, city);
			    ps.setString(10, state);
			    ps.setString(11, zipCode);
			    ps.setInt(12, customerId);
				
			}
			ps.execute();
			
			SQL = "select max(customer_id) as customer_id from customers ";
		    ps  = conn.prepareStatement(SQL); 

			ResultSet result =  ps.executeQuery();
			int new_customer_id = 0;
			while(result.next()){
				new_customer_id = result.getInt("customer_id");
			}
			
			int f = 0;
			for(InputStream is: files) {
				SQL = "insert into contract_files (customer_id, contract_file, contract_name) values ("+new_customer_id+", '"+is+"', '"+ fnames.get(f) +"') ";
			    ps  = conn.prepareStatement(SQL); 
			    ps.execute();
			    ++f;
			}
			
			result.close();
			ps.close();
			conn.close();
			//RequestContext.getCurrentInstance().update("customerTable");
			message = "Successfully saved customer.";
			resetForm();
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
			return message;

		} catch (SQLException ex) {
			System.out.println("Customer added error -->" + ex.getMessage());
			return "Sorry to saved data.";
		}
	}
	
	/*
	 * public void handleFileUpload(FileUploadEvent event) { UploadedFile
	 * uploadedFile = (UploadedFile)event.getFile(); try {
	 * file.add(event.getFile()); setFile(file);
	 * 
	 * } catch (Exception e) {
	 * 
	 * } }
	 */
	//<!-- onConfirmComplete="#{newCustomer.reload}" -->
//	public void reload() throws IOException {
//	    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//	    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
//	}
	

//	<!-- <p:fileUpload fileUploadListener="#{newCustomer.handleFileUpload}"  mode="single"/> -->
//    <!-- <p:fileUpload fileUploadListener="#{newCustomer.handleFileUpload}"  mode="single"/> -->
//    
//	<!-- <ui:repeat value="#{newCustomer.imageContent}" var="img">
//		<h1>img</h1>
//    	<h:graphicImage height="50px" width="50px"  value="#{img}">
//			</h:graphicImage>
//    </ui:repeat> -->
//    <!-- <p:galleria value="#{newCustomer.imageContent}" var="image" panelWidth="300" panelHeight="200" showCaption="true">
//    	<h:outputText value="{image}"></h:outputText>
//    	<p:graphicImage value="#{image}" alt="Image Description for #{image}" title="#{image}" />
//    	<h:graphicImage height="50px" width="50px"  value="#{image}">
//			</h:graphicImage>
//	</p:galleria> -->
//	<!-- <h:message for="zip"></h:message> -->

}