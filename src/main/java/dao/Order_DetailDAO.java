package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.Order_Detail;

public class Order_DetailDAO {
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
    
    //order_detailsへのデータ挿入
    public void insertOrder_Details(Order_Detail order_detail) {
    	try {
            this.connect();
            
            // SQL文の準備
            ps = db.prepareStatement("INSERT INTO order_details(OrderID, OrderDetailID) VALUES(?,?)");
            // パラメータの設定
            ps.setString(1, order_detail.getOrderID());
            ps.setInt(2, order_detail.getOrder_DetailID());
            
            ps.executeUpdate();
            
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        } finally {
            this.disconnect();
        }
    }

}
