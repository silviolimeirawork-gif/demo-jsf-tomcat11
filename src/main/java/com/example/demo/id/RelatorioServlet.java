package com.example.demo.id;

import com.example.demo.id.RelatorioService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/relatorio")
public class RelatorioServlet extends HttpServlet {

    @Inject
    private RelatorioService relatorioService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println(relatorioService.totalPedidosMesAtual());
    }
}
