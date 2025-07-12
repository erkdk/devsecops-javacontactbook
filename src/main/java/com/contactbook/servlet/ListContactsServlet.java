package com.contactbook.servlet;

import com.contactbook.dao.ContactDAO;
import com.contactbook.model.Contact;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/list-contacts")
public class ListContactsServlet extends HttpServlet {
    private ContactDAO contactDAO;

    @Override
    public void init() {
        contactDAO = new ContactDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Contact> contacts = contactDAO.selectAllContacts();
        request.setAttribute("contactsList", contacts);
        request.getRequestDispatcher("list-contacts.jsp").forward(request, response);
    }
}

