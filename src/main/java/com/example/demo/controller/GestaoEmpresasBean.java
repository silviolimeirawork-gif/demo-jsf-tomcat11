package com.example.demo.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;

@Named
//@RequestScoped
@ViewScoped
//@SessionScoped
//@ApplicationScoped
public class GestaoEmpresasBean implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static Integer NUMERO = 0;

    public GestaoEmpresasBean() {
        NUMERO++;
    }

    public Integer getNumero() {
        return NUMERO;
    }
}
