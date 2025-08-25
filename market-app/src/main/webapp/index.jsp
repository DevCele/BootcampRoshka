<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>Market — Menú</title>
  <style>
    body { font-family: Arial, sans-serif; margin: 24px; }
    h1 { margin-bottom: 6px; }
    .grid { display: grid; grid-template-columns: 1fr 1fr; gap: 24px; }
    .card { border: 1px solid #ddd; border-radius: 8px; padding: 16px; }
    .card h2 { margin-top: 0; }
    ul { margin: 0; padding-left: 18px; }
    a { text-decoration: none; }
    a:hover { text-decoration: underline; }
    .muted { color:#666; font-size: 0.9rem; }
  </style>
</head>
<body>
  <h1>Market — Menú de reportes y formularios</h1>
  <p class="muted">Selecciona un reporte o un formulario.</p>

  <div class="grid">
    <!-- Reportes -->
    <div class="card">
      <h2>Reportes (Tops)</h2>
      <ul>
        <li><a href="<%=request.getContextPath()%>/reports/top-clientes">Top clientes por cantidad de facturas</a></li>
        <li><a href="<%=request.getContextPath()%>/reports/top-gasto">Top clientes que más gastaron</a></li>
        <li><a href="<%=request.getContextPath()%>/reports/top-monedas">Top monedas más utilizadas</a></li>
        <li><a href="<%=request.getContextPath()%>/reports/top-proveedores">Top proveedores por cantidad de productos</a></li>
        <li><a href="<%=request.getContextPath()%>/reports/productos-mas">Productos más vendidos</a></li>
        <li><a href="<%=request.getContextPath()%>/reports/productos-menos">Productos menos vendidos</a></li>
        <li><a href="<%=request.getContextPath()%>/reports/factura-detalle?id=1">Detalle de una factura </a></li>
        <li><a href="<%=request.getContextPath()%>/reports/facturas-orden-total">Facturas ordenadas por total</a></li>
        <li><a href="<%=request.getContextPath()%>/reports/iva-10">IVA 10% por factura</a></li>
      </ul>
    </div>

    <!-- Formularios -->
   <div class="card">
      <h2>Formularios (ABM)</h2>
      <ul>
        <li><a href="<%=request.getContextPath()%>/forms/cliente/new">Nuevo cliente</a></li>
        <li><a href="<%=request.getContextPath()%>/forms/producto/new">Nuevo producto</a></li>
        <li><a href="<%=request.getContextPath()%>/forms/proveedor/new">Nuevo proveedor</a></li>
       <!-- <li><a href="<%=request.getContextPath()%>/forms/factura/new">Nueva factura (con detalles)</a></li>-->
      </ul>
    </div>
  </div>

  <!--<p class="muted">Debug: <a href="<%=request.getContextPath()%>/db/health">Probar conexión a BD</a></p>-->
</body>
</html>
