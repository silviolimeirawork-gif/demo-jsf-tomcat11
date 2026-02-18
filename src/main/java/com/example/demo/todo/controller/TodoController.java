package com.example.demo.todo.controller;

import com.example.demo.todo.model.Todo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

//@WebServlet("/api/todos/*")
public class TodoController extends HttpServlet {
    private final Map<Long, Todo> todos = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        // Dados iniciais
        criarTodoInicial("Estudar Vue 3", "Aprender os fundamentos do Vue 3");
        criarTodoInicial("Configurar Tomcat 11", "Instalar e configurar o Tomcat 11 com Java 17");
        criarTodoInicial("Criar projeto Todo", "Desenvolver aplicação de exemplo");
    }

    private void criarTodoInicial(String titulo, String descricao) {
        Todo todo = new Todo();
        todo.setId(idGenerator.getAndIncrement());
        todo.setTitulo(titulo);
        todo.setConcluida(false);
        todo.setDescricao(descricao);
        todo.setDataCriacao(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        todos.put(todo.getId(), todo);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");

        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // Listar todos
            objectMapper.writeValue(resp.getWriter(), todos.values());
        } else {
            // Buscar um específico
            try {
                Long id = Long.parseLong(pathInfo.substring(1));
                Todo todo = todos.get(id);
                if (todo != null) {
                    objectMapper.writeValue(resp.getWriter(), todo);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");

        Todo todo = objectMapper.readValue(req.getReader(), Todo.class);
        todo.setId(idGenerator.getAndIncrement());
        todo.setDataCriacao(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        todos.put(todo.getId(), todo);

        resp.setStatus(HttpServletResponse.SC_CREATED);
        objectMapper.writeValue(resp.getWriter(), todo);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            Long id = Long.parseLong(pathInfo.substring(1));
            Todo todoAtualizado = objectMapper.readValue(req.getReader(), Todo.class);
            todoAtualizado.setId(id);

            if (todos.containsKey(id)) {
                todos.put(id, todoAtualizado);
                objectMapper.writeValue(resp.getWriter(), todoAtualizado);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            Long id = Long.parseLong(pathInfo.substring(1));
            if (todos.remove(id) != null) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}