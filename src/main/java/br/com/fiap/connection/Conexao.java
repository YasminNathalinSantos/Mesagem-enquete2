package br.com.fiap.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public Connection getConnection() throws SQLException {
        String jdbc = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl";
        return DriverManager.getConnection(jdbc, "RM561365", "241006");
    }
}
