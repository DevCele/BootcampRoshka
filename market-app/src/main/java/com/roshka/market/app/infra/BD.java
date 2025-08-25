package com.roshka.market.app.infra;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class BD {

    private static String URL;
    private static String USER;
    private static String PASS;
    private static String DRIVER;

    // Se ejecuta una vez al cargar la clase
    static {
        try (InputStream in = BD.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) throw new RuntimeException("No se encontró db.properties en resources");

            Properties p = new Properties();
            p.load(in);

            URL = p.getProperty("db.url");
            USER = p.getProperty("db.user");
            PASS = p.getProperty("db.password");
            DRIVER = p.getProperty("db.driver");

            // Cargar driver de PostgreSQL
            Class.forName(DRIVER);

        } catch (Exception e) {
            throw new RuntimeException("Error al inicializar la conexión a la BD", e);
        }
    }

    public static Connection get() throws java.sql.SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
