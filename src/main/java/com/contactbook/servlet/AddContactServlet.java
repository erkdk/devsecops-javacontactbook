package com.contactbook.servlet;

import com.contactbook.dao.ContactDAO;
import com.contactbook.model.Contact;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/add-contact")
public class AddContactServlet extends HttpServlet {
    private ContactDAO contactDAO;

    @Override
    public void init() {
        contactDAO = new ContactDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        Contact newContact = new Contact(name, email, phone);
        contactDAO.insertContact(newContact);

        request.getSession().setAttribute("message", "Contact added successfully!");
        response.sendRedirect("list-contacts");
    }
}

