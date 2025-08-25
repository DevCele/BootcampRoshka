package com.roshka.market.app.web;


import com.roshka.market.app.dao.ReportDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "TopProveedoresServlet", urlPatterns = {"/reports/top-proveedores"})
public class TopProveedoresServlet extends HttpServlet {
    private final ReportDAO dao = new ReportDAO();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int limit = 10;
        try {
            if (req.getParameter("limit") != null) {
                limit = Integer.parseInt (req.getParameter("limit"));
            }
        } catch (NumberFormatException ignored) {}
        
        var data = dao.topProveedores(limit);
        req.setAttribute("data", data);
        req.setAttribute("title", "Top Proveedores");
        req.setAttribute("supportsLimit", true);
        try {
            req.getRequestDispatcher("/WEB-INF/views/table.jsp").forward(req, resp);
        } catch(Exception e){
            resp.sendError(500, e.getMessage());
        }
    }
 }
