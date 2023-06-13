<%@ page import="Domain.Topic" %>
<%@ page import="Domain.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Forum</title>
    <script src="Jquery/jquery.js"></script>
    <link rel="stylesheet" href="styles/main-styles.css">
    <script src="Jquery/main.js"></script>

</head>
<body>
<div class="container">
    <%
        String currentUser = ((User) request.getSession().getAttribute("user")).getUsername();
        System.out.println("<h4>Hello there, user " + currentUser + "!</h4>");
    %>
    <h3>Hello there, user <i style="margin-left: 4px; font-size: 18px;"> <%= currentUser %>  </i> !</h3>
    <div class="topics">
        <%
            @SuppressWarnings("unchecked")
            List<Topic> posts = (List<Topic>) request.getSession().getAttribute("topics");
            System.out.println("<ul class=\"elements\">");
            for (Topic post : posts) {
                System.out.println("<li><button onClick=goToTopic(" + post.getId() + ")>" + post.getContent() + "</button></li>");
            }
            System.out.println("</ul>");
        %>
        <ul class="elements">
            <% for (Topic post : posts) { %>
            <li>
                <button class="select-topic" onClick="goToTopic(<%= post.getId() %>)">
                    <%= post.getContent() %>
                </button>
            </li>
            <% } %>
        </ul>
    </div>

    <div class="add">
        <div class="input-field">
            <label for="topic">Topic content: </label>
            <input id="topic" name="topic" type="text">
        </div>
        <button onclick="addTopic()">Add Topic</button>
    </div>

    <button class="logout" onclick="logout()">Logout</button>

</div>
</body>

</html>