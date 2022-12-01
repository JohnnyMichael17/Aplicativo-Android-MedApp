package com.example.medapp;

import java.io.Serializable;

public class Especializacao implements Serializable {

    public int idEspec;
    public String descricao;

    public int getIdEspec() {
        return idEspec;
    }

    public void setIdEspec(int idEspec) {
        this.idEspec = idEspec;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "ID: "+ idEspec + "\nEspecialização: " + descricao;
    }
}
