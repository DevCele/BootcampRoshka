package com.roshka.market.app.web;

import com.roshka.market.app.infra.BD;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/db/health")
public class DbHealthServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        try (Connection cn = BD.get();
             PreparedStatement ps = cn.prepareStatement("select 1");
             ResultSet rs = ps.executeQuery()) {

            rs.next();
            resp.getWriter().println("DB OK - SELECT 1 = " + rs.getInt(1));

        } catch (Exception e) {
            resp.setStatus(500);
            e.printStackTrace(resp.getWriter());
        }
    }
}
