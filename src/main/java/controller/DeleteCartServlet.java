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

@WebServlet("/DeleteCartServlet")
public class DeleteCartServlet extends HttpServlet {
	private CartDAO cartDAO;
	private static final long serialVersionUID = 1L;
       
    public DeleteCartServlet() {
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
    	// フォームからアイテムIDを取得
    	int itemId = Integer.parseInt(request.getParameter("itemId"));
    	
    	Cart cart = new Cart();
    	cart.setItemID(itemId);
    	cart.setUserID(userID);
        
    	try {
    		//データベースから削除
    		cartDAO.deleteCartItem(cart);
	        response.sendRedirect(request.getContextPath() + "/Main");
    	} catch (Exception e) {
        	System.out.println("カート削除中にエラー発生");
            e.printStackTrace();
         // エラーが発生した場合もリダイレクト
            response.sendRedirect(request.getContextPath() + "/Main");
        }
	}

}
