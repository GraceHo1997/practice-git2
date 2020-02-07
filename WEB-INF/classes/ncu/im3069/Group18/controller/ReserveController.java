//doGet( oh.getById(), oh.getAll() )
//doPost( sh.getById(), od.addReserveService(), oh.create() )

package ncu.im3069.Group18.controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

import ncu.im3069.Group18.app.Reserve;
import ncu.im3069.Group18.app.ReserveHelper;
import ncu.im3069.Group18.app.Service;
import ncu.im3069.Group18.app.ServiceHelper;
import ncu.im3069.tools.JsonReader;

import javax.servlet.annotation.WebServlet;

@WebServlet("/api/reserve.do")
public class ReserveController extends HttpServlet {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

    /** sh，ServiceHelper 之物件與 Service 相關之資料庫方法（Sigleton） */
    private ServiceHelper sh =  ServiceHelper.getHelper();

    /** oh，ReserveHelper 之物件與 reserve 相關之資料庫方法（Sigleton） */
	private ReserveHelper oh =  ReserveHelper.getHelper();

    public ReserveController() {
        super();
    }

    /**
     * 處理 Http Method 請求 GET 方法（新增資料）
     *
     * @param request Servlet 請求之 HttpServletRequest 之 Request 物件（前端到後端）
     * @param response Servlet 回傳之 HttpServletResponse 之 Response 物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /** 透過 JsonReader 類別將 Request 之 JSON 格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);

        /** 取出經解析到 JsonReader 之 Request 參數 */
        String id = jsr.getParameter("id");

        /** 新建一個 JSONObject 用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();

        /** 判斷該字串是否存在，若存在代表要取回個別訂單之資料，否則代表要取回全部資料庫內訂單之資料 */
        if (!id.isEmpty()) {
          /** 透過 reserveHelper 物件的 getByID() 方法自資料庫取回該筆訂單之資料，回傳之資料為 JSONObject 物件 */
          JSONObject query = oh.getById(id);
          resp.put("status", "200");
          resp.put("message", "單筆訂單資料取得成功");
          resp.put("response", query);
        }
        else {
          /** 透過 reserveHelper 物件之 getAll() 方法取回所有訂單之資料，回傳之資料為 JSONObject 物件 */
          JSONObject query = oh.getAll();
          resp.put("status", "200");
          resp.put("message", "所有訂單資料取得成功");
          resp.put("response", query);
        }

        /** 透過 JsonReader 物件回傳到前端（以 JSONObject 方式） */
        jsr.response(resp, response);
	}

    /**
     * 處理 Http Method 請求 POST 方法（新增資料）
     *
     * @param request Servlet 請求之 HttpServletRequest 之 Request 物件（前端到後端）
     * @param response Servlet 回傳之 HttpServletResponse 之 Response 物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    /** 透過 JsonReader 類別將 Request 之 JSON 格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();

        /** 取出經解析到 JSONObject 之 Request 參數 */
        String date = jso.getString("date");
        String note = jso.getString("note");
        String complete_status = jso.getString("complete_status");
        String name = jso.getString("name");
        String email = jso.getString("email");
        String phone = jso.getString("phone");
        String rating = jso.getString("rating");
        JSONArray item = jso.getJSONArray("item");

        /** 建立一個新的訂單物件 */
        Reserve od = new Reserve(date, note, complete_status, name, email, phone, rating);

        /** 將每一筆訂單細項取得出來 */
        for(int i=0 ; i < item.length() ; i++) {
            String service_id = item.getString(i);

            /** 透過 ServiceHelper 物件之 getById()，取得產品的資料並加進訂單物件裡 */
            Service pd = sh.getById(service_id);
            od.addReserveService(pd);
        }

        /** 透過 reserveHelper 物件的 create() 方法新建一筆訂單至資料庫 */
        JSONObject result = oh.create(od);

        /** 設定回傳回來的訂單編號與訂單細項編號 */
        od.setId((int) result.getLong("reserve_id"));
        od.setReserveServiceId(result.getJSONArray("reserve_service_id"));

        /** 新建一個 JSONObject 用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "訂單新增成功！");
        resp.put("response", od.getReserveAllInfo());

        /** 透過 JsonReader 物件回傳到前端（以 JSONObject 方式） */
        jsr.response(resp, response);
	}

	public void doPut(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
	        JsonReader jsr = new JsonReader(request);
	        JSONObject jso = jsr.getObject();
	        
	        /** 取出經解析到JSONObject之Request參數 */
	        int id = jso.getInt("id");

	        /** 透過傳入之參數，新建一個以這些參數之會員Member物件 */
	        Reserve o = new Reserve(id);
	        
	        /** 透過Member物件的update()方法至資料庫更新該名會員資料，回傳之資料為JSONObject物件 */
	        JSONObject data = oh.update(o);
	        
	        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
	        JSONObject resp = new JSONObject();
	        resp.put("status", "200");
	        resp.put("message", "成功! 確認完成服務...");
	        resp.put("response", data);
	        
	        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
	        jsr.response(resp, response);
	    }
}
