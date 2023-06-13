package Controller;

import Domain.DBManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AddTopicController extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String topicContent = request.getParameter("topicContent");
        if (topicContent.isEmpty()) {
            request.getSession().setAttribute("error", "Topic content must not be empty");
            request.getRequestDispatcher("index.jsp").include(request, response);
        }
        else {
            DBManager dbManager = new DBManager();
            dbManager.addTopic(topicContent);
        }
    }
}
