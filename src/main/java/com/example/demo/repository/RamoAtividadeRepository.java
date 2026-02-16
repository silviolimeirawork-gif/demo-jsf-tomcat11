package com.example.demo.repository;

import com.example.demo.model.RamoAtividade;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Named
@ApplicationScoped
public class RamoAtividadeRepository implements Serializable {

    private EntityManagerFactory emf;
    private EntityManager em;

    @PostConstruct
    public void init() {
        emf = Persistence.createEntityManagerFactory("devPU");
        em = emf.createEntityManager();
        popularDadosIniciais();
    }

    @PreDestroy
    public void destroy() {
        if (em != null && em.isOpen()) em.close();
        if (emf != null && emf.isOpen()) emf.close();
    }

    /**
     * Busca cidades por nome (ignorando maiúsculas/minúsculas)
     */
    public List<RamoAtividade> buscarPorNome(String descricao, int limite) {
        String sql = "SELECT ra FROM RamoAtividade ra WHERE LOWER(ra.descricao) LIKE LOWER(:descricao) ORDER BY ra.descricao";

        return em.createQuery(sql, RamoAtividade.class)
                .setParameter("descricao", descricao + "%")
                .setMaxResults(limite)
                .getResultList();
    }

    /**
     * Busca cidade por ID
     */
    public Optional<RamoAtividade> buscarPorId(Long id) {
        RamoAtividade ramoAtividade = em.find(RamoAtividade.class, id);
        return Optional.ofNullable(ramoAtividade);
    }

    /**
     * Conta total de cidades
     */
    public long contar() {
        return em.createQuery("SELECT COUNT(ra) FROM RamoAtividade ra", Long.class)
                .getSingleResult();
    }

    private void popularDadosIniciais() {
        if (contar() > 0) return;

        String[][] ramoAtividades = {
            {"1", "Distribuição de alimentos"},
            {"2", "Telecomunicações"},
            {"3", "Vestuário"},
            {"4", "Lavanderia"},
            {"5", "Gráfica"},
            {"6", "Mecânica"},
            {"7", "Turismo"},
            {"8", "Saúde"},
            {"9", "Educação"},
            {"10", "Lazer"}
        };

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            for (String[] dados : ramoAtividades) {
                RamoAtividade ra = new RamoAtividade();
                ra.setId(Long.parseLong(dados[0]));
                ra.setDescricao(dados[1]);
                em.persist(ra);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }
}