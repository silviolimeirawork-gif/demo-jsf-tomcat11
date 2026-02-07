package com.example.demo.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class TesteConexaoMySQL {
    public static void main(String[] args) {
        System.out.println("🔍 DIAGNÓSTICO DE CONEXÃO MYSQL\n");
        
        // Teste diferentes configurações
        String[] urls = {
            "jdbc:mysql://localhost:3306/mysql",  // Banco padrão do MySQL
            "jdbc:mysql://localhost:3306/",
            "jdbc:mysql://127.0.0.1:3306/mysql"
        };
        
        String user = "root";
        String password = "password"; // Substitua pela sua senha
        
        for (String url : urls) {
            testarConexao(url, user, password);
        }
    }
    
    private static void testarConexao(String url, String user, String password) {
        System.out.println("Testando: " + url);
        
        try {
            // 1. Registrar driver (opcional para JDBC 4+)
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 2. Tentar conexão
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement()) {
                
                System.out.println("✅ CONEXÃO BEM-SUCEDIDA!");
                
                // Executar consulta simples
                var rs = stmt.executeQuery("SELECT VERSION()");
                if (rs.next()) {
                    System.out.println("   Versão MySQL: " + rs.getString(1));
                }
                
                // Verificar bancos existentes
                rs = stmt.executeQuery("SHOW DATABASES");
                System.out.println("   Bancos disponíveis:");
                while (rs.next()) {
                    System.out.println("   - " + rs.getString(1));
                }
                
            } catch (Exception e) {
                System.err.println("❌ ERRO NA CONEXÃO: " + e.getMessage());
                if (e.getMessage().contains("Access denied")) {
                    System.err.println("   → Problema de autenticação!");
                } else if (e.getMessage().contains("Communications link failure")) {
                    System.err.println("   → Problema de rede/firewall!");
                }
            }
            
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver MySQL não encontrado no classpath!");
        }
        System.out.println();
    }
}