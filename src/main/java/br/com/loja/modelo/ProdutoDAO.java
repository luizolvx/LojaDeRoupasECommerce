package br.com.loja.modelo; 

// Importa sua classe de conexão real!
import classes.database.DBConnection; 

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Importe sua classe Produto (embora já deva estar no mesmo pacote)
// import br.com.loja.modelo.Produto; 

// ATENÇÃO: A sua classe de conexão ConexaoDB do projeto Desktop deve ser importada
// Se sua classe de conexão no projeto Web se chama DBConnection (no pacote classes.database), 
// o import deve ser:
// import classes.database.DBConnection; 

public class ProdutoDAO {

    // 1. MÉTODO INSERIR (CREATE) - Ajuste: Lançar RuntimeException em caso de erro
    public void inserir(Produto produto) {
        String sql = "INSERT INTO produtos (nome, marca, modelo, idCategoria, descricao, unidadeMedida, cor, tamanho, preco, ativo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBConnection.getConnection(); // Usando sua classe de conexão
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getMarca());
            stmt.setString(3, produto.getModelo());
            stmt.setInt(4, produto.getIdCategoria());
            stmt.setString(5, produto.getDescricao());
            stmt.setString(6, produto.getUnidadeMedida());
            stmt.setString(7, produto.getCor());
            stmt.setString(8, produto.getTamanho());
            stmt.setBigDecimal(9, produto.getPreco());
            stmt.setString(10, produto.getAtivo());

            stmt.executeUpdate();
            // Em ambiente Web, System.out.println() não é mais o padrão, mas deixamos.
            // System.out.println("Produto '" + produto.getNome() + "' inserido com sucesso!");

        } catch (SQLException e) {
            // RELANÇANDO a exceção para que o Servlet de controle a capture
            throw new RuntimeException("Erro ao inserir produto: " + e.getMessage(), e);
        } finally {
            // Seus métodos de fechar recursos estão corretos (try-with-resources seria uma alternativa moderna)
            try {
                if (stmt != null) stmt.close();
                DBConnection.closeConnection(conn); 
            } catch (SQLException e) {
                // Apenas logar o erro de fechar recursos, sem interromper o fluxo principal
                System.err.println("Erro ao fechar recursos (inserir): " + e.getMessage());
            }
        }
    }

    // 2. MÉTODO LISTAR (READ ALL) - Renomeado de listarTodos para listar, para ser simples
    public List<Produto> listar() {
        List<Produto> produtos = new ArrayList<>();
        // Removi 'ativo' do select para ser mais simples. Se precisar, adicione-o no WHERE.
        String sql = "SELECT idProduto, nome, marca, modelo, idCategoria, descricao, unidadeMedida, cor, tamanho, preco, ativo FROM produtos ORDER BY nome";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection(); // Usando sua classe de conexão
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setIdProduto(rs.getInt("idProduto"));
                produto.setNome(rs.getString("nome"));
                produto.setMarca(rs.getString("marca"));
                produto.setModelo(rs.getString("modelo"));
                produto.setIdCategoria(rs.getInt("idCategoria"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setUnidadeMedida(rs.getString("unidadeMedida"));
                produto.setCor(rs.getString("cor"));
                produto.setTamanho(rs.getString("tamanho"));
                produto.setPreco(rs.getBigDecimal("preco"));
                produto.setAtivo(rs.getString("ativo"));
                produtos.add(produto);
            }

        } catch (SQLException e) {
            // RELANÇANDO a exceção
            throw new RuntimeException("Erro ao listar produtos: " + e.getMessage(), e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                DBConnection.closeConnection(conn); 
            } catch (SQLException e) {
                 System.err.println("Erro ao fechar recursos (listar): " + e.getMessage());
            }
        }
        return produtos;
    }
    
    // 3. MÉTODO BUSCAR POR ID (READ SINGLE) - Ajuste: Lançar RuntimeException
    public Produto buscarPorId(int id) {
        Produto produto = null;
        String sql = "SELECT idProduto, nome, marca, modelo, idCategoria, descricao, unidadeMedida, cor, tamanho, preco, ativo FROM produtos WHERE idProduto = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                 produto = new Produto();
                 // Lógica de popular o objeto copiada
                 produto.setIdProduto(rs.getInt("idProduto"));
                 produto.setNome(rs.getString("nome"));
                 produto.setMarca(rs.getString("marca"));
                 produto.setModelo(rs.getString("modelo"));
                 produto.setIdCategoria(rs.getInt("idCategoria"));
                 produto.setDescricao(rs.getString("descricao"));
                 produto.setUnidadeMedida(rs.getString("unidadeMedida"));
                 produto.setCor(rs.getString("cor"));
                 produto.setTamanho(rs.getString("tamanho"));
                 produto.setPreco(rs.getBigDecimal("preco"));
                 produto.setAtivo(rs.getString("ativo"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto por ID: " + e.getMessage(), e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                DBConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos (buscar): " + e.getMessage());
            }
        }
        return produto;
    }

    // 4. MÉTODO ATUALIZAR (UPDATE) - Ajuste: Lançar RuntimeException
    public void atualizar(Produto produto) {
        String sql = "UPDATE produtos SET nome = ?, marca = ?, modelo = ?, idCategoria = ?, descricao = ?, unidadeMedida = ?, cor = ?, tamanho = ?, preco = ?, ativo = ? WHERE idProduto = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getMarca());
            stmt.setString(3, produto.getModelo());
            stmt.setInt(4, produto.getIdCategoria());
            stmt.setString(5, produto.getDescricao());
            stmt.setString(6, produto.getUnidadeMedida());
            stmt.setString(7, produto.getCor());
            stmt.setString(8, produto.getTamanho());
            stmt.setBigDecimal(9, produto.getPreco());
            stmt.setString(10, produto.getAtivo());
            stmt.setInt(11, produto.getIdProduto());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                 // System.out.println("Produto de ID " + produto.getIdProduto() + " atualizado com sucesso!");
            } else {
                 // System.out.println("Nenhum produto encontrado...");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar produto: " + e.getMessage(), e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                DBConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos (atualizar): " + e.getMessage());
            }
        }
    }

    // 5. MÉTODO DELETAR (DELETE) - Ajuste: Lançar RuntimeException
    public void deletar(int id) {
        String sql = "DELETE FROM produtos WHERE idProduto = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                // System.out.println("Produto de ID " + id + " deletado com sucesso!");
            } else {
                // System.out.println("Nenhum produto encontrado...");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar produto: " + e.getMessage(), e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                DBConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos (deletar): " + e.getMessage());
            }
        }
    }
}	