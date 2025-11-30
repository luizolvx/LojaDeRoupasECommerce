<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.loja.modelo.Produto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.text.NumberFormat" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Lista de Produtos - Sua Loja</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

    <div class="container mt-5">
        <h1 class="mb-4">Produtos em Estoque (Controle)</h1>
        <p>Dados vindos da sua lógica migrada e banco de dados MySQL.</p>
        <hr>

        <% 
            // 1. Resgata a lista de produtos que o Servlet enviou ("produtos")
            List<Produto> lista = (List<Produto>) request.getAttribute("produtos");
            
            // Objeto para formatar o preço em moeda BRL (R$)
            NumberFormat nf = NumberFormat.getCurrencyInstance(new java.util.Locale("pt", "BR"));
            
            if (lista != null && !lista.isEmpty()) {
        %>

        <table class="table table-striped table-hover">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Marca</th>
                    <th>Preço</th>
                    <th>Ativo</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    // 2. Itera sobre a lista de produtos (LOOP FOR)
                    for (Produto p : lista) {
                %>
                <tr>
                    <td><%= p.getIdProduto() %></td>
                    <td><%= p.getNome() %></td>
                    <td><%= p.getMarca() %></td>
                    
                    <%-- Formata o BigDecimal para a moeda R$ --%>
                    <td><%= nf.format(p.getPreco()) %></td> 
                    
                    <td><%= p.getAtivo() %></td>
                    <td>
                        <a href="editarProduto?id=<%= p.getIdProduto() %>" class="btn btn-sm btn-warning">Editar</a>
                        <a href="deletarProduto?id=<%= p.getIdProduto() %>" class="btn btn-sm btn-danger">Excluir</a>
                    </td>
                </tr>
                <%
                    } // Fim do loop
                %>
            </tbody>
        </table>
        
        <% } else { %>
            <div class="alert alert-info">Nenhum produto encontrado no banco de dados.</div>
        <% } %>
        
        <a href="index.html" class="btn btn-primary mt-3">Voltar à Home</a>
    </div>

</body>
</html>