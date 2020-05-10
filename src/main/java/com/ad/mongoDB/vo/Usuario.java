package com.ad.mongoDB.vo;

import java.util.List;

/**
 *
 * Usuario
 */
public class Usuario {
    
    private String nome;
    private String username;
    private String password;
    private List<String> listaFollowers;

    public Usuario() {
    }

    public Usuario(String nome, String username, String password, List<String> listaFollowers) {
        this.nome = nome;
        this.username = username;
        this.password = password;
        this.listaFollowers = listaFollowers;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getListaFollowers() {
        return listaFollowers;
    }

    public void setListaFollowers(List<String> listaFollowers) {
        this.listaFollowers = listaFollowers;
    }

    @Override
    public String toString() {
        return "Usuario{" + "nome=" + nome + ", username=" + username + ", password=" + password + ", listaFollowers=" + listaFollowers.toString() + '}';
    }

}
