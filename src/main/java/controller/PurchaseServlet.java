package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CartDAO;
import dao.ItemDAO;
import dao.User_AddressDAO;
import model.Cart;
import model.Item;
import model.User_Address;

@WebServlet("/PurchaseServlet")
public class PurchaseServlet extends HttpServlet {
	private CartDAO cartDAO;
	private User_AddressDAO user_addressDAO;
	private static final long serialVersionUID = 1L;
       
    public PurchaseServlet() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
        super.init();
        cartDAO = new CartDAO();
        user_addressDAO = new User_AddressDAO();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	ItemDAO itemdao=new ItemDAO();
    	List<Item> items=itemdao.findAll();
			
		request.setAttribute("items", items);
			
		HttpSession session = request.getSession();
	    String userID = (String) session.getAttribute("UserID");
	    System.out.println("PurchaseServlet.java AtGet:userID = " + userID);
	    List<Cart> cartItems = cartDAO.getCartItemsByUserID(userID);
        List<User_Address> user_addresses = user_addressDAO.getUser_Address(userID);
	    	
	    request.setAttribute("user_addresses", user_addresses);
	    request.setAttribute("cartItems", cartItems);
    	
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/purchase.jsp");
        rd.forward(request, response);
    }

	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		 
		 ItemDAO itemdao=new ItemDAO();
	     
		 List<Item> items=itemdao.findAll();
		 request.setAttribute("items", items);
			
		HttpSession session = request.getSession();
	    String userID = (String) session.getAttribute("UserID");
	    System.out.println("PurchaseServlet.java:userID = " + userID);
	    List<Cart> cartItems = cartDAO.getCartItemsByUserID(userID);
	    List<User_Address> user_addresses = user_addressDAO.getUser_Address(userID);
	    	
	    request.setAttribute("user_addresses", user_addresses);
	    request.setAttribute("cartItems", cartItems);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/purchase.jsp");
	    rd.forward(request, response);
	 }
	    	

}
