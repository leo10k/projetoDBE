package br.com.fiap.projetodbe.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Feed{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Date data;
    String titulo;
    String descricao;
    String img;

    public Feed(Long id, Date data, String titulo, String descricao, String img) {
        this.id = id;
        this.data = data;
        this.titulo = titulo;
        this.descricao = descricao;
        this.img = img;
    }

    protected Feed(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Feed [id=" + id + ", data=" + data + ", titulo=" + titulo + ", descricao=" + descricao + ", img=" + img
                + "]";
    }

}
