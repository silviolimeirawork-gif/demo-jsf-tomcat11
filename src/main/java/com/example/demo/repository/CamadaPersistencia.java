package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import com.example.demo.model.Empresa;
import com.example.demo.model.RamoAtividade;
import com.example.demo.model.TipoEmpresa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class CamadaPersistencia {

    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("devPU");

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        //Declarando os repositórios
        RamoAtividades ramoAtividades = new RamoAtividades(em);
        Empresas empresas = new Empresas(em);

        //Buscando as informações do banco
        List<RamoAtividade> listaDeRamoAtividades = ramoAtividades.pesquisar("");
        List<Empresa> listaDeEmpresas = empresas.pesquisar("");
        System.out.println(listaDeEmpresas);

        //Criando uma empresa
        Empresa empresa = new Empresa();
        empresa.setNomeFantasia("José da Silva");
        empresa.setCnpj("41.952.519/0001-57");
        empresa.setRazaoSocial("José da Silva 41952519000157");
        empresa.setTipo(TipoEmpresa.MEI);
        empresa.setDataFundacao(new Date());
        empresa.setRamoAtividade(listaDeRamoAtividades.get(0));

        //Salvando a empresa
        empresas.guardar(empresa);

        em.getTransaction().commit();

        //Verifica se a inserção funcionou
        List<Empresa> listaDeEmpresas2 = empresas.pesquisar("");
        System.out.println(listaDeEmpresas2);

        em.close();
        emf.close();

    }

}
