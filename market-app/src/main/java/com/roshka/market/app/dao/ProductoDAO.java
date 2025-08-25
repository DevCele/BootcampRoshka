package com.roshka.market.app.dao;

import com.roshka.market.app.infra.BD;
import com.roshka.market.app.model.Producto;

import java.sql.*;

public class ProductoDAO {

    public Producto insert(Producto p) {
        String sql = """
            INSERT INTO producto(nombre, precio, proveedor_id, costo)
            VALUES (?, ?, ?, ?)
            RETURNING id
        """;

        try (Connection cn = BD.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecio());
            ps.setInt(3, p.getProveedorId());
            ps.setDouble(4, p.getCosto());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p.setId(rs.getInt("id"));
                }
            }
            return p;
        } catch (SQLException e) {
            throw new RuntimeException("Error insertando producto", e);
        }
    }
}
