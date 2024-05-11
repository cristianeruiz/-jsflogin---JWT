package com.mycompany.jsflogin.beans;

/**
 *
 * @author cristiane
 */
import com.mycompany.jsflogin.entity.Item;
import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.json.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

@SessionScoped
@ManagedBean(name = "itemManagedBean")
public class ItemManagedBean  implements Serializable {
	
	private static final long serialVersionUID = -65215454509L;
        
        private List<JSONObject> result;
        
        private String REST_URI = "http://localhost:8088/jsfrest/api";
        
        Client client = ClientBuilder.newClient();
        
        WebTarget webTarget 
  = client.target(REST_URI);
        
        WebTarget employeeWebTarget 
  = webTarget.path("items");
        
        Invocation.Builder invocationBuilder 
  = employeeWebTarget.request(MediaType.APPLICATION_JSON);
        
        //private String BASE_URL="http://localhost:8088/jsfrest/api/";
        
        //WebServiceSeguridad webServiceSeguridad = new WebServiceSeguridad(BASE_URL);

    public List<JSONObject> getResult() {
        return result;
    }

    public void setResult(List<JSONObject> result) {
        this.result = result;
    }

    
	
	private Item item = new Item();
	private List<Item> items;
	
	private int id;
	
	public ItemManagedBean() {
		//ItemDAO itemDAO = new ItemDAO();
		//this.items = itemDAO.selectAll();
                
            /*    String token = "webServiceSeguridad.getToken(data)";
            List<JSONObject> json = webServiceSeguridad.getItems("Bearer ");
            //return items;
            
            setResult(json);
            
            if(json != null){
                json.forEach(obj->{
                
                JsonNode jsonNode = convertJsonFormat(obj);
                ObjectMapper mapper = new ObjectMapper();
                Item myPojo = new Item();
                try {
                    myPojo = mapper.readValue(new TreeTraversingParser(jsonNode), Item.class);
                } catch (IOException ex) {
                    Logger.getLogger(ItemManagedBean.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.items.add(myPojo);
            });
            }*/
            
	}
	
	public List<Item> getItems() {
            
            //List<JSONObject> json = webServiceSeguridad.getItems("Bearer ");
            List<Item> response = invocationBuilder.get(new GenericType<List<Item>>() {
            });
            setItems(response);
            return this.items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public Item getItem() {
		return item;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	public String prepareInsert() throws UnsupportedEncodingException, IOException {	
		this.item = new Item();
                JSONObject json = new JSONObject();
                json.put("username", "mod"); 
                json.put("password", "123"); 
                String token;
                HttpPost post = new HttpPost("http://localhost:8080/api/auth/signin");

                StringEntity params = new StringEntity(json.toString());

            post.setHeader("Content-Type", "application/json");
            post.setEntity(params);

            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                    CloseableHttpResponse response = httpClient.execute(post)) {
                    token = EntityUtils.toString(response.getEntity());
                System.out.println("--------------Tokens--------------------------");                
                System.out.println(token);
                System.out.println("----------------------------------------");
             
            
                JSONObject jsonObj = new JSONObject(token);
                
                Response res = client.target("http://localhost:8080/api/test")
                .path("mod")
                .request(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + jsonObj.getString("accessToken"))
                .get();
            
                System.out.println("--------------accessToken--------------------------");                
                System.out.println("Bearer " + jsonObj.getString("accessToken"));
                System.out.println("----------------------------------------");
                
                String reply = res.readEntity(String.class);
                
            System.out.println("--------------Mensaje--------------------------");                
                System.out.println(reply);
                System.out.println("----------------------------------------");
                
            }
            
            
            
                
		return "insertItem?faces-redirect=true";
	}
	
	public String insert() {
		/*ItemDAO itemDAO = new ItemDAO();
		itemDAO.insert(this.item);
		this.items = itemDAO.selectAll();*/
                
                Response response = client.target("http://localhost:8088/jsfrest/api/items")
      .request(MediaType.APPLICATION_JSON)
      .post(Entity.entity(this.item, MediaType.APPLICATION_JSON));
                
                
		return "index?faces-redirect=true";
	}
        
	/*public String effacer() {
		System.out.println("Effacer");
		return "ok";
	}*/
        
        public String delete(String id) {
                System.out.println("delete");
                
                Response response = client.target("http://localhost:8088/jsfrest/api/items")
                    .path(String.valueOf(id))
      .request(MediaType.APPLICATION_JSON)
      .delete();
                
                return "index?faces-redirect=true";
        }
	
	public String prepareUpdate(String id) {
		//ItemDAO itemDAO = new ItemDAO();
                setId(Integer.parseInt(id));
		this.item = client.target("http://localhost:8088/jsfrest/api/items")
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON)
                .get(Item.class);
                
		return "editItem?faces-redirect=true";
	}
	
	public String update() {
		//ItemDAO itemDAO = new ItemDAO();
		//itemDAO.update(this.item);
		//this.items = itemDAO.selectAll();
            Response response = client.target("http://localhost:8088/jsfrest/api/items")
                    .path(String.valueOf(this.id))
      .request(MediaType.APPLICATION_JSON)
      .put(Entity.entity(this.item, MediaType.APPLICATION_JSON));
		return "index?faces-redirect=true";
	}
        
}	
