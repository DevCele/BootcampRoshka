<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.roshka.market.app.model.Pais" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>Nuevo proveedor</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/base.css">
</head>
<body>
  <h2>Nuevo proveedor</h2>

  <% Boolean ok = (Boolean) request.getAttribute("ok"); if (ok != null && ok) { %>
    <div class="ok">Proveedor creado correctamente. ID = <%= request.getAttribute("newId") %></div>
  <% } %>

  <%
    Map<String,String> errors = (Map<String,String>) request.getAttribute("errors");
    String formNombre = (String) request.getAttribute("formNombre"); if (formNombre == null) formNombre = "";
    String formRuc    = (String) request.getAttribute("formRuc");    if (formRuc == null) formRuc = "";
    Integer formPaisId = (Integer) request.getAttribute("formPaisId");
    List<Pais> paises = (List<Pais>) request.getAttribute("paises");
    if (paises == null) paises = java.util.Collections.emptyList();
  %>

  <% if (errors != null && errors.get("global") != null) { %>
    <div class="error"><%= errors.get("global") %></div>
  <% } %>

  <form method="post">
    <label>Nombre
      <input type="text" name="nombre" value="<%=formNombre%>" required />
      <% if (errors != null && errors.get("nombre") != null) { %>
        <div class="error"><%= errors.get("nombre") %></div>
      <% } %>
    </label>

    <label>RUC
        <input type="text" name="ruc" value="<%=formRuc%>" required
               placeholder="Ej: 1234567890"
               pattern="^\d{10}$"
               title="El RUC debe tener exactamente 10 dígitos"
               maxlength="10" />
        <% if (errors != null && errors.get("ruc") != null) { %>
          <div class="error"><%= errors.get("ruc") %></div>
        <% } %>
    </label>

    <label>País
      <select name="pais_id" required>
        <option value="">-- Seleccione --</option>
        <% for (Pais p : paises) { %>
          <option value="<%=p.getId()%>" <%= (formPaisId != null && formPaisId.equals(p.getId())) ? "selected" : "" %>>
            <%= p.getNombre() %>
          </option>
        <% } %>
      </select>
      <% if (errors != null && errors.get("pais_id") != null) { %>
        <div class="error"><%= errors.get("pais_id") %></div>
      <% } %>
    </label>

    
      <button type="submit">Guardar</button>
      <a href="<%=request.getContextPath()%>/">Volver al menú</a>
    
  </form>
</body>
</html>
