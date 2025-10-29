package br.com.fiap.app;

import br.com.fiap.model.Menssagem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenssagemBanco {
    private Connection con;

    public MenssagemBanco(Connection con) {
        this.con = con;
    }

    public void adicionarMenssagem(Menssagem menssagem) throws SQLException {
        // Pega o ID da categoria, ou cria se não existir
        Integer categoriaId = null;
        if (menssagem.getCategoria() != null && !menssagem.getCategoria().isEmpty()) {
            categoriaId = getOrCreateCategoriaId(menssagem.getCategoria());
        }

        String sql = "INSERT INTO ch_Menssagens (pergunta, resposta, categoria_id) VALUES (?, ?, ?) RETURNING id INTO ?";
        oracle.jdbc.OraclePreparedStatement stmt =
                (oracle.jdbc.OraclePreparedStatement) con.prepareStatement(sql);

        stmt.setString(1, menssagem.getPergunta());
        stmt.setString(2, menssagem.getResposta());

        if (categoriaId != null) {
            stmt.setInt(3, categoriaId);
        } else {
            stmt.setNull(3, java.sql.Types.INTEGER);
        }

        stmt.registerReturnParameter(4, java.sql.Types.INTEGER);
        stmt.executeUpdate();

        ResultSet rs = stmt.getReturnResultSet();
        if (rs.next()) {
            menssagem.setId(rs.getInt(1));
        }

        rs.close();
        stmt.close();
    }


    private Integer getOrCreateCategoriaId(String nomeCategoria) throws SQLException {
        String select = "SELECT id FROM ch_Categorias WHERE LOWER(nome) = LOWER(?)";
        PreparedStatement stmt = con.prepareStatement(select);
        stmt.setString(1, nomeCategoria);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int id = rs.getInt("id");
            rs.close();
            stmt.close();
            return id;
        }
        rs.close();
        stmt.close();

        String insert = "INSERT INTO ch_Categorias (nome) VALUES (?) RETURNING id INTO ?";
        oracle.jdbc.OraclePreparedStatement insertStmt =
                (oracle.jdbc.OraclePreparedStatement) con.prepareStatement(insert);
        insertStmt.setString(1, nomeCategoria);
        insertStmt.registerReturnParameter(2, java.sql.Types.INTEGER);
        insertStmt.executeUpdate();
        ResultSet rs2 = insertStmt.getReturnResultSet();
        rs2.next();
        int newId = rs2.getInt(1);
        rs2.close();
        insertStmt.close();
        return newId;
    }

    public List<Menssagem> buscarPorPergunta(String pergunta) throws SQLException {
        String sql = """
            SELECT m.id, m.pergunta, m.resposta, c.nome AS categoria
            FROM ch_Menssagens m
            LEFT JOIN ch_Categorias c ON m.categoria_id = c.id
            WHERE UTL_MATCH.EDIT_DISTANCE(LOWER(m.pergunta), LOWER(?)) <= 5
            ORDER BY UTL_MATCH.EDIT_DISTANCE(LOWER(m.pergunta), LOWER(?))
        """;

        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, pergunta);
        stmt.setString(2, pergunta);
        ResultSet rs = stmt.executeQuery();

        List<Menssagem> menssagens = new ArrayList<>();
        while (rs.next()) {
            String categoria = rs.getString("categoria");
            menssagens.add(new Menssagem(
                    rs.getInt("id"),
                    rs.getString("pergunta"),
                    rs.getString("resposta"),
                    categoria != null ? categoria : "Sem categoria"
            ));
        }

        rs.close();
        stmt.close();
        return menssagens;
    }

    public List<Menssagem> listarMenssagens() throws SQLException {
        String sql = """
            SELECT m.id, m.pergunta, m.resposta, c.nome AS categoria
            FROM ch_Menssagens m
            LEFT JOIN ch_Categorias c ON m.categoria_id = c.id
            ORDER BY m.id
        """;

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<Menssagem> menssagens = new ArrayList<>();
        while (rs.next()) {
            String categoria = rs.getString("categoria");
            menssagens.add(new Menssagem(
                    rs.getInt("id"),
                    rs.getString("pergunta"),
                    rs.getString("resposta"),
                    categoria != null ? categoria : "Sem categoria"
            ));
        }
        rs.close();
        stmt.close();
        return menssagens;
    }

    public Menssagem buscarPorId(int id) throws SQLException {
        String sql = """
            SELECT m.id, m.pergunta, m.resposta, c.nome AS categoria
            FROM ch_Menssagens m
            LEFT JOIN ch_Categorias c ON m.categoria_id = c.id
            WHERE m.id = ?
        """;
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        Menssagem m = null;
        if (rs.next()) {
            String categoria = rs.getString("categoria");
            m = new Menssagem(
                    rs.getInt("id"),
                    rs.getString("pergunta"),
                    rs.getString("resposta"),
                    categoria != null ? categoria : "Sem categoria"
            );
        }
        rs.close();
        stmt.close();
        return m;
    }

    public void atualizarMenssagem(Menssagem menssagem) throws SQLException {
        String sql = "UPDATE ch_Menssagens SET pergunta = ?, resposta = ? WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, menssagem.getPergunta());
        stmt.setString(2, menssagem.getResposta());
        stmt.setInt(3, menssagem.getId());
        stmt.executeUpdate();
        stmt.close();
    }

    public void deletarMenssagem(int id) throws SQLException {
        String sql = "DELETE FROM ch_Menssagens WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        stmt.close();
    }
}
