package controller;

import java.io.IOException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.User_AddressDAO;
import model.User_Address;

@WebServlet("/AddAddressServlet")
public class AddAddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private User_AddressDAO user_addressDAO;
       
	@Override
    public void init() throws ServletException {
        super.init();
        user_addressDAO = new User_AddressDAO();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session = request.getSession();
    	String userID = (String) session.getAttribute("UserID");
		
    	List<User_Address> user_addresses = user_addressDAO.getUser_Address(userID);
    	request.setAttribute("user_addresses", user_addresses);
	    
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/user-address.jsp");
        rd.forward(request, response);
		}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		HttpSession session = request.getSession();
    	String userID = (String) session.getAttribute("UserID");
		String prefecture = request.getParameter("prefecture");
		String city = request.getParameter("city");
		String number = request.getParameter("number");
		String address = prefecture+"-"+city+"-"+number;
		
		User_Address user_address = new User_Address();
    	user_address.setUserID(userID);
    	user_address.setAddress(address);
		
		try {
			
			if(user_addressDAO.authenticate(user_address)) {
				//住所の登録をする
				user_addressDAO.registerUserAddress(user_address);
	            List<User_Address> user_addresses = user_addressDAO.getUser_Address(userID);
	        	request.setAttribute("user_addresses", user_addresses);
	            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/user-address.jsp");
	            rd.forward(request, response);
				
			}else {
				//住所の登録に失敗
				List<User_Address> user_addresses = user_addressDAO.getUser_Address(userID);
	        	request.setAttribute("user_addresses", user_addresses);
			    request.setAttribute("errorMessage", "既に登録済みの住所です");
			    System.out.println("住所登録にエラーが発生");
	            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/user-address.jsp");
	            rd.forward(request, response); 
			}
            
		} catch (NamingException | ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}
