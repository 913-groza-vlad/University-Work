package Domain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    String connectionURL = "jdbc:mysql://localhost:3306/wp";
    String username = "root";
    String password = "";

    public DBManager() {
        try {
            // DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public User authenticate(String username, String password) {
        User user = getUserByName(username);
        if (user != null && user.getPassword().equals(password))
            return user;
        return null;
    }

    public User getUserByName(String username) {
        User user = null;
        String statement = "SELECT * FROM users WHERE username = '" + username + "'";
        try (Connection connection = DriverManager.getConnection(this.connectionURL, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(statement);
             ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        return user;
    }

    public List<Topic> getAllTopics() {
        List<Topic> topics = new ArrayList<>();
        String statement = "SELECT * FROM topics";
        try (Connection connection = DriverManager.getConnection(this.connectionURL, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(statement);
             ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    topics.add(new Topic(rs.getInt("id"), rs.getString("content"), getTopicComments(rs.getInt("id"))));
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        return topics;
    }

    public List<Comment> getTopicComments(int id) {
        List<Comment> comments = new ArrayList<>();
        String statement = "SELECT * FROM comments WHERE topic_id = " + id;
        try (Connection connection = DriverManager.getConnection(this.connectionURL, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(statement);
             ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    comments.add(new Comment(rs.getInt("id"), rs.getString("commentText"), getUserById(rs.getInt("user_id"))));
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        return comments;
    }

    private User getUserById(int userId) {
        User user = null;
        String statement = "SELECT * FROM users WHERE id = " + userId;
        try (Connection connection = DriverManager.getConnection(this.connectionURL, this.username, this.password);
             PreparedStatement preparedStatement = connection.prepareStatement(statement);
             ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        return user;
    }

    public void addTopic(String topicContent) {
        String statement = "INSERT INTO topics(content) VALUES ('" + topicContent + "')";
        try (var connection = DriverManager.getConnection(connectionURL, username, password);
             var preparedStatement = connection.prepareStatement(statement)) {
             preparedStatement.execute();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
    }

    public void deleteComment(int commentId) {
        String statement = "DELETE FROM comments where id = " + commentId;
        try (var connection = DriverManager.getConnection(connectionURL, username, password);
             var preparedStatement = connection.prepareStatement(statement)) {
             preparedStatement.execute();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
    }

    public void addComment(String commentText, int topicId, int userId) {
        String statement = "INSERT INTO comments(commentText, topic_id, user_id) VALUES ('" + commentText + "', " + topicId + ", " + userId + ")";
        try (var connection = DriverManager.getConnection(connectionURL, username, password);
             var preparedStatement = connection.prepareStatement(statement)) {
             preparedStatement.execute();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
    }
}
