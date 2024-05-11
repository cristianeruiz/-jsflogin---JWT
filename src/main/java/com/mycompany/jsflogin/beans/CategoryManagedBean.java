package com.mycompany.jsflogin.beans;

/**
 *
 * @author cristiane
 */
import com.mycompany.jsflogin.entity.Category;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;


@RequestScoped
@ManagedBean(name = "categoryManagedBean", eager = true)
public class CategoryManagedBean  implements Serializable {
	
	private static final long serialVersionUID = -65215454509L;
        
        private String REST_URI = "http://localhost:8088/jsfrest/api";
        
        Client client = ClientBuilder.newClient();
        
        WebTarget webTarget = client.target(REST_URI);
        WebTarget employeeWebTarget = webTarget.path("categories");
        
        Invocation.Builder invocationBuilder = employeeWebTarget.request(MediaType.APPLICATION_JSON);
	
	private Category category = new Category();
	private List<Category> categories;
	//private List<String> listOfName;
	private static Map<String, Object> listOfName;
	
	private int id;
	
	public CategoryManagedBean() {
		/*CategoryDAO categoryDAO = new CategoryDAO();
		this.categories = categoryDAO.selectAll();
		listOfName = new LinkedHashMap<String, Object>();
		for(Category category : categories) {
			listOfName.put(category.getName(),category.getId()); //label, value
			System.out.println(category.getId() + " - " + category.getName());
		}*/
            List<Category> response = invocationBuilder.get(new GenericType<List<Category>>() {
            });
            
            listOfName = new LinkedHashMap<String, Object>();
		for(Category res : response) {
			listOfName.put(res.getName(), res.getId()); //label, value
			System.out.println(res.getId() + " - " + res.getName());
		}
	}

    public Map<String, Object> getList()
    {
        return listOfName;
    }
	
	public List<Category> getCategories() {                       
            return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	public Category getCategory() {
		return this.category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}	
