package com.example.demo.controller;

import com.example.demo.model.Empresa;
import com.example.demo.model.TipoEmpresa;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
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

    @Inject
    private Empresa empresa;

    public Empresa getEmpresa() {
        return empresa;
    }

    public TipoEmpresa[] getTiposEmpresa() {
        return TipoEmpresa.values();
    }

    public void salvar() {
        System.out.println("Razao Social: " + empresa.getRazaoSocial() +
                " - Nome fantasia: " + empresa.getNomeFantasia() +
                " - Tipo: " + empresa.getTipo());
    }

}
