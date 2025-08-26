<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>Nuevo Producto</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/base.css">
  </style>
</head>
<body>
  <h2>Registrar Producto</h2>

  <%
    Map<String,String> errors = (Map<String,String>) request.getAttribute("errors");
    boolean ok = Boolean.TRUE.equals(request.getAttribute("ok"));
    String newId = (String) request.getAttribute("newId");

    String formNombre = (String) request.getAttribute("formNombre"); if (formNombre == null) formNombre = "";
    String formPrecio = (String) request.getAttribute("formPrecio"); if (formPrecio == null) formPrecio = "";
    String formProveedorId = (String) request.getAttribute("formProveedorId"); if (formProveedorId == null) formProveedorId = "";
    String formCosto = (String) request.getAttribute("formCosto"); if (formCosto == null) formCosto = "";
  %>

  <% if (ok) { %>
    <p class="ok">Producto guardado con éxito. ID generado: <%= newId %></p>
  <% } %>

  <form method="post">
    <label>Nombre
      <input type="text" name="nombre" value="<%=formNombre%>" required />
      <% if (errors != null && errors.get("nombre") != null) { %>
        <span class="error"><%= errors.get("nombre") %></span>
      <% } %>
    </label>

    <label>Precio
      <input type="number" step="0.01" name="precio" value="<%=formPrecio%>" required />
      <% if (errors != null && errors.get("precio") != null) { %>
        <span class="error"><%= errors.get("precio") %></span>
      <% } %>
    </label>

    <label>Costo
      <input type="number" step="0.01" name="costo" value="<%=formCosto%>" required />
      <% if (errors != null && errors.get("costo") != null) { %>
        <span class="error"><%= errors.get("costo") %></span>
      <% } %>
    </label>

    <label>Proveedor ID
      <input type="number" name="proveedor_id" value="<%=formProveedorId%>" required />
      <% if (errors != null && errors.get("proveedor_id") != null) { %>
        <span class="error"><%= errors.get("proveedor_id") %></span>
      <% } %>
    </label>

    <button type="submit">Guardar</button>
    <a href="<%=request.getContextPath()%>/">Volver al menú</a>
  </form>
</body>
</html>
