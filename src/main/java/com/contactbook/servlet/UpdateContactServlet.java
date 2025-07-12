package com.contactbook.servlet;

import com.contactbook.dao.ContactDAO;
import com.contactbook.model.Contact;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/update-contact")
public class UpdateContactServlet extends HttpServlet {
    private ContactDAO contactDAO;

    @Override
    public void init() {
        contactDAO = new ContactDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        Contact updatedContact = new Contact(id, name, email, phone);
        boolean success = contactDAO.updateContact(updatedContact);

        if (success) {
            request.getSession().setAttribute("message", "Contact updated successfully!");
        } else {
            request.getSession().setAttribute("message", "Failed to update contact.");
        }
        response.sendRedirect("list-contacts");
    }
}

