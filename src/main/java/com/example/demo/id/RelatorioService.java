package com.example.demo.id;

import jakarta.inject.Inject;

import java.math.BigDecimal;

public class RelatorioService {

    @Inject
    private Pedidos pedidos;

    public RelatorioService() {
    }

    public RelatorioService(Pedidos pedidos) {
        this.pedidos = pedidos;
    }

    public BigDecimal totalPedidosMesAtual() {
        return pedidos.totalPedidosMesAtual();
    }

    public void setPedidos(Pedidos pedidos) {
        this.pedidos = pedidos;
    }
}
