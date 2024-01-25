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
import dao.CategoryDAO;
import dao.ItemDAO;
import model.Cart;
import model.Category;
import model.Item;

@WebServlet("/Main")
public class Main extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ItemDAO itemdao=new ItemDAO();
        CartDAO cartDAO = new CartDAO();
        CategoryDAO categoryDAO = new CategoryDAO();
        
     // セッションからitemsを取得
        HttpSession session = request.getSession();

    	String userID = (String) session.getAttribute("UserID");
        List<Item> items = itemdao.findAll();
        request.setAttribute("items", items);
        
        List<Category> categories = categoryDAO.getCategories();
		request.setAttribute("categories", categories);

        List<Cart> cartItems = cartDAO.getCartItemsByUserID(userID);
        request.setAttribute("cartItems", cartItems);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/main.jsp");
        rd.forward(request, response);
	}

}