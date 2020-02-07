//os.getService(), os.getPrice()
//getReserveServiceByReserveId()

package ncu.im3069.Group18.app;

import java.sql.*;
import java.util.*;

import org.json.*;

import ncu.im3069.Group18.util.DBMgr;

public class ReserveItemHelper {
    
    private ReserveItemHelper() {
        
    }
    
    private static ReserveItemHelper osh;
    private Connection conn = null;
    private PreparedStatement pres = null;
    
    /**
     * 靜態方法<br>
     * 實作Singleton（單例模式），僅允許建立一個MemberHelper物件
     *
     * @return the helper 回傳MemberHelper物件
     */
    public static ReserveItemHelper getHelper() {
        /** Singleton檢查是否已經有MemberHelper物件，若無則new一個，若有則直接回傳 */
        if(osh == null) osh = new ReserveItemHelper();
        
        return osh;
    }
    
    public JSONArray createByList(long reserve_id, List<ReserveItem> reserveservice) {
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        
        for(int i=0 ; i < reserveservice.size() ; i++) {
            ReserveItem os = reserveservice.get(i);
            
            /** 取得所需之參數 */
            int service_id = os.getService().getID();
            int price = os.getPrice();
            
            try {
                /** 取得資料庫之連線 */
                conn = DBMgr.getConnection();
                /** SQL指令 */
                String sql = "INSERT INTO `missa`.`reserve_service`(`reserve_id`, `service_id`, `price`)"
                        + " VALUES(?, ?, ?)";
                
                /** 將參數回填至SQL指令當中 */
                pres = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pres.setLong(1, reserve_id);
                pres.setInt(2, service_id);
                pres.setDouble(3, price);
                
                /** 執行新增之SQL指令並記錄影響之行數 */
                pres.executeUpdate();
                
                /** 紀錄真實執行的SQL指令，並印出 **/
                exexcute_sql = pres.toString();
                System.out.println(exexcute_sql);
                
                ResultSet rs = pres.getGeneratedKeys();

                if (rs.next()) {
                    long id = rs.getLong(1);
                    jsa.put(id);
                }
            } catch (SQLException e) {
                /** 印出JDBC SQL指令錯誤 **/
                System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
            } catch (Exception e) {
                /** 若錯誤則印出錯誤訊息 */
                e.printStackTrace();
            } finally {
                /** 關閉連線並釋放所有資料庫相關之資源 **/
                DBMgr.close(pres, conn);
            }
        }
        
        return jsa;
    }
    
    public ArrayList<ReserveItem> getReserveServiceByReserveId(int reserve_id) {
        ArrayList<ReserveItem> result = new ArrayList<ReserveItem>();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        ResultSet rs = null;
        ReserveItem os;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `missa`.`reserve_service` WHERE `reserve_service`.`reserve_id` = ?";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, reserve_id);
            
            /** 執行新增之SQL指令並記錄影響之行數 */
            rs = pres.executeQuery();
            
            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                
                /** 將 ResultSet 之資料取出 */
                int reserve_service_id = rs.getInt("id");
                int service_id = rs.getInt("service_id");
                int price = rs.getInt("price");
                
                /** 將每一筆會員資料產生一名新Member物件 */
                os = new ReserveItem(reserve_service_id, reserve_id, service_id, price);
                /** 取出該名會員之資料並封裝至 JSONsonArray 內 */
                result.add(os);
            }
        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(pres, conn);
        }
        
        return result;
    }
}
