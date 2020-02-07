//doGet( sh.getByIdList(), sh.getAll() )
//doPost

package ncu.im3069.Group18.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

import ncu.im3069.Group18.app.Member;
import ncu.im3069.Group18.app.Service;
import ncu.im3069.Group18.app.ServiceHelper;
import ncu.im3069.Group18.util.DBMgr;
import ncu.im3069.tools.JsonReader;

@WebServlet("/api/service.do")
public class ServiceController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ServiceHelper sh =  ServiceHelper.getHelper();
       
    public ServiceController() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
        String id = jsr.getParameter("id");
        String id_list = jsr.getParameter("id_list");
        System.out.print(id_list);
        
        JSONObject resp = new JSONObject();
        /** 判斷該字串是否存在，若存在代表要取回個別會員之資料，否則代表要取回全部資料庫內會員之資料 */
        if (!id.isEmpty()) {
          JSONObject query = sh.getByIdd(id);
          resp.put("status", "200");
          resp.put("message", "所有商品資料取得成功");
          resp.put("response", query);
        }
        else if (!id_list.isEmpty()) {
          JSONObject query = sh.getByIdList(id_list);
          resp.put("status", "200");
          resp.put("message", "所有購物車之商品資料取得成功");
          resp.put("response", query);
        }
        else {
          JSONObject query = sh.getAll();

          resp.put("status", "200");
          resp.put("message", "所有商品資料取得成功");
          resp.put("response", query);
        }
        
        jsr.response(resp, response);
	}

	//新增服務(但尚未完成！！！！！)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        String name = jso.getString("name");
        int price = jso.getInt("price");
        String image = jso.getString("image");
        String describe = jso.getString("describe");
        String address = jso.getString("address");
        
        /** 建立一個新的會員物件 */
        Service s = new Service(name, price, image, describe, address);
        
        /** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
        if(name.isEmpty() || describe.isEmpty() || address.isEmpty() || price < 0) {
            /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }else{
            /** 透過MemberHelper物件的create()方法新建一個會員至資料庫 */
            JSONObject data = sh.create(s);
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "成功! 新增服務資料...");
            resp.put("response", data);
            
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
	}
	
	public void doDelete(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
	        JsonReader jsr = new JsonReader(request);
	        JSONObject jso = jsr.getObject();
	        
	        /** 取出經解析到JSONObject之Request參數 */
	        int id = jso.getInt("id");
	        
	        /** 透過MemberHelper物件的deleteByID()方法至資料庫刪除該名會員，回傳之資料為JSONObject物件 */
	        JSONObject query = sh.deleteByID(id);
	        
	        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
	        JSONObject resp = new JSONObject();
	        resp.put("status", "200");
	        resp.put("message", "服務刪除成功！");
	        resp.put("response", query);

	        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
	        jsr.response(resp, response);
	    }

	public void doPut(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
	        JsonReader jsr = new JsonReader(request);
	        JSONObject jso = jsr.getObject();
	        
	        /** 取出經解析到JSONObject之Request參數 */
	        int id = jso.getInt("id");
	        String name = jso.getString("name");
	        int price = jso.getInt("price");
	        String describe = jso.getString("describe");
	        String address = jso.getString("address");

	        /** 透過傳入之參數，新建一個以這些參數之會員Member物件 */
	        Service s = new Service(id, name, price, describe, address);
	        
	        /** 透過Member物件的update()方法至資料庫更新該名會員資料，回傳之資料為JSONObject物件 */
	        JSONObject data = sh.update(s);
	        
	        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
	        JSONObject resp = new JSONObject();
	        resp.put("status", "200");
	        resp.put("message", "成功! 更新服務資料...");
	        resp.put("response", data);
	        
	        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
	        jsr.response(resp, response);
	    }
	
	


}
