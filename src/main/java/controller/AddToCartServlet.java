package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CartDAO;
import model.Cart;

@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
	private CartDAO cartDAO;
	
	public AddToCartServlet() {
        super();
    }
	
	@Override
    public void init() throws ServletException {
        super.init();
        cartDAO = new CartDAO();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/main.jsp");
        rd.forward(request, response);
    }
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	String userID = (String) session.getAttribute("UserID");

        // フォームからアイテムIDと数量を取得
    	int itemId = Integer.parseInt(request.getParameter("itemId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        
        double price = cartDAO.getPriceById(itemId);
        Cart cart = new Cart();
        cart.setItemID(itemId);
        cart.setPrice(price);
        cart.setQuantity(quantity);
        cart.setUserID(userID);
        
        
	    try {
	    	//データベースへ挿入
	    	cartDAO.insertCartItem(cart);
	    	// リダイレクトを行う
	        response.sendRedirect(request.getContextPath() + "/Main");
        } catch (Exception e) {
        	System.out.println("カート操作中にエラー発生");
            e.printStackTrace();
         // エラーが発生した場合もリダイレクト
            response.sendRedirect(request.getContextPath() + "/Main");
        }
    }
    
}