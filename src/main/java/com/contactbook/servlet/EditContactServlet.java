package com.contactbook.servlet;

import com.contactbook.dao.ContactDAO;
import com.contactbook.model.Contact;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/edit-contact")
public class EditContactServlet extends HttpServlet {
    private ContactDAO contactDAO;

    @Override
    public void init() {
        contactDAO = new ContactDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Contact existingContact = contactDAO.selectContactById(id);

        request.setAttribute("contact", existingContact);
        request.getRequestDispatcher("edit-contact.jsp").forward(request, response);
    }
}

