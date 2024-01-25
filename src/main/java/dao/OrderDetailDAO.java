package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.OrderDetail;

public class OrderDetailDAO {
	
	private Connection db;
    private PreparedStatement ps;
    private ResultSet rs;

    // データベースへの接続
    private void connect() throws NamingException, SQLException {
        Context context = new InitialContext();
        DataSource ds = (DataSource) context.lookup("java:comp/env/shop");
        this.db = ds.getConnection();
    }

    // データベースからの切断
    private void disconnect() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (db != null) {
                db.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //orderdetailsへのデータ挿入
    public void insertOrderDetails(OrderDetail orderdetail) {
    	try {
            this.connect();
            
            // SQL文の準備
            ps = db.prepareStatement("INSERT INTO orderdetails(ItemID,Quantity,UnitPrice, UserID,Stutas) VALUES(?,?,?,?,?)");
            // パラメータの設定
            ps.setInt(1, orderdetail.getItemID());
            ps.setInt(2, orderdetail.getQuantity());
            ps.setDouble(3, orderdetail.getUnitPrice());
            ps.setString(4, orderdetail.getUserID());
            ps.setString(5, "Pending");
            
            ps.executeUpdate();	
            
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        } finally {
            this.disconnect();
        }
    	
    }
    
    public int getOrder_DetailIDForUncompletedOrderByUserID(String UserID, int ItemID) {
    	int OrderDetailID = 0;
    	
    	try {
            this.connect();
            
            // SQL文の準備
            ps = db.prepareStatement("SELECT OrderDetailID FROM orderdetails WHERE ItemID = ? AND UserID = ? AND Stutas = ?");
            // パラメータの設定
            ps.setInt(1, ItemID);
            ps.setString(2, UserID);
            ps.setString(3, "Pending");
            rs = ps.executeQuery();
            
            if (rs.next()) {
                OrderDetailID = rs.getInt("OrderDetailID");
            }
            
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        } finally {
            this.disconnect();
        }
    	
    	
    	return OrderDetailID;
    }
    
  //StutasをCompleteに変更する
    public void completeStutasAtOrderDetails(String UserID) {
    	
    	try {
            this.connect();
            
            // SQL文の準備
            ps = db.prepareStatement("UPDATE orderdetails SET Stutas = 'Complete' WHERE UserID = ? AND Stutas = 'Pending'");
            // パラメータの設定
            ps.setString(1, UserID);
            ps.executeUpdate();
            
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        } finally {
            this.disconnect();
        }
    	
    }

}
