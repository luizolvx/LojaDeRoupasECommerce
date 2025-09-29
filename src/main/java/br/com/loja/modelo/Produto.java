package br.com.loja.modelo;

// Esta é uma classe Java simples (POJO) que espelha os dados da tabela 'produto'.
public class Produto {
    
    // ATRIBUTOS (Devem ser privados)
    private int id;
    private String nome;
    private double preco;
    private String categoria;
    
    // ----------------------------------------------------------------------
    // 1. CONSTRUTORES
    // ----------------------------------------------------------------------
    
    // Construtor vazio: Essencial para que o DBQuery e o Gson consigam criar
    // uma nova instância da classe sem passar parâmetros.
    public Produto() {
    }

    // Construtor com parâmetros (opcional, mas útil)
    public Produto(int id, String nome, double preco, String categoria) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
    }
    
    // ----------------------------------------------------------------------
    // 2. GETTERS e SETTERS
    // ----------------------------------------------------------------------
    
    // Getters e Setters são métodos públicos que permitem acessar e modificar 
    // os atributos privados, conforme exigido pelas boas práticas do Java.
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}