package controller;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.OrderDAO;
import model.Order;

@WebServlet("/PurchaseHistoryServlet")
public class PurchaseHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderDAO orderDAO;
	
	@Override
    public void init() throws ServletException {
        super.init();
        orderDAO = new OrderDAO();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session = request.getSession();
    	String userID = (String) session.getAttribute("UserID");
    	
    	//UserIDから"Complete"状態のOrders
    	List<Order> orders = orderDAO.getCompleteOrderByUserID(userID);

        orders.sort(Comparator.comparing(Order::getOrderDate).reversed());
		request.setAttribute("orders", orders);
		
    	//(Totalcost、詳細)を取得
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/purchase-history.jsp");
        rd.forward(request, response);
		
		}

}
