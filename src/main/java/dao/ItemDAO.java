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

import model.Item;

public class ItemDAO {
	private Connection db;
	private PreparedStatement ps;
	private ResultSet rs;

	private void connect() throws NamingException, SQLException {
		Context context = new InitialContext();
		DataSource ds = (DataSource) context.lookup("java:comp/env/shop");
		this.db = ds.getConnection();
	}

	private void disconnect()  {
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
	public void ConnectCheck() {
		try {
			this.connect();
			System.out.println("OK");
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		}finally {
			this.disconnect();
		}
	}
	
	public void insertOne(Item item) {
		try {
			this.connect();
			ps = db.prepareStatement("INSERT INTO items(name,price,category_id,stock) VALUES(?,?,?,?)");
			ps.setString(1, item.getName());
			ps.setDouble(2, item.getPrice());
			ps.setInt(3, item.getCategory_ID());
			ps.setInt(4, item.getStock());
			ps.execute();
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
	}

	//リストに商品のデータをIDの順で格納する
	public List<Item> findAll() {
		List<Item> list = new ArrayList<>();
		try {
			this.connect();
			ps = db.prepareStatement("SELECT * FROM items");
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				double price = rs.getDouble("price");
				int category_id = rs.getInt("category_id");
				int stock = rs.getInt("stock");
				list.add(new Item(id, name, price, category_id, stock));
			}
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
		return list;
	}
	
	//引数の方法の順で商品を選択
	public List<Item> Sorting(String orderMethod) {
	    List<Item> list = new ArrayList<>();
	    try {
	        this.connect();

	        // ソート対象のカラムとソートの指定をプレースホルダーに置き換える
	        String query = "SELECT * FROM items ORDER BY price " + orderMethod;
	        ps = db.prepareStatement(query);
	        
	        rs = ps.executeQuery();
	        
	        while (rs.next()) {
	            int id = rs.getInt("id");
	            String name = rs.getString("name");
	            double price = rs.getDouble("price");
	            int category_id = rs.getInt("category_id");
	            int stock = rs.getInt("stock");
	            list.add(new Item(id, name, price, category_id, stock));
	        }
	        System.out.println("並び替えが実行されました:method= "+orderMethod);
	    } catch (NamingException | SQLException e) {
	        e.printStackTrace();
	        System.out.println("並び替えは実行されませんでした");
	    } finally {
	        this.disconnect();
	    }
	    return list;
	}
	
	//Category_idの商品を入れるメソッド
	public List<Item> getItemsByCategory_id(int category_id){
		List<Item> items = new ArrayList<>();
		
		try {
            this.connect();

            // SQL文の準備
            ps = db.prepareStatement("SELECT * FROM items WHERE category_id = ?");
            ps.setInt(1,category_id);

            rs = ps.executeQuery();

            while(rs.next()) {
            	int id = rs.getInt("id");
	            String name = rs.getString("name");
	            double price = rs.getDouble("price");
	            int c = rs.getInt("category_id");
	            int stock = rs.getInt("stock");
	            items.add(new Item(id, name, price, c, stock));
            }
            
        } catch (NamingException | SQLException e) {
            // エラーログの出力やエラーページにリダイレクトなど、適切なエラーハンドリングを行う
            e.printStackTrace();
        } finally {
                this.disconnect();
        }
		return items;
	}
	
	//ItemIDから名前の取得
    public String getNameById(int itemId) {
        String itemName = "null";
        
        try {
            this.connect();
            ps = db.prepareStatement("SELECT name FROM items WHERE id = ?");
            ps.setInt(1, itemId);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                itemName = rs.getString("name");
            }
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }

        return itemName;
    }
    
    //ItemIDからStockを減少させるメソッド
    public void decreaseStock(int ItemID,int quantity) {
        
        try {
            this.connect();
            ps = db.prepareStatement("UPDATE items SET stock = stock - ? WHERE id = ?");
            
            ps.setInt(1,quantity);
            ps.setInt(2, ItemID);
            ps.executeUpdate();
           
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        } finally {
            this.disconnect();
        }
    }
    
}