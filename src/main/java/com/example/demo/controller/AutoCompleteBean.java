package com.example.demo.controller;

import com.example.demo.model.RamoAtividade;
import com.example.demo.service.RamoAtividadeService;
import com.example.demo.util.FacesMessages;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class AutoCompleteBean implements Serializable {

    @Inject
    private FacesMessages facesMessages;

    @Inject
    private RamoAtividadeService ramoAtividadeService;

    private RamoAtividade ramoAtividadeSelecionado;
    private List<RamoAtividade> ramoAtividadesMultiplas = new ArrayList<>();

    /**
     * Chamado pelo AutoComplete
     */
    public List<RamoAtividade> buscarRamoAtividades(String query) {
        return ramoAtividadeService.buscarPorDescricao(query);
    }

    /**
     * Quando seleciona uma cidade
     */
    public void aoSelecionar() {
        if (ramoAtividadeSelecionado != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Selecionado: " + ramoAtividadeSelecionado.getDescricao()));
        }

    }

    /**
     * Mostra as ramos de atividades selecionadas
     */
    public void mostrarMultiplas() {
        if (ramoAtividadesMultiplas.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Nenhum ramo atividade selecionado"));
            return;
        }

        StringBuilder sb = new StringBuilder("Ramo Atividades: ");
        for (RamoAtividade ra : ramoAtividadesMultiplas) {
            sb.append(ra.getDescricao()).append(", ");
        }

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Múltiplas", sb.toString()));
    }

    /**
     * Limpa tudo
     */
    public void limpar() {
        ramoAtividadeSelecionado = null;
        ramoAtividadesMultiplas.clear();
    }

    // Getters e Setters
    public RamoAtividade getRamoAtividadeSelecionado() {
        return ramoAtividadeSelecionado;
    }

    public void setRamoAtividadeSelecionado(RamoAtividade ramoAtividadeSelecionado) {
        this.ramoAtividadeSelecionado = ramoAtividadeSelecionado;
    }

    public List<RamoAtividade> getRamoAtividadesMultiplas() {
        return ramoAtividadesMultiplas;
    }

    public void setRamoAtividadesMultiplas(List<RamoAtividade> ramoAtividadeesMultiplas) {
        this.ramoAtividadesMultiplas = ramoAtividadesMultiplas;
    }
}