package com.ad.mongoDB.vo;

import java.util.List;

/**
 *
 * Mensaxe
 */
public class Mensaxe {
    
    private String texto;
    private String date;
    private String nome;
    private String username;
    private List<String> hashtags; 

    public Mensaxe() {
    }

    public Mensaxe(String texto, String date, String nome, String username, List<String> hashtags) {
        this.texto = texto;
        this.date = date;
        this.nome = nome;
        this.username = username;
        this.hashtags = hashtags;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    @Override
    public String toString() {
        return "Mensaxes{" + "texto=" + texto + ", date=" + date + ", nome=" + nome + ", username=" + username + ", hashtags=" + hashtags + '}';
    }
    
}
