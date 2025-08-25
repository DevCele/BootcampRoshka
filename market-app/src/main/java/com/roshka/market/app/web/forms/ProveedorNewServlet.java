package com.roshka.market.app.web.forms;

import com.roshka.market.app.dao.PaisDAO;
import com.roshka.market.app.dao.ProveedorDAO;
import com.roshka.market.app.model.Proveedor;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/forms/proveedor/new")
public class ProveedorNewServlet extends HttpServlet {
    private final ProveedorDAO proveedorDAO = new ProveedorDAO();
    private final PaisDAO paisDAO = new PaisDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setAttribute("paises", paisDAO.listAll());
        req.setAttribute("ok", "1".equals(req.getParameter("ok")));
        req.setAttribute("newId", req.getParameter("id"));
        forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String nombre = val(req.getParameter("nombre"));
        String ruc    = val(req.getParameter("ruc"));
        String pais   = val(req.getParameter("pais_id"));

        Integer paisId = null;
        try { if (!pais.isBlank()) paisId = Integer.valueOf(pais); } catch (NumberFormatException ignored) {}

        Map<String,String> errors = new LinkedHashMap<>();
        if (nombre.isBlank()) errors.put("nombre", "Nombre es obligatorio");
        if (ruc.isBlank())    errors.put("ruc", "RUC es obligatorio");
        // RUC: dígitos + guion + dígito (ajusta si tu formato difiere)
        if (!ruc.isBlank() && !ruc.matches("^\\d{10}$")) {
            errors.put("ruc", "El RUC debe tener exactamente 10 dígitos");
        }           

        if (paisId == null || paisId <= 0)
            errors.put("pais_id", "Seleccione un país");

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("formNombre", nombre);
            req.setAttribute("formRuc", ruc);
            req.setAttribute("formPaisId", paisId);
            req.setAttribute("paises", paisDAO.listAll()); // recargar combo
            forward(req, resp);
            return;
        }

        try {
            Proveedor p = new Proveedor(null, nombre, ruc, paisId);
            p = proveedorDAO.insert(p);
            resp.sendRedirect(req.getContextPath() + "/forms/proveedor/new?ok=1&id=" + p.getId());
        } catch (Exception e) {
            errors.put("global", "Error guardando en BD: " + e.getMessage());
            req.setAttribute("errors", errors);
            req.setAttribute("formNombre", nombre);
            req.setAttribute("formRuc", ruc);
            req.setAttribute("formPaisId", paisId);
            req.setAttribute("paises", paisDAO.listAll());
            forward(req, resp);
        }
    }

    private String val(String s) { return s == null ? "" : s.trim(); }

    private void forward(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            req.getRequestDispatcher("/WEB-INF/views/forms/proveedor_form.jsp").forward(req, resp);
        } catch (Exception e) { resp.sendError(500, e.getMessage()); }
    }
}
