//addReserveService
//getReserveServiceFromDB
//getReserveData
//getReserveServiceData
//getReserveAllInfo

package ncu.im3069.Group18.app;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import org.json.*;

public class Reserve {

    /** id，訂單編號 */
    private int id;

    private String date;
    
    private String note;
    
    private String complete_status;
    
    private String name;

    /** email，會員電子郵件信箱 */
    private String email;

    /** phone，會員手機 */
    private String phone;
    
    private String rating;

    /** list，訂單列表 */
    private ArrayList<ReserveItem> list = new ArrayList<ReserveItem>();

    /** osh，ReserveItemHelper 之物件與 Reserve 相關之資料庫方法（Sigleton） */
    private ReserveItemHelper osh = ReserveItemHelper.getHelper();

    /**
     * 實例化（Instantiates）一個新的（new）Reserve 物件<br>
     * 採用多載（overload）方法進行，此建構子用於建立訂單資料時，產生一個新的訂單
     *
     * @param first_name 會員名
     * @param last_name 會員姓
     * @param email 會員電子信箱
     * @param address 會員地址
     * @param phone 會員姓名
     */
    public Reserve(int id) {
    	this.id = id;
    }
    
    public Reserve(String date, String note, String complete_status, String name, String email, String phone, String rating) {
    	this.date = date;
    	this.note = note;
    	this.complete_status = complete_status;
    	this.name = name;
        this.email = email;
        this.phone = phone;
        this.rating = rating;
    }

    /**
     * 實例化（Instantiates）一個新的（new）Reserve 物件<br>
     * 採用多載（overload）方法進行，此建構子用於修改訂單資料時，新改資料庫已存在的訂單
     *
     * @param first_name 會員名
     * @param last_name 會員姓
     * @param email 會員電子信箱
     * @param address 會員地址
     * @param phone 會員姓名
     * @param create 訂單創建時間
     * @param modify 訂單修改時間
     */
    public Reserve(int id, String date, String note, String complete_status, String name, String email, String phone, String rating) {
    	this.id = id;
    	this.date = date;
    	this.note = note;
    	this.complete_status = complete_status;
    	this.name = name;
        this.email = email;
        this.phone = phone;
        this.rating = rating;
        getReserveServiceFromDB();
    }

    /**
     * 新增一個訂單產品及其數量
     */
    
    public void addReserveService(Service sv) {
        this.list.add(new ReserveItem(sv));
    }

    /**
     * 新增一個訂單產品
     */
    
    public void addReserveService(ReserveItem os) {
        this.list.add(os);
    }

    /**
     * 設定訂單編號
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 取得訂單編號
     *
     * @return int 回傳訂單編號
     */
    public int getId() {
        return this.id;
    }

    public String getDate() {
        return this.date;
    }
    
    public String getNote() {
        return this.note;
    }
    
    public String getCompleteStatus() {
        return this.complete_status;
    }

    /**
     * 取得訂單會員的姓名
     *
     * @return String 回傳訂單會員的姓名
     */
    public String getName() {
        return this.name;
    }

    /**
     * 取得訂單信箱
     *
     * @return String 回傳訂單信箱
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * 取得訂單電話
     *
     * @return String 回傳訂單電話
     */
    public String getPhone() {
        return this.phone;
    }
    
    public String getRating() {
        return this.rating;
    }

    /**
     * 取得該名會員所有資料
     *
     * @return the data 取得該名會員之所有資料並封裝於JSONObject物件內
     */
    public ArrayList<ReserveItem> getReserveService() {
        return this.list;
    }

    /**
     * 從 DB 中取得訂單產品
     */
    private void getReserveServiceFromDB() {
        ArrayList<ReserveItem> data = osh.getReserveServiceByReserveId(this.id);
        this.list = data;
    }

    /**
     * 取得訂單基本資料
     *
     * @return JSONObject 取得訂單基本資料
     */
    public JSONObject getReserveData() {
        JSONObject jso = new JSONObject();
        jso.put("id", getId());
        jso.put("date", getDate());
        jso.put("note", getNote());
        jso.put("complete_status", getCompleteStatus());
        jso.put("name", getName());
        jso.put("email", getEmail());
        jso.put("phone", getPhone());
        jso.put("rating", getRating());

        return jso;
    }

    /**
     * 取得訂單產品資料
     *
     * @return JSONArray 取得訂單產品資料
     */
    public JSONArray getReserveServiceData() {
        JSONArray result = new JSONArray();

        for(int i=0 ; i < this.list.size() ; i++) {
            result.put(this.list.get(i).getData());
        }
        return result;
    }

    /**
     * 取得訂單所有資訊
     *
     * @return JSONObject 取得訂單所有資訊
     */
    public JSONObject getReserveAllInfo() {
        JSONObject jso = new JSONObject();
        jso.put("reserve_info", getReserveData());
        jso.put("service_info", getReserveServiceData());

        return jso;
    }

    /**
     * 設定訂單產品編號
     */
    public void setReserveServiceId(JSONArray data) {
        for(int i=0 ; i < this.list.size() ; i++) {
            this.list.get(i).setId((int) data.getLong(i));
        }
    }

}
