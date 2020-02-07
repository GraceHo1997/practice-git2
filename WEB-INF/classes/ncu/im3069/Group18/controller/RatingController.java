//doPost(rh.create)
//doGet(rh.getAll)(rh.getByID)

package ncu.im3069.Group18.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.*;

import ncu.im3069.Group18.app.Rating;
import ncu.im3069.Group18.app.RatingHelper;
import ncu.im3069.tools.JsonReader;

@WebServlet("/api/rating.do")
public class RatingController extends HttpServlet {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** rh，RatingHelper之物件與Rating相關之資料庫方法（Sigleton） */
    private RatingHelper rh =  RatingHelper.getHelper();
    
    /**
     * 處理Http Method請求POST方法（新增資料）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        int star = jso.getInt("star");
        String description = jso.getString("description");
        int service_id = jso.getInt("service_id");
        String service_name = jso.getString("service_name");
        int reserve_id = jso.getInt("reserve_id");

        Rating r = new Rating(star, description, service_id, service_name, reserve_id);
        
        /** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
        if( description.isEmpty()) {
            /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }else {
            /** 透過MemberHelper物件的create()方法新建一個評價至資料庫 */
            JSONObject data = rh.create(r);
            rh.updateRating(r);
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "成功! 新增評價...");
            resp.put("response", data);
            
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
    }

    /**
     * 處理Http Method請求GET方法（取得資料）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        
        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
        String id = jsr.getParameter("id");
        
        /** 判斷該字串是否存在，若存在代表要取回個別評價之資料，否則代表要取回全部資料庫內評價之資料 */
        if (id.isEmpty()) {
            /** 透過RatingHelper物件之getAll()方法取回所有評價之資料，回傳之資料為JSONObject物件 */
            JSONObject query = rh.getAll();
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "所有評價資料取得成功");
            resp.put("response", query);
            System.out.print(resp);
            
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
        else {
            /** 透過RatingHelper物件的getByID()方法自資料庫取回該名評價之資料，回傳之資料為JSONObject物件 */
            JSONObject query = rh.getByID(id);
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "評價資料取得成功");
            resp.put("response", query);
            System.out.print(resp);
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
            JSONObject query = rh.deleteByID(id);
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "評價刪除成功！");
            resp.put("response", query);

            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }

}