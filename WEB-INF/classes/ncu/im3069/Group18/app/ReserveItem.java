//getServiceFromDB
//getService
//getData
//id, sv, quantity, price

package ncu.im3069.Group18.app;

import org.json.JSONObject;

import ncu.im3069.Group18.util.Arith;

public class ReserveItem {

    /** id，產品細項編號 */
    private int id;

    /** sv，產品 */
    private Service sv;

    /** price，產品價格 */
    private int price;

    /** sh，ServiceHelper 之物件與 ReserveItem 相關之資料庫方法（Sigleton） */
    private ServiceHelper sh =  ServiceHelper.getHelper();

    /**
     * 實例化（Instantiates）一個新的（new）ReserveItem 物件<br>
     * 採用多載（overload）方法進行，此建構子用於建立訂單細項時
     *
     * @param sv 會員電子信箱
     * @param quantity 會員密碼
     */
    public ReserveItem(Service sv) {
        this.sv = sv;
        this.price = this.sv.getPrice();
    }

    /**
     * 實例化（Instantiates）一個新的（new）ReserveItem 物件<br>
     * 採用多載（overload）方法進行，此建構子用於修改訂單細項時
     *
     * @param reserve_service_id 訂單產品編號
     * @param reserve_id 會員密碼
     * @param service_id 產品編號
     * @param price 產品價格
     * @param quantity 產品數量
     * @param subtotal 小計
     */
    public ReserveItem(int reserve_service_id, int reserve_id, int service_id, int price) {
        this.id = reserve_service_id;
        this.price = price;
        getServiceFromDB(service_id);
    }

    /**
     * 從 DB 中取得產品
     */
    private void getServiceFromDB(int service_id) {
        String id = String.valueOf(service_id);
        this.sv = sh.getById(id);
    }

    /**
     * 取得產品
     *
     * @return Service 回傳產品
     */
    public Service getService() {
        return this.sv;
    }

    /**
     * 設定訂單細項編號
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 取得訂單細項編號
     *
     * @return int 回傳訂單細項編號
     */
    public int getId() {
        return this.id;
    }

    /**
     * 取得產品價格
     *
     * @return double 回傳產品價格
     */
    public int getPrice() {
        return this.price;
    }


    /**
     * 取得產品細項資料
     *
     * @return JSONObject 回傳產品細項資料
     */
    public JSONObject getData() {
        JSONObject data = new JSONObject();
        data.put("id", getId());
        data.put("service", getService().getData());
        data.put("price", getPrice());
        
        return data;
    }
}
