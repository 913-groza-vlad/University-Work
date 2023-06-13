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

public class AddCommentController extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = null;
        Topic currentTopic = null;
        DBManager databaseManager = new DBManager();
        int ok = 0;
        for (Cookie cookie: request.getCookies()) {
            System.out.println(cookie.getName());
            if (cookie.getName().equals("user")) {
                ok++;
                currentUser = databaseManager.getUserByName(cookie.getValue());
            }
            if (cookie.getName().equals("topicId")) {
                ok++;
                List<Topic> posts = databaseManager.getAllTopics();
                currentTopic = posts.stream().filter(post -> post.getId() == Integer.parseInt(cookie.getValue())).findFirst().get();
            }
            if (ok == 2) {
                String content = request.getParameter("commentText");
                System.out.println(currentTopic);
                System.out.println(currentUser);
                System.out.println(content);
                databaseManager.addComment(content, currentTopic.getId(), currentUser.getID());
                return;
            }
        }
        response.getWriter().println("Invalid request");
    }
}
