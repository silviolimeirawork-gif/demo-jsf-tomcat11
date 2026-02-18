package com.example.demo.controller;

import com.example.demo.converter.RamoAtividadeConverter;
import com.example.demo.model.Empresa;
import com.example.demo.model.RamoAtividade;
import com.example.demo.model.TipoEmpresa;
import com.example.demo.repository.Empresas;
import com.example.demo.repository.RamoAtividades;
import com.example.demo.service.CadastroEmpresaService;
import com.example.demo.util.FacesMessages;
import com.example.demo.util.Transacional;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.faces.convert.Converter;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
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

    private Converter ramoAtividadeConverter;

    @Inject
    private RamoAtividades ramoAtividades;

    @Inject
    private CadastroEmpresaService cadastroEmpresaService;

    @Inject
    private Empresas empresas;

    @Inject
    private AutoCompleteBean autoCompleteBean;

    private Empresa empresa;

    private boolean jaHouvePesquisa() {
        return termoPesquisa != null && !"".equals(termoPesquisa);
    }

    public void salvar() {
        empresa.setRamoAtividade(
                autoCompleteBean.getRamoAtividadeSelecionado());
        cadastroEmpresaService.salvar(empresa);

        if (jaHouvePesquisa()) {
            pesquisar();
        } else {
            todasEmpresas();
        }

        facesMessages.info("Empresa salva com sucesso!");
    }

    public void excluir() {
        cadastroEmpresaService.excluir(empresa);

        empresa = null;

        if (jaHouvePesquisa()) {
            pesquisar();
        } else {
            todasEmpresas();
        }

        facesMessages.info("Empresa excluída com sucesso!");
    }


    public void prepararNovaEmpresa() {
        empresa = new Empresa();
    }

    public void prepararEdicao() {
        ramoAtividadeConverter = new RamoAtividadeConverter(Arrays.asList(empresa.getRamoAtividade()));
        autoCompleteBean.setRamoAtividadeSelecionado(empresa.getRamoAtividade());
    }

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

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public boolean isEmpresaSelecionada() {
        return empresa != null && empresa.getId() != null;
    }
}
