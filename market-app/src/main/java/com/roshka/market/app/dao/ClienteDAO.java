
package com.roshka.market.app.dao;

import com.roshka.market.app.infra.BD;
import com.roshka.market.app.model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {
    
    public Cliente insert(Cliente c) {
        String whoSql = "select current_database(), current_user";
        String sql = """
            INSERT INTO cliente (nombre, apellido, nro_cedula, telefono)
            VALUES (?, ?, ?, ?)
            RETURNING id
        """;

        try (Connection cn = BD.get()) {

            // --- LOG: dónde estoy conectado (BD y usuario) ---
            try (PreparedStatement who = cn.prepareStatement(whoSql);
                 ResultSet r = who.executeQuery()) {
                if (r.next()) {
                    System.out.println("[DB] database=" + r.getString(1) + " user=" + r.getString(2));
                }
            }

            // --- INSERT ---
            try (PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setString(1, c.getNombre());
                ps.setString(2, c.getApellido());
                ps.setString(3, c.getNroCedula());
                ps.setString(4, c.getTelefono());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int idGen = rs.getInt(1);
                        c.setId(idGen);
                        System.out.println("[DB] Insert OK, new id=" + idGen); // LOG del ID generado
                        return c;
                    } else {
                        throw new RuntimeException("No se obtuvo el ID generado");
                    }
                }
            }

        } catch (SQLException e) {
            // Log completo al Output/Tomcat
            e.printStackTrace();
            throw new RuntimeException("Error insertando cliente", e);
        }
    }
}
