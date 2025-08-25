package com.roshka.market.app.web;

import com.roshka.market.app.dao.ReportDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/reports/top-monedas")
public class TopMonedasServlet extends HttpServlet {
    private final ReportDAO dao = new ReportDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int limit = 10;
        try {
            if (req.getParameter("limit") != null) {
                limit = Integer.parseInt(req.getParameter("limit"));
            }
        } catch (NumberFormatException ignored) {}

        var data = dao.topMonedasMasUsadas(limit);
        req.setAttribute("data", data);
        req.setAttribute("title", "Top monedas mas utilizadas");
        req.setAttribute("supportsLimit", true);
        try {
            req.getRequestDispatcher("/WEB-INF/views/table.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendError(500, e.getMessage());
        }
    }
}
