import { createApp, ref } from 'https://unpkg.com/vue@3/dist/vue.esm-browser.js';
import { useTodos } from './composables/useTodos.js';

createApp({
    setup() {
        // Título da aplicação (pode ser mantido aqui ou movido para o composable)
        const titulo = ref('Lista de Tarefas Vue 3 (Modular)');

        // Importa toda a lógica do composable
        const {
            tarefas,
            carregando,
            filtroAtual,
            modoEdicao,
            tarefaEditada,
            modalExclusaoAtivo,
            tarefaParaExcluir,
            novaTarefa,
            tarefasFiltradas,
            tarefasPendentes,
            tarefasConcluidas,
            totalTarefas,
            carregarTarefas,
            adicionarTarefa,
            toggleTarefa,
            confirmarExclusao,
            cancelarExclusao,
            excluirTarefaConfirmada,
            editarTarefa,
            salvarEdicao,
            cancelarEdicao,
            formatarData
        } = useTodos();

        // Carregar tarefas na montagem
        carregarTarefas();

        return {
            titulo,
            tarefas,
            carregando,
            filtroAtual,
            modoEdicao,
            tarefaEditada,
            modalExclusaoAtivo,
            tarefaParaExcluir,
            novaTarefa,
            tarefasFiltradas,
            tarefasPendentes,
            tarefasConcluidas,
            totalTarefas,
            adicionarTarefa,
            toggleTarefa,
            confirmarExclusao,
            cancelarExclusao,
            excluirTarefaConfirmada,
            editarTarefa,
            salvarEdicao,
            cancelarEdicao,
            formatarData
        };
    }
}).mount('#app');
