<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>Market — Menú</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/base.css">
</head>
<body>
    <div class="grid">
  <h1>Market — Menú de reportes y formularios</h1>
  <p class="muted">Selecciona un reporte o un formulario.</p>
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
