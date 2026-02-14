package com.example.demo.controller;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Listener que inicia e para um agendador de backups do banco de dados
 * a cada hora, durante todo o ciclo de vida da aplicação.
 */
@WebListener
public class DatabaseBackupScheduler implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(DatabaseBackupScheduler.class.getName());
    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        // Cria um agendador com um único thread
        scheduler = Executors.newSingleThreadScheduledExecutor();

        // Agenda a tarefa de backup para rodar a cada hora (com delay inicial 0)
        scheduler.scheduleAtFixedRate(new BackupTask(), 0, 1, TimeUnit.SECONDS);

        logger.info("Agendador de backups iniciado. Backup será executado a cada hora.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // Para o agendador de forma graciosa quando a aplicação for encerrada
        if (scheduler != null) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(30, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
            logger.info("Agendador de backups encerrado.");
        }
    }

    /**
     * Tarefa responsável por executar o backup do banco de dados.
     */
    private static class BackupTask implements Runnable {

        @Override
        public void run() {
            try {
                realizarBackup();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Erro durante a execução do backup", e);
            }
        }

        private void realizarBackup() {
            // Aqui você implementa a lógica real de backup do banco de dados.
            // Exemplos:
            // 1. Usando mysqldump via ProcessBuilder (para MySQL)
            // 2. Usando JDBC para exportar dados para arquivos
            // 3. Chamando um script externo

            logger.info("Iniciando backup do banco de dados...");

            // Exemplo com mysqldump (descomente e ajuste conforme necessário)
            /*
            ProcessBuilder pb = new ProcessBuilder(
                "mysqldump",
                "-u", "usuario",
                "-p senha",
                "--host", "localhost",
                "nome_do_banco",
                "--result-file=" + "/caminho/para/backup/backup_" + System.currentTimeMillis() + ".sql"
            );
            try {
                Process process = pb.start();
                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    logger.info("Backup concluído com sucesso.");
                } else {
                    logger.warning("Backup falhou com código de saída: " + exitCode);
                }
            } catch (Exception e) {
                throw new RuntimeException("Erro ao executar mysqldump", e);
            }
            */

            // Simulação de backup (apenas log)
            logger.info("Backup simulado executado em: " + new java.util.Date());
        }
    }
}