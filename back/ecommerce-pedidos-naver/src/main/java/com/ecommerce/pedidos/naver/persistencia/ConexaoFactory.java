package com.ecommerce.pedidos.naver.persistencia;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoFactory {

    private static final String URL = "jdbc:h2:mem:ecommerce_naver;DB_CLOSE_DELAY=-1";
    private static final String USUARIO = "sa";
    private static final String SENHA = "";

    public static Connection obterConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    public static void inicializarSchema(Connection conn) throws Exception {
        InputStream is = ConexaoFactory.class.getResourceAsStream("/schema.sql");
        if (is == null) {
            throw new IllegalStateException("Arquivo /schema.sql não encontrado em src/main/resources");
        }
        String sql = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        try (Statement st = conn.createStatement()) {
            st.execute(sql);
        }
    }
}