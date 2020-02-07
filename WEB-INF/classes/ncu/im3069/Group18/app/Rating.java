
package ncu.im3069.Group18.app;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import org.json.*;

public class Rating {

    /** id，評價編號 */
    private int id;

    /** create，評價創建時間 */
    private Timestamp create;
    
    private int star;
    
    private String description;
    
    private int service_id;
    
    private String service_name;
    
    private int reserve_id;

    private RatingHelper rh =  RatingHelper.getHelper();
    
    public Rating(int star, String description) {
    	this.star = star;
    	this.description = description;
    }
    
    
    public Rating(int star, String description, int service_id, String service_name, int reserve_id) {
    	this.star = star;
    	this.description = description;
    	this.service_id = service_id;
        this.service_name = service_name;
        this.reserve_id = reserve_id;
    }

    public Rating(int id, Timestamp create, int star, String description, int service_id, String service_name, int reserve_id) {
    	this.id = id;
    	this.create = Timestamp.valueOf(LocalDateTime.now());
    	this.star = star;
    	this.description = description;
    	this.service_id = service_id;
        this.service_name = service_name;
        this.reserve_id = reserve_id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }

    public Timestamp getCreateTime() {
        return this.create;
    }
    
    public int getStar() {
        return this.star;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public int getServiceId() {
        return this.service_id;
    }
    
    public String getServiceName() {
        return this.service_name;
    }
    
    public int getReserveId() {
        return this.reserve_id;
    }
    
    public JSONObject getRatingData() {
    	
        JSONObject jso = new JSONObject();
        jso.put("id", getId());
        jso.put("create", getCreateTime());
        jso.put("star", getStar());
        jso.put("description", getDescription());
        jso.put("service_id", getServiceId());
        jso.put("service_name", getServiceName());
        jso.put("reserve_id", getReserveId());

        return jso;
    }


}
