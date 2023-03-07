package br.com.fiap.projetodbe.models;
 
public class Feed{

    String titulo;
    String descicao;
    String img;
    
    public Feed(String titulo, String descicao, String img) {
        this.titulo = titulo;
        this.descicao = descicao;
        this.img = img;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getDescicao() {
        return descicao;
    }
    public void setDescicao(String descicao) {
        this.descicao = descicao;
    }
    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Feed [titulo=" + titulo + ", descicao=" + descicao + ", img=" + img + "]";
    }
    
}
