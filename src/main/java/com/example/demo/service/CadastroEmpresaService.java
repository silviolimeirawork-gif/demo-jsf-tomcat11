package com.example.demo.service;

import com.example.demo.model.Empresa;
import com.example.demo.repository.Empresas;
import com.example.demo.util.Transacional;
import jakarta.inject.Inject;

import java.io.Serializable;

public class CadastroEmpresaService implements Serializable {

    @Inject
    private Empresas empresas;

    @Transacional
    public void salvar(Empresa empresa) {
        empresas.guardar(empresa);
    }

    @Transacional
    public void excluir(Empresa empresa) {
        empresas.remover(empresa);
    }

}
