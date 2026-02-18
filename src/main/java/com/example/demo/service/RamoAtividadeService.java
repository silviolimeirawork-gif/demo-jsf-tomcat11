package com.example.demo.service;

import com.example.demo.model.RamoAtividade;
import com.example.demo.repository.RamoAtividadeRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class RamoAtividadeService implements Serializable {

    @Inject
    private RamoAtividadeRepository ramoAtividadeRepository;

    /**
     * Método chamado pelo AutoComplete
     */
    public List<RamoAtividade> buscarPorDescricao(String query) {
        if (query == null || query.trim().length() < 2) {
            return List.of();
        }
        return ramoAtividadeRepository.buscarPorNome(query.trim(), 15);
    }

    /**
     * Busca cidade por ID (usado pelo converter)
     */
    public RamoAtividade buscarPorId(Long id) {
        if (id == null) return null;
        return ramoAtividadeRepository.buscarPorId(id).orElse(null);
    }
}