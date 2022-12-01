package com.example.medapp;

import java.io.Serializable;

public class Medico implements Serializable {

    private int idMed;
    private int idEspec;
    private String nome;
    private String telefone;
    private String endereco;
    private int idade;

    public int getIdMed() {
        return idMed;
    }

    public void setIdMed(int idMed) {
        this.idMed = idMed;
    }

   public int getIdEspec() {
        return idEspec;
    }

    public void setIdEspec(int idEspec) {
        this.idEspec = idEspec;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Nome: " + nome +
                "\nTelefone: " + telefone +
                "\nIdade: " + idade +
                " anos\nEndereço: " + endereco;
    }
}
