package Controller;

import Domain.DBManager;
import Domain.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


public class LoginController extends HttpServlet {
    public LoginController() {
        super();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        request.getRequestDispatcher("login.jsp").include(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username.isEmpty()) {
            request.getSession().setAttribute("error", "Username must not be empty");
            request.getRequestDispatcher("login.jsp").include(request, response);
        }
        else if (password.isEmpty()) {
            request.getSession().setAttribute("error", "Password must not be empty");
            request.getRequestDispatcher("login.jsp").include(request, response);
        }
        else {
            DBManager dbManager = new DBManager();
            User user = dbManager.authenticate(username, password);
            if (user != null) {
                response.addCookie(new Cookie("user", user.getUsername()));
                request.getSession().setAttribute("user", user);
                response.sendRedirect("main");
                //request.getRequestDispatcher("index.jsp").include(request, response);
            }
            else {
                request.getSession().setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("login.jsp").include(request, response);
            }
        }
    }
}
