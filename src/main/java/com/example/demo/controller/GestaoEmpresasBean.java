package com.example.demo.controller;

import com.example.demo.model.Empresa;
import com.example.demo.model.TipoEmpresa;
import com.example.demo.repository.Empresas;
import com.example.demo.util.FacesMessages;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Named
//@RequestScoped
@ViewScoped
//@SessionScoped
//@ApplicationScoped
public class GestaoEmpresasBean implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String termoPesquisa;

    @Inject
    private Empresas empresas;

    @Inject
    private FacesMessages facesMessages;

    private List<Empresa> listaEmpresas;

    public void todasEmpresas() {
        listaEmpresas = empresas.todas();
    }

    public List<Empresa> getListaEmpresas() {
        return listaEmpresas;
    }

    public String getTermoPesquisa() {
        return termoPesquisa;
    }

    public void setTermoPesquisa(String termoPesquisa) {
        this.termoPesquisa = termoPesquisa;
    }

    public void pesquisar() {
        listaEmpresas = empresas.pesquisar(termoPesquisa);

        if (listaEmpresas.isEmpty()) {
            facesMessages.info("Sua consulta não retornou registros.");
        }
    }

    public TipoEmpresa[] getTiposEmpresa() {
        return TipoEmpresa.values();
    }
}
