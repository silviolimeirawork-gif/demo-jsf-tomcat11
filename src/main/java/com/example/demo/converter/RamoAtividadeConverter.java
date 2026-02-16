package com.example.demo.converter;

import com.example.demo.model.RamoAtividade;
import com.example.demo.service.RamoAtividadeService;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Named
@FacesConverter(value = "ramoAtividadeConverter", managed = true)
public class RamoAtividadeConverter implements Converter<RamoAtividade> {

    @Inject
    private RamoAtividadeService ramoAtividadeService;

    public RamoAtividadeConverter(List<RamoAtividade> listaRamoAtividades) {
    }

    public RamoAtividadeConverter() {
    }

    @Override
    public RamoAtividade getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            Long id = Long.parseLong(value);
            return ramoAtividadeService.buscarPorId(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, RamoAtividade cidade) {
        if (cidade == null || cidade.getId() == null) {
            return "";
        }
        return String.valueOf(cidade.getId());
    }
}