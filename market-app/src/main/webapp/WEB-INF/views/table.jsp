<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title><%=request.getAttribute("title")%></title>
  <style>
    body { font-family: Arial, sans-serif; margin: 24px; }
    table { border-collapse: collapse; width: 100%; }
    th, td { border: 1px solid #ddd; padding: 8px; }
    th { background: #f4f4f4; text-align: left; }
    .actions { margin: 12px 0; }
  </style>
</head>
<body>
  <h2><%=request.getAttribute("title")%></h2>

<%
  // flag que define si se muestra el control de "limit"
  boolean supportsLimit = false;
  Object flag = request.getAttribute("supportsLimit");
  if (flag instanceof Boolean) supportsLimit = (Boolean) flag;
  
  
  //flag que define si se muestra el control de "id"
  boolean supportsId = false;
  Object si = request.getAttribute("supportsId");
  if (si instanceof Boolean) supportsId = (Boolean) si;

  // valor de limit (si aplica)
  String limitParam = request.getParameter("limit");
  String limitValue = (limitParam == null || limitParam.isBlank()) ? "10" : limitParam;
  
 //valor de id (si aplica)
  String idParam = request.getParameter("id");
  String idValue = (idParam == null || idParam.isBlank())
      ? (request.getAttribute("currentId") == null ? "" : String.valueOf(request.getAttribute("currentId")))
      : idParam;
%>

  <div class="actions">
    <form method="get">
        <% if (supportsId) { %>
        <label>ID factura:
          <input type="number" name="id" min="1" value="<%= idValue %>" />
        </label>
      <% } %>

      <% if (supportsLimit) { %>
        <label>Límite:
          <input type="number" name="limit" min="1" value="<%= limitValue %>"/>
        </label>
      <% } %>

      <% if (supportsId || supportsLimit) { %>
        <button type="submit">Aplicar</button>
      <% } %>
      
      <a href="<%=request.getContextPath()%>/">Volver al menú</a>
    </form>
  </div>

<%
  List<Map<String,Object>> data = (List<Map<String,Object>>) request.getAttribute("data");
  if (data == null || data.isEmpty()) {
%>
  <p>No hay datos para mostrar.</p>
<%
  } else {
    Map<String,Object> first = data.get(0);
%>
  <table>
    <thead>
      <tr>
        <th>Nro</th>
<%
      for (String k : first.keySet()) {
%>        <th><%=k%></th>
<%
      }
%>
      </tr>
    </thead>
    <tbody>
<%
      int nro = 1;
      for (Map<String,Object> row : data) {
%>      <tr>
        <td><%=nro++%></td>
<%
        for (Object v : row.values()) {
%>        <td><%= v %></td>
<%
        }
%>      </tr>
<%
      }
%>
    </tbody>
  </table>
<%
  }
%>
</body>
</html>
