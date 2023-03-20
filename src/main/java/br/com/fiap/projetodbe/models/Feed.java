package br.com.fiap.projetodbe.models;
 
public class Feed{

    Long id;
    String titulo;
    String descricao;
    String img;

    public Feed(Long id, String titulo, String descicao, String img) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descicao;
        this.img = img;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescicao() {
        return descricao;
    }

    public void setDescicao(String descicao) {
        this.descricao = descicao;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Feed [id=" + id + ", titulo=" + titulo + ", descicao=" + descricao + ", img=" + img + "]";
    }
    
}
