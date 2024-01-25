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

import dao.UserDAO;
import model.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;


    public LoginServlet() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	List<User> list = userDAO.findAll();
        request.setAttribute("list", list);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/login.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // フォームからユーザー名とパスワードを取得
        String userid = request.getParameter("userid");
        String password = request.getParameter("password");

        //ユーザーの認証を行う
        try {
			if (userDAO.authenticate(userid, password)) {
				//ユーザーIDをセッションに保存
				request.getSession().setAttribute("UserID", userid);
				// 認証成功の場合、login-success.jspにフォワード
			    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/login-success.jsp");
			    rd.forward(request, response);
			} else {
			    // 認証失敗の場合、エラーメッセージを設定してregister.jspにリダイレクト
			    request.setAttribute("errorMessage", "ユーザー名またはパスワードが正しくありません");
			    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/login.jsp");
			    rd.forward(request, response);
			}
		} catch (NamingException | ServletException | IOException e) {
			e.printStackTrace();
		}
    }
}
