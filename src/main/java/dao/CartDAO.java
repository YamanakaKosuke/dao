package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.Cart;

public class CartDAO {
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

    // データベース接続の確認（デバッグ用）
    public void connectCheck() {
        try {
            this.connect();
            System.out.println("接続成功");
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        } finally {
            this.disconnect();
        }
    }
    
    //ItemIDからPriceの取得
    public double getPriceById(int itemId) {
        double price = 0.0;
        
        try {
            this.connect();
            ps = db.prepareStatement("SELECT price FROM items WHERE id = ?");
            ps.setInt(1, itemId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                price = rs.getDouble("price");
            }
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        } finally {
			this.disconnect();
		}

        return price;
    }
    
    //UserIDからカートの商品の取得
    public List<Cart> getCartItemsByUserID(String UserID){
    	List<Cart> cartlist = new ArrayList<>();
    	try {
    		this.connect();
			ps = db.prepareStatement("SELECT * FROM carts WHERE UserID = ?");
			ps.setString(1, UserID);
			rs = ps.executeQuery();
			while (rs.next()) {
				int ItemID = rs.getInt("ItemID");
				double Price = rs.getDouble("Price");
				int Quantity = rs.getInt("Quantity");
				
				cartlist.add(new Cart(UserID, ItemID, Price, Quantity));
			}
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
		return cartlist;
    	
    	
    }

    // カートアイテムの挿入
    public void insertCartItem(Cart cart) {
        try {
            this.connect();
            
            if (!isCartItemExist(cart.getUserID(), cart.getItemID())) {
            	// SQL文の準備
                ps = db.prepareStatement("INSERT INTO carts(UserID, ItemID, Price, Quantity) VALUES(?,?,?,?)");
                // パラメータの設定
                ps.setString(1, cart.getUserID());
                ps.setInt(2, cart.getItemID());
                ps.setDouble(3, cart.getPrice());
                ps.setInt(4, cart.getQuantity());
                
                ps.executeUpdate();	
            } else {
                System.out.println("既に同じユーザーとアイテムの組み合わせが存在します。挿入はスキップされました。");
            }
            
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        } finally {
            this.disconnect();
        }
    }
    
    //特定のカートアイテムの削除
    public void deleteCartItem(Cart cart) {
    	try {
    		this.connect();
    		
    		// SQL文の準備
            ps = db.prepareStatement("DELETE FROM carts WHERE UserID = ? AND ItemID = ?");
            ps.setString(1,cart.getUserID());
            ps.setInt(2, cart.getItemID());
    		
            // 実行
            ps.executeUpdate();
    	} catch (NamingException | SQLException e) {
            e.printStackTrace();
        } finally {
            this.disconnect();
        }
    }
    
 // 同じユーザーとアイテムの組み合わせが既に存在するか確認するメソッド
    private boolean isCartItemExist(String userID, int itemID) throws SQLException {
        // SQL文の準備
        PreparedStatement checkPs = db.prepareStatement("SELECT COUNT(*) FROM carts WHERE UserID = ? AND ItemID = ?");
        
        // パラメータの設定
        checkPs.setString(1, userID);
        checkPs.setInt(2, itemID);

        // 結果の取得
        ResultSet resultSet = checkPs.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);

        // 同じユーザーとアイテムの組み合わせが存在するかを返す
        return count > 0;
    }
    
}