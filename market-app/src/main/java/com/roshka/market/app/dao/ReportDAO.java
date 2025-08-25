package com.roshka.market.app.dao;

import com.roshka.market.app.infra.BD;
import java.sql.*;
import java.util.*;

public class ReportDAO {

    /* 1) Top clientes con más facturas */
     public List<Map<String,Object>> topClientesMasFacturas(int limit) {
        String sql = """
            select c.id, c.nombre, c.apellido, count(f.id) as total_facturas
            from cliente c
            join factura f on f.cliente_id = c.id
            group by c.id, c.nombre, c.apellido
            order by total_facturas desc
            limit ?
        """;
        List<Map<String,Object>> out = new ArrayList<>();
        try (Connection cn = BD.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String,Object> row = new LinkedHashMap<>();
                    row.put("id", rs.getInt("id"));
                    row.put("nombre", rs.getString("nombre"));
                    row.put("apellido", rs.getString("apellido"));
                    row.put("total_facturas", rs.getInt("total_facturas"));
                    out.add(row);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error en topClientesMasFacturas", e);
        }
        return out;
    }

    /* 2) Top clientes que más gastaron */
    public List<Map<String,Object>> topClientesMayorGasto(int limit) {
        String sql = """
            select c.id, c.nombre, c.apellido,
                   sum(p.precio * fd.cantidad)::numeric(18,2) as cantidad_total
            from cliente c
            join factura f on f.cliente_id = c.id
            join factura_detalle fd on fd.factura_id = f.id
            join producto p on p.id = fd.producto_id
            group by c.id, c.nombre, c.apellido
            order by cantidad_total desc
            limit ?
        """;
        return queryList(sql, ps -> ps.setInt(1, limit));
    }

    /* 3) Top monedas más utilizadas */
    public List<Map<String,Object>> topMonedasMasUsadas(int limit) {
        String sql = """
            select m.id, m.nombre as moneda, count(f.id) as cantidad_facturas
            from moneda m
            join factura f on f.moneda_id = m.id
            group by m.id, m.nombre
            order by cantidad_facturas desc
            limit ?
        """;
        return queryList(sql, ps -> ps.setInt(1, limit));
    }

    /* 4) Top proveedores por cantidad de productos */
    public List<Map<String,Object>> topProveedores(int limit) {
        String sql = """
            select pr.id, pr.nombre as proveedor, count(p.id) as cantidad_productos
            from proveedor pr
            join producto p on p.proveedor_id = pr.id
            group by pr.id, pr.nombre
            order by cantidad_productos desc
            limit ?
        """;
        return queryList(sql, ps -> ps.setInt(1, limit));
    }

    /* 5) Productos más vendidos */
    public List<Map<String,Object>> productosMasVendidos(int limit) {
        String sql = """
            select p.id, p.nombre, sum(fd.cantidad)::numeric(18,2) as unidades_vendidas
            from producto p
            join factura_detalle fd on fd.producto_id = p.id
            group by p.id, p.nombre
            order by unidades_vendidas desc
            limit ?
        """;
        return queryList(sql, ps -> ps.setInt(1, limit));
    }

    /* 6) Productos menos vendidos */
    public List<Map<String,Object>> productosMenosVendidos(int limit) {
        String sql = """
            select p.id, p.nombre, coalesce(sum(fd.cantidad),0)::numeric(18,2) as unidades_vendidas
            from producto p
            left join factura_detalle fd on fd.producto_id = p.id
            group by p.id, p.nombre
            order by unidades_vendidas asc, p.nombre
            limit ?
        """;
        return queryList(sql, ps -> ps.setInt(1, limit));
    }

    /* 7) Detalle de una factura específica */
    public List<Map<String,Object>> detalleFactura(int facturaId) {
        String sql = """
            select f.id, f.fecha_emision,
                   c.nombre as cliente_nombre, c.apellido as cliente_apellido,
                   ft.nombre as factura_tipo,
                   p.nombre as producto_nombre,
                   fd.cantidad
            from factura f
            join cliente c on c.id = f.cliente_id
            join factura_tipo ft on ft.id = f.factura_tipo_id
            join factura_detalle fd on fd.factura_id = f.id
            join producto p on p.id = fd.producto_id
            where f.id = ?
        """;
        return queryList(sql, ps -> ps.setInt(1, facturaId));
    }

    /* 8) Facturas ordenadas por total */
    public List<Map<String,Object>> facturasOrdenadasPorTotal() {
        String sql = """
            select f.id as factura_id, f.fecha_emision,
                   c.nombre as cliente_nombre, c.apellido as cliente_apellido,
                   sum(p.precio * fd.cantidad)::numeric(18,2) as total_factura
            from factura f
            join cliente c on c.id = f.cliente_id
            join factura_detalle fd on fd.factura_id = f.id
            join producto p on p.id = fd.producto_id
            group by f.id, f.fecha_emision, c.nombre, c.apellido
            order by total_factura desc
        """;
        return queryList(sql, null);
    }

    /* 9) IVA 10% por factura */
    public List<Map<String,Object>> iva10PorFactura() {
        String sql = """
            select f.id as factura_id,
                   round(sum(p.precio * fd.cantidad)::numeric, 2) as total_sin_iva,
                   round(sum(p.precio * fd.cantidad)::numeric * 0.10::numeric, 2) as iva_10,
                   round(sum(p.precio * fd.cantidad)::numeric * 1.10::numeric, 2) as total_con_iva
            from factura f
            join factura_detalle fd on fd.factura_id = f.id
            join producto p on p.id = fd.producto_id
            group by f.id
            order by f.id
        """;
        return queryList(sql, null);
    }

    
    private interface Binder { void bind(PreparedStatement ps) throws Exception; }

    private List<Map<String,Object>> queryList(String sql, Binder binder) {
        List<Map<String,Object>> out = new ArrayList<>();
        try (Connection cn = BD.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            if (binder != null) binder.bind(ps);
            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData md = rs.getMetaData();
                int n = md.getColumnCount();
                while (rs.next()) {
                    Map<String,Object> row = new LinkedHashMap<>();
                    for (int i = 1; i <= n; i++) {
                        row.put(md.getColumnLabel(i), rs.getObject(i));
                    }
                    out.add(row);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error ejecutando consulta", e);
        }
        return out;
    }
}
