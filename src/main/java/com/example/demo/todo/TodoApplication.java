package com.example.demo.todo;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
public class TodoApplication extends Application {
    // Classe vazia para ativar JAX-RS
    int abs = 123;

}