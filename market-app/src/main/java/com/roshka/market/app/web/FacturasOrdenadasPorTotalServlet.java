package com.roshka.market.app.web;

import com.roshka.market.app.dao.ReportDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/reports/facturas-orden-total")
public class FacturasOrdenadasPorTotalServlet extends HttpServlet {
    private final ReportDAO dao = new ReportDAO();

    @Override 
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        
        var data = dao.facturasOrdenadasPorTotal();
        req.setAttribute("data", data);
        req.setAttribute("title", "Facturas Ordenadas por Total");
        req.setAttribute("supportsLimit", false);
        try {
            req.getRequestDispatcher("/WEB-INF/views/table.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendError(500, e.getMessage());
        }
    }
}
