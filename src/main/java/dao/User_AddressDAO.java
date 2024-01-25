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

import model.User_Address;

public class User_AddressDAO {
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
    
  //ユーザーの住所登録を行う
    public void registerUserAddress(User_Address user_address){

        try {
            this.connect();
            ps = db.prepareStatement("INSERT INTO user_address (userid, Address) VALUES (?, ?)");
            ps.setString(1, user_address.getUserID());
            ps.setString(2, user_address.getAddress());
            ps.executeUpdate();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        } finally {
            this.disconnect();
        }
    }
    
  //住所の認証を行う
    public boolean authenticate(User_Address user_address)
    		throws NamingException {
    	List<User_Address> list = getUser_Address(user_address.getUserID());
    	
    	for (User_Address user : list) {
    		if(user.getUserID().equals(user_address.getUserID()) && 
    				user.getAddress().equals(user_address.getAddress())) {
    			//ユーザー名と住所が一致すればfalse
    			return false;
    		}
    	}
    	
    	return true;
    	
    }
    
    //UserIDから住所を取得
    public List<User_Address> getUser_Address(String UserID){
    	List<User_Address> list = new ArrayList<>();
		try {
			this.connect();
			ps = db.prepareStatement("SELECT * FROM user_address WHERE userid = ?");
			ps.setString(1, UserID);
			rs = ps.executeQuery();
			while (rs.next()) {
				String userid = rs.getString("userid");
				String user_address = rs.getString("Address");
				System.out.println("user_address");
				list.add(new User_Address(userid,user_address));
			}
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
		return list;
	}

}
