package Controller;

import Domain.DBManager;
import Domain.Topic;
import Domain.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class MainController extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        for (Cookie cookie: request.getCookies()) {
            System.out.println(cookie.getName() + ' ' + cookie.getValue());
            if (cookie.getName().equals("user")) {
                List<Topic> topics = (new DBManager()).getAllTopics();
                User user = (new DBManager()).getUserByName(cookie.getValue());
                request.getSession().setAttribute("topics", topics);
                request.getSession().setAttribute("user", user);
                request.getRequestDispatcher("index.jsp").include(request, response);
                return;
            }
        }
        response.getWriter().println("Invalid request!");
    }
}
