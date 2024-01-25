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
import dao.OrderDAO;
import dao.OrderDetailDAO;
import dao.Order_DetailDAO;
import model.Cart;
import model.OrderDetail;
import model.Order_Detail;

@WebServlet("/PurchaseCompleteServlet")
public class PurchaseCompleteServlet extends HttpServlet {
	OrderDAO orderDAO;
	CartDAO cartDAO;
	OrderDetailDAO orderdetailDAO;
	Order_DetailDAO order_detailDAO;
	ItemDAO itemDAO;
	private static final long serialVersionUID = 1L;

    public PurchaseCompleteServlet() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	super.init();
    	orderDAO = new OrderDAO();
    	cartDAO = new CartDAO();
    	orderdetailDAO = new OrderDetailDAO();
    	order_detailDAO = new Order_DetailDAO();
    	itemDAO = new ItemDAO();
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
    	String userID = (String) session.getAttribute("UserID");
    	
    	//UserIDからカート情報を取得
    	List<Cart> cartItems = cartDAO.getCartItemsByUserID(userID);
  
		//情報の取得
		String Address = request.getParameter("deliveryLocation");
		String payment_method = request.getParameter("paymentMethod");
		
		//Ordersテーブルの処理Statusは"未完了"としてある
		orderDAO.insertOrders(cartItems,Address, payment_method);
		
		//OrderDetailsテーブルの処理,商品1つずつ挿入していく
		OrderDetail orderdetail;
		Order_Detail order_detail;
		
		for(Cart cartitem : cartItems) {
			//Itemのstockを数量分だけ減らす
			itemDAO.decreaseStock(cartitem.getItemID(),cartitem.getQuantity());
			
			orderdetail = new OrderDetail();
			orderdetail.setUserID(userID);
			orderdetail.setItemID(cartitem.getItemID());
			orderdetail.setQuantity(cartitem.getQuantity());
			orderdetail.setUnitPrice(cartitem.getPrice());
			
			//OrderDetailsテーブルへ挿入
			orderdetailDAO.insertOrderDetails(orderdetail);
			
			//Cart情報の削除
			cartDAO.deleteCartItem(cartitem);

			//2つのテーブルからOrderIDとDetailIDを取得
			order_detail = new Order_Detail();
			order_detail.setOrderID(orderDAO.getOrderIDForUncompletedOrderByUserID(userID));
			order_detail.setOrder_DetailID(orderdetailDAO.getOrder_DetailIDForUncompletedOrderByUserID(userID,cartitem.getItemID()));
			order_detailDAO.insertOrder_Details(order_detail);
		}

		//Stutasを完了にする
		orderDAO.completeStatusAtOrders(userID);
		orderdetailDAO.completeStutasAtOrderDetails(userID);
		
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/purchase-complete.jsp");
        rd.forward(request, response);

	}

}
