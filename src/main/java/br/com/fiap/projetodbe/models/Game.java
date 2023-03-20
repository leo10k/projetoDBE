package br.com.fiap.projetodbe.models;

public class Game {
    
    private Long id;
    private String nome;
    private String genero;
    
    public Game(Long id, String nome, String genero) {
        this.id = id;
        this.nome = nome;
        this.genero = genero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "Game [id=" + id + ", nome=" + nome + ", genero=" + genero + "]";
    }

}
