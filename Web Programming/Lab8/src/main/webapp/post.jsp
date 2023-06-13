<%@ page import="Domain.User" %>
<%@ page import="Domain.Topic" %>
<%@ page import="Domain.Comment" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Vlad
  Date: 5/21/2023
  Time: 11:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Forum post</title>
    <script src="Jquery/jquery.js"></script>
    <link rel="stylesheet" href="styles/topic.css">
    <script src="Jquery/post.js"></script>
</head>
<body>
<div class="container">
    <%
        User user = (User) request.getSession().getAttribute("user");
        Topic currentTopic= (Topic) request.getSession().getAttribute("currentTopic");
        // System.out.println("<h4> Hello there! General " + user.getUsername() + "! Welcome to the forum topic: <b>" + currentTopic.getContent() + "</b></h4>");
    %>
    <h4>Hello <%= user.getUsername()%> ! Welcome to the forum topic: <b>" <%= currentTopic.getContent()%> "</b></h4>
    <div class="comments">
        <h3>Comments:</h3>
        <%
            @SuppressWarnings("unchecked")
            List<Comment> commentList = (List<Comment>) request.getSession().getAttribute("comments");
            for (Comment comment: commentList) {
                String toPrint = "<div class=\"comment\">" + "<p>" + "<i>" + comment.getUser().getUsername() + "</i>" + ": " + comment.getCommentText() + "</p>";
                if (comment.getUser().getID() == user.getID()) {
                    toPrint += "<button class=\"delete\" onclick=\"deleteComment(" + comment.getId() + ")\">" + "DELETE" + "</button> </div>";
                } else {
                    toPrint += "</div>";
                }
        %>
        <%= toPrint %>
        <%
            }
        %>

    </div>
    <div class="add-div">
        <div class="input-field">
            <label for="commentInput">Comment: </label>
            <input id="commentInput" type="text">
        </div>
        <button class="add-button" onclick="addComment()">Add Comment</button>
    </div>


</div>
</body>
</html>
