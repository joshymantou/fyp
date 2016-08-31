/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dao.QuotationRequestDAO;
import dao.WorkshopDAO;
import entity.WebUser;
import entity.Workshop;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Joanne
 */
@WebServlet(name = "AddDiagnosticPriceServlet", urlPatterns = {"/AddDiagnosticPrice"})
public class AddDiagnosticPriceServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);

        int quotationRequestId = Integer.parseInt(request.getParameter("id"));
        double price = Double.parseDouble(request.getParameter("price"));
        String description = "";
        WebUser user = (WebUser) session.getAttribute("loggedInUser");
        int staffId = user.getStaffId();
        String token = user.getToken();
        int workshopId = user.getShopId();
        QuotationRequestDAO qrDAO = new QuotationRequestDAO();
        String isSuccess = qrDAO.addDiagnosticPrice(staffId, token, quotationRequestId, workshopId, price, description);
        //Error message? success message?
        if (isSuccess.length() == 0) {
            session.setAttribute("isSuccess", "Diagnostic Price: $" + price + "0 for ID: " + quotationRequestId);
//            RequestDispatcher view = request.getRequestDispatcher("ViewRequest.jsp?id=" + quotationRequestId);
//            view.forward(request, response);
            response.sendRedirect("New_Request.jsp");
        } else {
            session.setAttribute("fail", isSuccess + "(ID: " + quotationRequestId + ")");
//            RequestDispatcher view = request.getRequestDispatcher("AddDiagnosticPrice.jsp?id=" + quotationRequestId);
//            view.forward(request, response);
            response.sendRedirect("New_Request.jsp");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
