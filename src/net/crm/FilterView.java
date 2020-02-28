package net.crm;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class FilterView implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private List<CustomerEntity> customerList;
    private List<CustomerEntity> filterCustomerList;

    private CustomerEntity selectedCustomer;
    
    @ManagedProperty(value="#{customerContainer}")
    private CustomerContainer customerContainer;
    
    @PostConstruct
    public void init() {
    	try {
			customerList = customerContainer.getCustomerList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public String setCustomer() throws Exception {
		try {
		    Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	
		    int customerId = Integer.valueOf(params.get("customerId"));
		    
		    for(CustomerEntity c : customerList){
		        if( c.getCustomerId()== customerId){
		        	
		        }
		    }
			
		    return "Successfully customer deleted";
		}catch (Exception ex) {
			System.out.println("Customer deleted error -->" + ex.getMessage());
			return "Sorry to delete data.";
		}
	    //System.out.println("In selected Book(), book: " + getSelectedBook());
	}
    
//	public void setContainer(CustomerContainer container) {
//		this.container = container;
//	}
 
//    public boolean filterByPrice(Object value, Object filter, Locale locale) {
//        String filterText = (filter == null) ? null : filter.toString().trim();
//        if (filterText == null || filterText.equals("")) {
//            return true;
//        }
// 
//        if (value == null) {
//            return false;
//        }
// 
//        return ((Comparable) value).compareTo(getInteger(filterText)) > 0;
//    }
 
    public void setCustomerContainer(CustomerContainer customerContainer) {
		this.customerContainer = customerContainer;
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
 
        CustomerEntity customer = (CustomerEntity) value;
        return customer.getFirstName().contains(filterText)
                || customer.getLastName().toLowerCase().contains(filterText)
                || customer.getEmail().toLowerCase().contains(filterText)
                || customer.getEmail().toLowerCase().contains(filterText)
                || customer.getEmail().toLowerCase().contains(filterText)
                || customer.getWorkPhone().toLowerCase().contains(filterText)
                || customer.getMobilePhone().toLowerCase().contains(filterText)
                || customer.getCompany().toLowerCase().contains(filterText)
                || customer.getJobTitle().toLowerCase().contains(filterText)
                || customer.getAddress().toLowerCase().contains(filterText)
                || customer.getCity().toLowerCase().contains(filterText)
                || customer.getState().toLowerCase().contains(filterText)
                || customer.getZipCode().toLowerCase().contains(filterText);
    }

    

	public List<CustomerEntity> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<CustomerEntity> customerList) {
		this.customerList = customerList;
	}

	public List<CustomerEntity> getFilterCustomerList() {
		return filterCustomerList;
	}

	public void setFilterCustomerList(List<CustomerEntity> filterCustomerList) {
		this.filterCustomerList = filterCustomerList;
	}
	
	public CustomerEntity getSelectedCustomer() {
		return selectedCustomer;
	}
	
	public void setSelectedCustomer(CustomerEntity selectedCustomer) {
		this.selectedCustomer = selectedCustomer;
	}

}
