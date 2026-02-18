package com.example.demo.todo.model;

public class Todo {
    private Long id;
    private String titulo;
    private boolean concluida;
    private String descricao;
    private String dataCriacao;

    public Todo() {}

    public Todo(Long id, String titulo, boolean concluida, String descricao, String dataCriacao) {
        this.id = id;
        this.titulo = titulo;
        this.concluida = concluida;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public boolean isConcluida() { return concluida; }
    public void setConcluida(boolean concluida) { this.concluida = concluida; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(String dataCriacao) { this.dataCriacao = dataCriacao; }
}