package com.mycompany.jsflogin.client;

import com.mycompany.jsflogin.entity.Item;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author cristiane
 */
public class ItemClient {
    
    private static final String REST_URI = "http://localhost:8088/jsfrest/api/items";
    private Client client = ClientBuilder.newClient();

    public Response createJsonEmployee(Item itm) {
        return client.target(REST_URI).request(MediaType.APPLICATION_JSON).post(Entity.entity(itm, MediaType.APPLICATION_JSON));
    }

    public Item getJsonItem(int id) {
        return client.target(REST_URI).path(String.valueOf(id)).request(MediaType.APPLICATION_JSON).get(Item.class);
    }

    public List<Item> getJsonItems(int id) {
        return client.target(REST_URI)
                .path("items")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Item>>() {
            });
    }
    
    /*
    public Response createXmlEmployee(Employee emp) {
        return client.target(REST_URI).request(MediaType.APPLICATION_XML).post(Entity.entity(emp, MediaType.APPLICATION_XML));
    }

    public Employee getXmlEmployee(int id) {
        return client.target(REST_URI).path(String.valueOf(id)).request(MediaType.APPLICATION_XML).get(Employee.class);
    }*/
    
}
