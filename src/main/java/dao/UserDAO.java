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

import model.User;

public class UserDAO {
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

    //userlistにusersテーブルのデータを入れる
    public List<User> findAll() {
        List<User> userlist = new ArrayList<>();
        try {
            this.connect();
            ps = db.prepareStatement("SELECT * FROM users");
            rs = ps.executeQuery();
            while (rs.next()) {
                String userid = rs.getString("userid");
                String password = rs.getString("password");
                userlist.add(new User(userid, password));
            }
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        } finally {
            this.disconnect();
        }
        return userlist;
    }

    //ユーザーの登録を行う
    public void registerUser(User user) throws Exception {
        try {
            this.connect();
            ps = db.prepareStatement("INSERT INTO users (userid, password) VALUES (?, ?)");
            ps.setString(1, user.getUserid());
            ps.setString(2, user.getPassword());
            ps.executeUpdate();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            this.disconnect();
        }
    }
    
    //ユーザー認証を行う
    public boolean authenticate(String userid, String password) throws NamingException {
    	List<User> userlist = findAll();
    	
    	for (User user : userlist) {
    		if(user.getUserid().equals(userid) && user.getPassword().equals(password)) {
    			//ユーザー名とパスワードが一致すればtrue
    			return true;
    		}
    	}
    	
    	return false;
    	
    }
}
