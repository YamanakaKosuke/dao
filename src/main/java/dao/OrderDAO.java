package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.Cart;
import model.Order;

public class OrderDAO {
	
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
    
    //ordersテーブルへの挿入
    public void insertOrders(List<Cart> cartItems,
    		String deliveryLocation, String paymentmethod) {
    	
    	try {
    		double TotalAmount = 0;
    		String OrderID = generateRandomID(10);
    		String UserID = "";
    		String Address = deliveryLocation;
    		
    		if(cartItems != null && cartItems.size() > 0) {
    			UserID = cartItems.get(0).getUserID();
        		for(Cart cartitem : cartItems) {
            		TotalAmount += cartitem.getPrice() * cartitem.getQuantity();
        		}
        	}
    		
            this.connect();
            
            LocalDateTime currentDateTime = LocalDateTime.now();
            Timestamp orderDateTimestamp = Timestamp.valueOf(currentDateTime);
            Date OrderDate = new Date(orderDateTimestamp.getTime());
            
            
            // SQL文の準備
            ps = db.prepareStatement("INSERT INTO orders(OrderID,UserID,OrderDate,TotalAmount,Address,payment_method,Stutas) VALUES(?,?,?,?,?,?,?)");
            // パラメータの設定
            ps.setString(1, OrderID);
            ps.setString(2, UserID);
            ps.setDate(3, OrderDate);
			ps.setDouble(4, TotalAmount);
			ps.setString(5, Address);
			ps.setString(6, paymentmethod);
            ps.setString(7, "Pending");//未完了としてデータが挿入される
			
            ps.executeUpdate();
            
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        } finally {
            this.disconnect();
        }
    	
    }
    
    //UserIDからOrderIDを取得
    public String getOrderIDByUserID(String UserID) {
    	String OrderID="null";
    	
    	try {
            this.connect();
            
            // SQL文の準備
            ps = db.prepareStatement("SELECT OrderID FROM orders WHERE UserID = ?");
            // パラメータの設定
            ps.setString(1, UserID);
            ps.executeQuery();
            
            if (rs.next()) {
                OrderID = rs.getString("OrderID");
            }
            
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        } finally {
            this.disconnect();
        }
    	
    	return OrderID;
    }
    
    //UserIDから未完了のorderのOrderIDを取得
    public String getOrderIDForUncompletedOrderByUserID(String userID) {
        String OrderID = "";

        try {
            this.connect();

            // SQL文の準備
            ps = db.prepareStatement("SELECT OrderID FROM orders WHERE UserID = ? AND Stutas = ?");
            // パラメータの設定
            ps.setString(1, userID);
            ps.setString(2, "Pending");
            rs = ps.executeQuery();

            if (rs.next()) {
                OrderID = rs.getString("OrderID");
            }
            
        } catch (NamingException | SQLException e) {
            // エラーログの出力やエラーページにリダイレクトなど、適切なエラーハンドリングを行う
            e.printStackTrace();
        } finally {
                this.disconnect();
        }

        return OrderID;
    }


    
    //StutasをCompleteに変更する
    public void completeStatusAtOrders(String UserID) {
    	
    	try {
            this.connect();
            
            // SQL文の準備
            ps = db.prepareStatement("UPDATE orders SET Stutas = 'Complete' WHERE UserID = ? AND Stutas = 'Pending'");
            // パラメータの設定
            ps.setString(1, UserID);
            ps.executeUpdate();
            
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        } finally {
            this.disconnect();
        }
    	
    }
    
    //Stutasが"Complete"の注文の日付を取得
    public List<Order> getCompleteOrderByUserID(String UserID) {
    	List<Order> orders = new ArrayList<>();

        try {
            this.connect();

            // SQL文の準備
            ps = db.prepareStatement("SELECT * FROM orders WHERE UserID = ? AND Stutas = ?");
            // パラメータの設定
            ps.setString(1, UserID);
            ps.setString(2, "Complete");
            rs = ps.executeQuery();

            while(rs.next()) {
            	String orderid = rs.getString("OrderID");
            	String userid = rs.getString("UserID");
            	Date orderdate = rs.getDate("OrderDate");
            	double totalamount = rs.getDouble("TotalAmount");
            	String address = rs.getString("Address");
            	String payment_method = rs.getString("payment_method");
            	String stutas = rs.getString("Stutas");
            	orders.add(new Order(orderid,userid,orderdate,totalamount,address,payment_method,stutas));
            }
            
        } catch (NamingException | SQLException e) {
            // エラーログの出力やエラーページにリダイレクトなど、適切なエラーハンドリングを行う
            e.printStackTrace();
        } finally {
                this.disconnect();
        }

        return orders;
    }
    
    private String generateRandomID(int n) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < n; i++) {
            int digit = random.nextInt(n); // 0から9の範囲のランダムな数字を生成
            stringBuilder.append(digit);
        }

        return stringBuilder.toString();
    }

}
