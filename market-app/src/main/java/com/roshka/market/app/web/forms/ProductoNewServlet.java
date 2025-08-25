package com.roshka.market.app.web.forms;

import com.roshka.market.app.dao.ProductoDAO;
import com.roshka.market.app.model.Producto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/forms/producto/new")
public class ProductoNewServlet extends HttpServlet {
    private final ProductoDAO productoDAO = new ProductoDAO();

    private String val(String s) { return s == null ? "" : s.trim(); }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setAttribute("ok", "1".equals(req.getParameter("ok")));
        req.setAttribute("newId", req.getParameter("id"));
        forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        String nombre = val(req.getParameter("nombre"));
        String precioStr = val(req.getParameter("precio"));
        String proveedorIdStr = val(req.getParameter("proveedor_id"));
        String costoStr = val(req.getParameter("costo"));

        Map<String,String> errors = new LinkedHashMap<>();

        if (nombre.isBlank()) errors.put("nombre", "Nombre es obligatorio");

        Double precio = null;
        try { precio = Double.parseDouble(precioStr); }
        catch (Exception e) { errors.put("precio", "Precio inválido"); }

        Integer proveedorId = null;
        try { proveedorId = Integer.parseInt(proveedorIdStr); }
        catch (Exception e) { errors.put("proveedor_id", "Proveedor ID inválido"); }

        Double costo = null;
        try { costo = Double.parseDouble(costoStr); }
        catch (Exception e) { errors.put("costo", "Costo inválido"); }

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("formNombre", nombre);
            req.setAttribute("formPrecio", precioStr);
            req.setAttribute("formProveedorId", proveedorIdStr);
            req.setAttribute("formCosto", costoStr);
            forward(req, resp);
            return;
        }

        try {
            Producto p = new Producto(null, nombre, precio, proveedorId, costo);
            p = productoDAO.insert(p);
            resp.sendRedirect(req.getContextPath() + "/forms/producto/new?ok=1&id=" + p.getId());
        } catch (Exception e) {
            errors.put("global", "Error guardando en BD: " + e.getMessage());
            req.setAttribute("errors", errors);
            forward(req, resp);
        }
    }

    private void forward(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            req.getRequestDispatcher("/WEB-INF/views/forms/producto_form.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendError(500, e.getMessage());
        }
    }
}
