import javax.print.DocFlavor;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Consultas {
    //Top clientes con mas facturas
    public static void topClientesConMasFacturas (int limit) throws SQLException {
        String sql= """
                select c.id,
                       c.nombre,
                       c.apellido,
                       count(f.id) as total_facturas
                from cliente c
                join factura f on f.cliente_id = c.id
                group by c.id, c.nombre, c.apellido
                order by total_facturas desc
                limit ?;
                """;
        try(Connection cn = DB.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql)){
            ps.setInt(1, limit);
            try(ResultSet rs = ps.executeQuery()){
                System.out.println("== Top clientes con mas facturas: ==");
                while(rs.next()) {
                    System.out.printf("%d - %s %s | facturas=%d%n",
                            rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido"),
                            rs.getInt("total_facturas"));
                }
            }
        }
    }
    //Top clientes que más gastaron
    public static void topClientesQueMasGastaron(int limit) throws SQLException {
        String sql =  """
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
                limit ?;
                """;
        try (Connection cn = DB.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try(ResultSet rs = ps.executeQuery()){
                System.out.println("== Top Clientes que mas gastaron: ==");
                while(rs.next()){
                    System.out.printf("%d - %s %s | facturas=%s%n",
                            rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido"),
                            rs.getBigDecimal("cantidad_total"));
                }
            }
        }
    }

    //Top monedas mas usadas
    public static void topMonedasMasUsadas(int limit) throws SQLException {
        String sql = """
                select m.id,
                	   m.nombre as moneda,
                	   count (f.id) as cantidad_facturas
                from moneda m
                join factura f on f.moneda_id = m.id
                group by m.id, m.nombre
                order by cantidad_facturas desc
                limit ?;
                """;
        try(Connection cn = DB.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try(ResultSet rs = ps.executeQuery()){
                System.out.println("== Top monedad mas usadas: ==");
                while(rs.next()){
                    System.out.printf("%d - %s | facturas= %d%n",
                            rs.getInt("id"), rs.getString("moneda"), rs.getInt("cantidad_facturas"));
                }
            }
        }
    }

    public static void topProveedoresDeProductos(int limit) throws SQLException{
        String sql = """
                select pr.id,
                	   pr.nombre as proveedor,
                	   count (p.id) as cantidad_productos
                from proveedor pr
                join producto p on p.proveedor_id = pr.id
                group by pr.id, pr.nombre
                order by cantidad_productos desc
                limit ?;
                """;
        try(Connection cn = DB.getConnection();
        PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try(ResultSet rs = ps.executeQuery()) {
                System.out.println(" == Top Proveedores: ==");
                while(rs.next()){
                    System.out.printf(" %d - %s | %d%n",
                            rs.getInt("id"), rs.getString("proveedor"), rs.getInt("cantidad_productos"));
                }
            }
        }
    }

    public static void productosMasVendidos(int limit) throws SQLException{
        String sql = """
                select p.id,
                	   p.nombre,
                	   sum(fd.cantidad)::numeric(18,2) as unidades_vendidas
                from producto p
                join factura_detalle fd on fd.producto_id = p.id
                group by p.id, p.nombre
                order by unidades_vendidas desc
                limit ?
                """;
        try(Connection cn = DB.getConnection();
        PreparedStatement ps = cn.prepareStatement(sql)){
            ps.setInt(1, limit);
            try(ResultSet rs = ps.executeQuery()){
                System.out.println("== Productos mas Vendidos: ==");
                while (rs.next()){
                    System.out.printf("%d - %s | %d%n",
                    rs.getInt("id"), rs.getString("nombre"), rs.getInt("unidades_vendidas"));
                }
            }
        }
    }

    public static void productosMenosVendidos(int limit) throws SQLException{
        String sql = """
                select p.id,
                	   p.nombre,
                	   coalesce(sum(fd.cantidad),0)::numeric(18,2) as unidades_vendidas
                from producto p
                left join factura_detalle fd on fd.producto_id = p.id
                group by p.id, p.nombre
                order by unidades_vendidas asc, p.nombre
                limit ?
                """;
        try(Connection cn = DB.getConnection();
        PreparedStatement ps = cn.prepareStatement(sql)){
            ps.setInt(1, limit);
            try(ResultSet rs = ps.executeQuery()){
                System.out.println("== Productos Menos Vendidos: ==");
                while(rs.next()){
                    System.out.printf("%d - %s | %d%n",
                            rs.getInt("id"), rs.getString("nombre"), rs.getInt("unidades_vendidas"));
                }
            }
        }

    }

    public static void detalleFactura(int facturaId) throws SQLException{
        String sql = """
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
                where f.id = ?
                """;
        try(Connection cn = DB.getConnection();
        PreparedStatement ps = cn.prepareStatement(sql)){
            ps.setInt(1, facturaId);
            try(ResultSet rs = ps.executeQuery()){
                System.out.println("== Detalle de la Factura ==");
                while(rs.next()){
                    System.out.printf("%s | %s %s | %s | prod=%s | cant=%s%n",
                            rs.getDate("fecha_emision"),
                            rs.getString("cliente_nombre"),
                            rs.getString("cliente_apellido"),
                            rs.getString("factura_tipo"),
                            rs.getString("producto_nombre"),
                            rs.getBigDecimal("cantidad"));
                }
            }
        }
    }

    public static void facturasOrdenadasPorTotal() throws SQLException {
        String sql = """
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
            order by total_factura DESC
        """;
        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("== Facturas ordenadas por total: ==");
            while (rs.next()) {
                System.out.printf("Factura %d | %s %s | %s | total=%s%n",
                        rs.getInt("factura_id"),
                        rs.getString("cliente_nombre"),
                        rs.getString("cliente_apellido"),
                        rs.getDate("fecha_emision"),
                        rs.getBigDecimal("total_factura"));
            }
        }
    }

    public static void iva10PorFactura(int facturaId) throws SQLException {
        String sql = """
        select f.id AS factura_id,
          round(sum(p.precio * fd.cantidad)::numeric, 2) as total_sin_iva,
          round(sum(p.precio * fd.cantidad)::numeric * 0.10::numeric, 2) as iva_10,
          round(sum(p.precio * fd.cantidad)::numeric * 1.10::numeric, 2) as total_con_iva
        from factura f
        join factura_detalle fd on fd.factura_id = f.id
        join producto p on p.id = fd.producto_id
        where f.id = ?\s
        group by f.id
        order by f.id
   \s""";

        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, facturaId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("== Montos de la factura ==");
                    System.out.printf("Factura %d | sinIVA=%s | IVA=%s | total=%s%n",
                            rs.getInt("factura_id"),
                            rs.getBigDecimal("total_sin_iva"),
                            rs.getBigDecimal("iva_10"),
                            rs.getBigDecimal("total_con_iva"));
                } else {
                    System.out.println("⚠️ No existe la factura " + facturaId);
                }
            }
        }
    }
}
