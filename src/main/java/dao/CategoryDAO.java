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

import model.Category;

public class CategoryDAO {

	private Connection db;
    private PreparedStatement ps;
    private ResultSet rs;

    private void connect() throws NamingException, SQLException {
        try {
            Context context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java:comp/env/shop");
            this.db = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

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
    
    //カテゴリーIDとカテゴリー名をListで返すメソッド
    public List<Category> getCategories(){
    	List<Category> categories = new ArrayList<>();
    	
    	try {
            this.connect();

            // SQL文の準備
            ps = db.prepareStatement("SELECT * FROM categories");

            rs = ps.executeQuery();

            while(rs.next()) {
            	int category_id = rs.getInt("category_id");
            	String name = rs.getString("name");
            	categories.add(new Category(category_id,name));
            }
            
        } catch (NamingException | SQLException e) {
            // エラーログの出力やエラーページにリダイレクトなど、適切なエラーハンドリングを行う
            e.printStackTrace();
        } finally {
                this.disconnect();
        }

    	return categories;
    }

}
