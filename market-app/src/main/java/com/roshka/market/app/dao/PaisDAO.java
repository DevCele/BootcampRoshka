package com.roshka.market.app.dao;

import com.roshka.market.app.infra.BD;
import com.roshka.market.app.model.Pais;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaisDAO {
    public List<Pais> listAll() {
        String sql = "SELECT id, nombre FROM pais ORDER BY nombre";
        List<Pais> out = new ArrayList<>();
        try (Connection cn = BD.get();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(new Pais(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error listando países", e);
        }
        return out;
    }
}
