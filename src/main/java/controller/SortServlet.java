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

@WebServlet("/SortServlet")
public class SortServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ItemDAO itemdao;
	private CartDAO cartDAO;
	private CategoryDAO categoryDAO;
	
    public SortServlet() {
        super();
        }
    
    @Override
    public void init() throws ServletException {
        super.init();
        itemdao = new ItemDAO();
        cartDAO = new CartDAO();
        categoryDAO = new CategoryDAO();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/main.jsp");
        rd.forward(request, response);
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

        String ordermethod = request.getParameter("sortmethod");
        
        //並び替え方法によりデータ順を変更
        List<Item> items;
        
        if(ordermethod == "null") {
			items = itemdao.findAll();
	        request.setAttribute("items", items);
		}else {
			items = itemdao.Sorting(ordermethod);
            request.setAttribute("items", items);
		}
		
        List<Category> categories = categoryDAO.getCategories();
		request.setAttribute("categories", categories);
        
		// カートの中身を取得してリクエスト属性にセット
		HttpSession session = request.getSession();
    	String userID = (String) session.getAttribute("UserID");

        List<Cart> cartItems = cartDAO.getCartItemsByUserID(userID);
        request.setAttribute("cartItems", cartItems);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/main.jsp");
        rd.forward(request, response);
    	
	}
}
