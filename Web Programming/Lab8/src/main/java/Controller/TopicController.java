package Controller;

import Domain.Comment;
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

public class TopicController extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        int ok = 0;
        for (Cookie cookie: request.getCookies()) {
            if (cookie.getName().equals("topicId")) {
                DBManager dbManager = new DBManager();
                List<Topic> topics = dbManager.getAllTopics();
                Topic currentTopic = topics.stream().filter(topic -> topic.getId() == Integer.parseInt(cookie.getValue())).findFirst().orElse(null);
                request.getSession().setAttribute("currentTopic", currentTopic);
                List<Comment> comments = dbManager.getTopicComments(Integer.parseInt(cookie.getValue()));
                request.getSession().setAttribute("comments", comments);
                ok++;
            }
            if (cookie.getName().equals("user")) {
                User user = (new DBManager()).getUserByName(cookie.getValue());
                request.getSession().setAttribute("user", user);
                ok++;
            }
            if (ok == 2) {
                request.getRequestDispatcher("post.jsp").include(request, response);
                return;
            }
        }

        response.getWriter().println("Invalid request!");
    }

}
