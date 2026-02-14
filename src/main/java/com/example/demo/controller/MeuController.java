package com.example.demo.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@RequestScoped
public class MeuController {
    
    private String mensagem = "Hello JSF 4.0 + CDI!";
    
    public String getMensagem() {
        return mensagem;
    }
    
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    public void enviar() {
    	System.out.println("Método enviar() chamado!");
        System.out.println("Nome: " + mensagem);
        
        // Adicionar mensagem de sucesso
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Sucesso", 
                "Dados enviados: " + mensagem));    	
    }
}