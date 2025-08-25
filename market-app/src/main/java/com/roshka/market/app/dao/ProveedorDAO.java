package com.roshka.market.app.dao;

import com.roshka.market.app.infra.BD;
import com.roshka.market.app.model.Proveedor;

import java.sql.*;

public class ProveedorDAO {

    public Proveedor insert(Proveedor p) {
        String whoSql = "select current_database(), current_user";
        String sql = """
            INSERT INTO proveedor (nombre, ruc, pais_id)
            VALUES (?, ?, ?)
            RETURNING id
        """;
        try (Connection cn = BD.get()) {

            try (PreparedStatement who = cn.prepareStatement(whoSql);
                 ResultSet r = who.executeQuery()) {
                if (r.next()) {
                    System.out.println("[DB] database=" + r.getString(1) + " user=" + r.getString(2));
                }
            }

            try (PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setString(1, p.getNombre());
                ps.setString(2, p.getRuc());
                if (p.getPaisId() == null) ps.setNull(3, Types.INTEGER);
                else ps.setInt(3, p.getPaisId());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        p.setId(rs.getInt(1));
                        System.out.println("[DB] Proveedor insert OK, id=" + p.getId());
                        return p;
                    }
                    throw new RuntimeException("No se obtuvo ID generado");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error insertando proveedor", e);
        }
    }
}
