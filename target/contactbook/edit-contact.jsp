<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.contactbook.model.Contact" %>
<html>
<head><title>ContactBook - Edit Contact</title></head>
<body>
    <h1>Edit Contact</h1>

    <%
        Contact contact = (Contact) request.getAttribute("contact");
        if (contact == null) {
    %>
        <p>Contact not found!</p>
    <%
        } else {
    %>
    <form action="update-contact" method="post">
        <input type="hidden" name="id" value="<%= contact.getId() %>">
        Name: <input type="text" name="name" value="<%= contact.getName() %>" required><br><br>
        Email: <input type="email" name="email" value="<%= contact.getEmail() %>" required><br><br>
        Phone: <input type="text" name="phone" value="<%= contact.getPhone() %>"><br><br>
        <input type="submit" value="Update Contact">
    </form>
    <%
        }
    %>

    <br>
    <a href="list-contacts">Back to Contact List</a>
</body>
</html>

