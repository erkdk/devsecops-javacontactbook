<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>ContactBook - Add Contact</title></head>
<body>
    <h1>Add Contact</h1>
    <form action="add-contact" method="post">
        Name: <input type="text" name="name" required><br><br>
        Email: <input type="email" name="email" required><br><br>
        Phone: <input type="text" name="phone"><br><br>
        <input type="submit" value="Add Contact">
    </form>
    <br>
    <a href="list-contacts">View All Contacts</a>
</body>
</html>

