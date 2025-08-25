package com.roshka.market.app.web;

import com.roshka.market.app.dao.ReportDAO;
import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/reports/factura-detalle")
public class FacturaDetalleServlet extends HttpServlet {
  private final ReportDAO dao = new ReportDAO();
  @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    int id = parseIntOrDefault(req.getParameter("id"), 1);
    var data = dao.detalleFactura(id);
    req.setAttribute("data", data);
    req.setAttribute("title", "Detalle de factura #" + id);
    req.setAttribute("supportsLimit", false);
    req.setAttribute("supportsId", true); 
    try { req.getRequestDispatcher("/WEB-INF/views/table.jsp").forward(req, resp); }
    catch (Exception e) { resp.sendError(500, e.getMessage()); }
  }
  private int parseIntOrDefault(String s, int d){ try{ return s==null?d:Integer.parseInt(s);}catch(Exception e){return d;}}
}

