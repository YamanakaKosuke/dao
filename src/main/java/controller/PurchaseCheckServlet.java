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
import model.Cart;
import model.Item;

@WebServlet("/PurchaseCheckServlet")
public class PurchaseCheckServlet extends HttpServlet {
	private CartDAO cartDAO;
	private static final long serialVersionUID = 1L;

    public PurchaseCheckServlet() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	super.init();
    	cartDAO = new CartDAO();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//カート情報を取得し、送る
		HttpSession session = request.getSession();
    	String userID = (String) session.getAttribute("UserID");
    	
    	//UserIDからカート情報を取得
    	List<Cart> cartItems = cartDAO.getCartItemsByUserID(userID);
    	request.setAttribute("cartItems", cartItems);

		
		//住所情報の取得
		String deliveryLocation = request.getParameter("deliveryLocation");
		request.setAttribute("deliveryLocation", deliveryLocation);

		//支払い方法の取得
		String paymentMethod = request.getParameter("paymentMethod");
		request.setAttribute("paymentMethod", paymentMethod);
		
		ItemDAO itemdao=new ItemDAO();
		List<Item> items=itemdao.findAll();
	    request.setAttribute("items", items);
		
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/purchase-check.jsp");
        rd.forward(request, response);
	}

}
