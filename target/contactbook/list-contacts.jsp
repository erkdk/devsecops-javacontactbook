<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.contactbook.model.Contact" %>
<html>
<head><title>ContactBook - List Contacts</title></head>
<body>
    <h1>All Contacts</h1>

    <%
        String message = (String) session.getAttribute("message");
        if (message != null) {
    %>
        <div style="color: green;"><%= message %></div>
    <%
        session.removeAttribute("message");
        }
    %>

    <table border="1" cellpadding="5" cellspacing="0">
        <tr>
            <th>Name</th><th>Email</th><th>Phone</th><th>Actions</th>
        </tr>
        <%
            List<Contact> contacts = (List<Contact>) request.getAttribute("contactsList");
            if (contacts != null) {
                for (Contact c : contacts) {
        %>
        <tr>
            <td><%= c.getName() %></td>
            <td><%= c.getEmail() %></td>
            <td><%= c.getPhone() %></td>
            <td>
                <a href="edit-contact?id=<%= c.getId() %>">Edit</a> |
                <a href="delete-contact?id=<%= c.getId() %>" onclick="return confirm('Are you sure?');">Delete</a>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>

    <br>
    <a href="index.jsp">Add New Contact</a>
</body>
</html>

