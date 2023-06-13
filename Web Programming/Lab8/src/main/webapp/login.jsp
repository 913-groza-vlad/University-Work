
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Forum</title>
    <script src="Jquery/jquery.js"></script>
    <link rel="stylesheet" href="styles/login.css">

</head>
<body>
<div class="container">
    <h3>Welcome to the Forum App. Please log in to continue</h3>
    <form action="login" method="post">
        <div class="input-field">
            <label for="username">Username:</label>
            <input id="username" name="username" type="text">
        </div>
        <div class="input-field">
            <label for="password">Password:</label>
            <input id="password" name="password" type="password">
        </div>
        <%
            String error = (String) session.getAttribute("error");
            if (error != null) {
        %>
            <script>
                // Wrap the alert inside a setTimeout to allow the page to load before showing the alert
                setTimeout(function() {
                    alert("<%= error %>");
                }, 0);
            </script>
        <%
            }
        %>

        <div class="button">
            <button type="submit">Login</button>
        </div>
    </form>
</div>
</body>
</html>