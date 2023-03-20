package br.com.fiap.projetodbe.models;

public class User {

    private Long id;
    private String nome;
    private String email;
    private String genero;
    private String telefone;
    private String password;

    public User(Long id, String nome, String email, String genero, String telefone, String password) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.genero = genero;
        this.telefone = telefone;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", nome=" + nome + ", email=" + email + ", genero=" + genero + ", telefone="
                + telefone + ", password=" + password + "]";
    }

}
