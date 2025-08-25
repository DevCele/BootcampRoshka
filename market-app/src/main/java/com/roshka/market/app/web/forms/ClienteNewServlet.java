package com.roshka.market.app.web.forms;

import com.roshka.market.app.dao.ClienteDAO;
import com.roshka.market.app.model.Cliente;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/forms/cliente/new")
public class ClienteNewServlet extends HttpServlet {
    private final ClienteDAO dao = new ClienteDAO();

    @Override 
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
       req.setAttribute("ok", "1".equals(req.getParameter("ok")));
       req.setAttribute("newId", req.getParameter("id"));
       forward(req, resp);
        
    }
    private String val(String s) { return s == null ? "" : s.trim();}
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String nombre    = val(req.getParameter("nombre"));
        String apellido  = val(req.getParameter("apellido"));
        String nroCedula = val(req.getParameter("nro_cedula"));
        String telefono  = val(req.getParameter("telefono"));

        Map<String,String> errors = new LinkedHashMap<>();
        if (nombre.isBlank())    errors.put("nombre", "Nombre es obligatorio");
        if (apellido.isBlank())  errors.put("apellido", "Apellido es obligatorio");
        if (nroCedula.isBlank()) errors.put("nro_cedula", "Nro. cédula es obligatorio");
        
        if (!nroCedula.isBlank() && !nroCedula.matches("^\\d{1,12}-\\d{1}$")) {
            errors.put("nro_cedula", "Formato inválido. Use 123456789-0");
            }
        if (!telefono.isBlank() && !telefono.matches("^\\d{3}-\\d{3}-\\d{4}$")) {
            errors.put("telefono", "Formato inválido. Use 123-456-7890");
            }

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("formNombre", nombre);
            req.setAttribute("formApellido", apellido);
            req.setAttribute("formNroCedula", nroCedula);
            req.setAttribute("formTelefono", telefono);
            forward(req, resp);
            return;
        }

         try {
        Cliente c = new Cliente(null, nombre, apellido, nroCedula, telefono);
        c = dao.insert(c); // puede lanzar excepción si hay error en BD

        String ctx = req.getContextPath();
        resp.sendRedirect(ctx + "/forms/cliente/new?ok=1&id=" + c.getId());
        } catch (Exception e) {
            errors.put("global", "Error guardando en la BD: " + e.getMessage());
            req.setAttribute("errors", errors);
            // repintar el form con lo que venía
            req.setAttribute("formNombre", nombre);
            req.setAttribute("formApellido", apellido);
            req.setAttribute("formNroCedula", nroCedula);
            req.setAttribute("formTelefono", telefono);
            forward(req, resp);
        }
    }
    
     private void forward(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            req.getRequestDispatcher("/WEB-INF/views/forms/cliente_form.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendError(500, e.getMessage());
        }
    }
}
