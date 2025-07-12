package com.contactbook.servlet;

import com.contactbook.dao.ContactDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/delete-contact")
public class DeleteContactServlet extends HttpServlet {
    private ContactDAO contactDAO;

    @Override
    public void init() {
        contactDAO = new ContactDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean success = contactDAO.deleteContact(id);

        if (success) {
            request.getSession().setAttribute("message", "Contact deleted successfully!");
        } else {
            request.getSession().setAttribute("message", "Failed to delete contact.");
        }
        response.sendRedirect("list-contacts");
    }
}

