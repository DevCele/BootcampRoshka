package com.roshka.market.app.web;

import com.roshka.market.app.dao.ReportDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/reports/iva-10")
public class Iva10PorFacturaServlet extends HttpServlet {
    private final ReportDAO dao = new ReportDAO();

    @Override 
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
       
        var data = dao.iva10PorFactura();
        req.setAttribute("data", data);
        req.setAttribute("title", "Facturas IVA 10%");
        req.setAttribute("supportsLimit", false);
        try {
            req.getRequestDispatcher("/WEB-INF/views/table.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendError(500, e.getMessage());
        }
    }
}
