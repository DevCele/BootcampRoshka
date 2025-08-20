-- Top clientes con más facturas
select c.id,
       c.nombre,
       c.apellido,
       count(f.id) as total_facturas
from cliente c
join factura f on f.cliente_id = c.id
group by c.id, c.nombre, c.apellido
order by total_facturas desc
limit 10;

-- Top clientes que más gastaron
select c.id,
	   c.nombre,
	   c.apellido,
	   sum(p.precio * fd.cantidad)::numeric(18,2) as cantidad_total
from cliente c
join factura f on f.cliente_id = c.id
join factura_detalle fd on fd.factura_id = f.id
join producto p on p.id = fd.producto_id
group by c.id, c.nombre, c.apellido
order by cantidad_total desc
limit 10;

-- Top monedas más utilizadas
select m.id, 
	   m.nombre as moneda,
	   count (f.id) as cantidad_facturas
from moneda m
join factura f on f.moneda_id = m.id
group by m.id, m.nombre
order by cantidad_facturas desc
limit 10;

--Top proveedor de productos
select pr.id,
	   pr.nombre as proveedor,
	   count (p.id) as cantidad_productos
from proveedor pr
join producto p on p.proveedor_id = pr.id
group by pr.id, pr.nombre
order by cantidad_productos desc
limit 10;

--Productos mas vendidos
select p.id,
	   p.nombre,
	   sum(fd.cantidad)::numeric(18,2) as unidades_vendidas
from producto p
join factura_detalle fd on fd.producto_id = p.id
group by p.id, p.nombre
order by unidades_vendidas desc
limit 10;

--Productos menos vendidos 
select p.id,
	   p.nombre,
	   coalesce(sum(fd.cantidad),0)::numeric(18,2) as unidades_vendidas
from producto p
join factura_detalle fd on fd.producto_id = p.id
group by p.id, p.nombre
order by unidades_vendidas asc, p.nombre
limit 10;

--Detalle de una factura especifica 
select f.id,
       f.fecha_emision,
	   c.nombre as cliente_nombre,
	   c.apellido as cliente_apellido,
	   ft.nombre as factura_tipo,
	   p.nombre as producto_nombre,
	   fd.cantidad
from factura f
join cliente c on c.id = f.cliente_id
join factura_tipo ft on ft.id = f.factura_tipo_id
join factura_detalle fd on fd.factura_id = f.id
join producto p on p.id = fd.producto_id
where f.id = 1 --reemplazar por el id que se desea consultar 

select * from factura_detalle --consulta para ver los id ya cargados en la tabla 

--Ordenadas por total
select f.id as factura_id,
       f.fecha_emision,
       c.nombre  as cliente_nombre,
       c.apellido as cliente_apellido,
       sum(p.precio * fd.cantidad)::numeric(18,2) as total_factura
from factura f
join cliente c on c.id = f.cliente_id
join factura_detalle fd on fd.factura_id = f.id
join producto p on p.id = fd.producto_id
group by f.id, f.fecha_emision, c.nombre, c.apellido
order by total_factura desc;

-- IVA 10%
select f.id as factura_id,
  round(sum(p.precio * fd.cantidad)::numeric, 2) as total_sin_iva,
  round(sum(p.precio * fd.cantidad)::numeric * 0.10::numeric, 2) as iva_10,
  round(sum(p.precio * fd.cantidad)::numeric * 1.10::numeric, 2) as total_con_iva
from factura f
join factura_detalle fd on fd.factura_id = f.id
join producto p on p.id = fd.producto_id
group by f.id
order by f.id;