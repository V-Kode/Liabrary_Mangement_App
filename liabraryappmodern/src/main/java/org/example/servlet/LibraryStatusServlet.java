package org.example.servlet;

import org.example.dao.BookDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/library-status")
public class LibraryStatusServlet extends HttpServlet {

    private BookDAO bookDAO;

    @Override
    public void init() throws ServletException {
        bookDAO = new BookDAO(); // DAO integration
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int totalBooks = bookDAO.getAll().size();

        out.println("<html><body>");
        out.println("<h2>Library Management System</h2>");
        out.println("<p>Servlet is running successfully.</p>");
        out.println("<p>Total books in library: <b>" + totalBooks + "</b></p>");
        out.println("</body></html>");
    }
}
