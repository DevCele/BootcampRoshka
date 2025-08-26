<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>Nuevo cliente</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/base.css">
</head>
<body>
  <h2>Nuevo cliente</h2>

  <% Boolean ok = (Boolean) request.getAttribute("ok"); if (ok != null && ok) { %>
    <div class="ok">Cliente creado correctamente. ID = <%= request.getAttribute("newId") %></div>
  <% } %>

  <%
    java.util.Map<String,String> errors = (java.util.Map<String,String>) request.getAttribute("errors");
    String formNombre    = (String) request.getAttribute("formNombre");    if (formNombre == null) formNombre = "";
    String formApellido  = (String) request.getAttribute("formApellido");  if (formApellido == null) formApellido = "";
    String formNroCedula = (String) request.getAttribute("formNroCedula"); if (formNroCedula == null) formNroCedula = "";
    String formTelefono  = (String) request.getAttribute("formTelefono");  if (formTelefono == null) formTelefono = "";
  %>

  <form method="post">
    <label>Nombre
      <input type="text" name="nombre" value="<%=formNombre%>" required />
      <% if (errors != null && errors.get("nombre") != null) { %>
        <div class="error"><%= errors.get("nombre") %></div>
      <% } %>
    </label>

    <label>Apellido
      <input type="text" name="apellido" value="<%=formApellido%>" required />
      <% if (errors != null && errors.get("apellido") != null) { %>
        <div class="error"><%= errors.get("apellido") %></div>
      <% } %>
    </label>

    <label>Nro. cédula
      <input id="nro_cedula" name="nro_cedula" required
       placeholder="123456789-0"
       pattern="^\d{1,12}-\d{1}$" maxlength="14" title="Use 123456789-0" />
      <% if (errors != null && errors.get("nro_cedula") != null) { %>
        <div class="error"><%= errors.get("nro_cedula") %></div>
      <% } %>
    </label>

    <label>Teléfono
      <input id="telefono" name="telefono"
       placeholder="123-456-7890"
       pattern="^\d{3}-\d{3}-\d{4}$" maxlength="12" title="Use 123-456-7890" />
      <% if (errors != null && errors.get("telefono") != null) { %>
        <div class="error"><%= errors.get("telefono") %></div>
      <% } %>
    </label>

    
      <button type="submit">Guardar</button>
      <a href="<%=request.getContextPath()%>/">Volver al menú</a>
    
  </form>
</body>
</html>
