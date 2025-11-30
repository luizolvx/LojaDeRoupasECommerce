package br.com.loja.controle;

import br.com.loja.modelo.Produto;
import br.com.loja.modelo.ProdutoDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Esta anotação mapeia a URL /listaProdutos para este Servlet
@WebServlet("/listaProdutos") 
public class ListaProdutosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // O método doGet é chamado quando o usuário acessa a URL diretamente no navegador
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // 1. CHAMA O MODELO/DAO (SUA LÓGICA DE DADOS MIGRADA)
            ProdutoDAO dao = new ProdutoDAO();
            
            // Chama o seu método listar()
            List<Produto> listaProdutos = dao.listar();

            // 2. PREPARA OS DADOS PARA A VISUALIZAÇÃO
            // Coloca a lista de produtos no objeto 'request' com o nome "produtos"
            request.setAttribute("produtos", listaProdutos);

            // 3. ENCAMINHA PARA A VISUALIZAÇÃO (VIEW - JSP)
            // O código continua a execução na página listagem.jsp
            RequestDispatcher rd = request.getRequestDispatcher("/listagem.jsp");
            rd.forward(request, response);
            
        } catch (RuntimeException e) {
            // Se houver um erro no DAO (ex: falha na conexão com o MySQL)
            System.err.println("Erro Crítico no Servlet: " + e.getMessage());
            // Envia uma resposta de erro HTTP 500 para o navegador
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                                "Erro ao carregar lista de produtos. Verifique a conexão com o banco de dados. Detalhe: " + e.getMessage());
        }
    }
}