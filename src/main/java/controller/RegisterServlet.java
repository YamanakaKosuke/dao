package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import dao.User_AddressDAO;
import model.User;
import model.User_Address;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserDAO userDAO;
    private User_AddressDAO user_addressDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO();
        user_addressDAO = new User_AddressDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> list = userDAO.findAll();
        request.setAttribute("list", list);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/register.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	//ユーザーIDとパスワードを取得
        String userid = request.getParameter("userid");
        String password = request.getParameter("password");
        String Address = request.getParameter("address");

        User user = new User();
        user.setUserid(userid);
        user.setPassword(password);
        User_Address user_address = new User_Address();
        user_address.setUserID(userid);
        user_address.setAddress(Address);

        try {
        	//登録が成功なら成功画面を出す
            userDAO.registerUser(user);
            user_addressDAO.registerUserAddress(user_address);
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/register-success.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
        	//登録が失敗ならエラー画面を出す
            e.printStackTrace();
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/register-error.jsp");
            rd.forward(request, response);
        }
    }
}
