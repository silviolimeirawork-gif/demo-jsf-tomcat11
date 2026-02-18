import { ref, computed } from 'https://unpkg.com/vue@3/dist/vue.esm-browser.js';
import { fetchTodos, createTodo, updateTodo, deleteTodo } from '../api.js';

export function useTodos() {
    const tarefas = ref([]);
    const carregando = ref(true);
    const filtroAtual = ref('todas');
    const modoEdicao = ref(false);
    const tarefaEditada = ref({});
    const modalExclusaoAtivo = ref(false);
    const tarefaParaExcluir = ref(null);
    const novaTarefa = ref({ titulo: '', descricao: '' });

    // Computed
    const tarefasFiltradas = computed(() => {
        switch (filtroAtual.value) {
            case 'pendentes': return tarefas.value.filter(t => !t.concluida);
            case 'concluidas': return tarefas.value.filter(t => t.concluida);
            default: return tarefas.value;
        }
    });

    const tarefasPendentes = computed(() => tarefas.value.filter(t => !t.concluida));
    const tarefasConcluidas = computed(() => tarefas.value.filter(t => t.concluida));
    const totalTarefas = computed(() => tarefas.value.length);

    // Métodos
    async function carregarTarefas() {
        carregando.value = true;
        try {
            tarefas.value = await fetchTodos();
        } catch (error) {
            console.error(error);
            alert('Erro ao carregar tarefas');
        } finally {
            carregando.value = false;
        }
    }

    async function adicionarTarefa() {
        if (!novaTarefa.value.titulo.trim()) return;
        try {
            const nova = await createTodo({
                titulo: novaTarefa.value.titulo,
                descricao: novaTarefa.value.descricao,
                concluida: false
            });
            tarefas.value.push(nova);
            novaTarefa.value = { titulo: '', descricao: '' };
        } catch (error) {
            console.error(error);
            alert('Erro ao adicionar tarefa');
        }
    }

    async function toggleTarefa(tarefa) {
        const atualizada = { ...tarefa, concluida: !tarefa.concluida };
        try {
            await updateTodo(tarefa.id, atualizada);
            const index = tarefas.value.findIndex(t => t.id === tarefa.id);
            if (index !== -1) tarefas.value[index] = atualizada;
        } catch (error) {
            console.error(error);
            alert('Erro ao atualizar tarefa');
        }
    }

    function confirmarExclusao(tarefa) {
        tarefaParaExcluir.value = tarefa;
        modalExclusaoAtivo.value = true;
    }

    function cancelarExclusao() {
        modalExclusaoAtivo.value = false;
        tarefaParaExcluir.value = null;
    }

    async function excluirTarefaConfirmada() {
        if (!tarefaParaExcluir.value) return;
        try {
            await deleteTodo(tarefaParaExcluir.value.id);
            tarefas.value = tarefas.value.filter(t => t.id !== tarefaParaExcluir.value.id);
            cancelarExclusao();
        } catch (error) {
            console.error(error);
            alert('Erro ao excluir tarefa');
        }
    }

    function editarTarefa(tarefa) {
        tarefaEditada.value = { ...tarefa };
        modoEdicao.value = true;
    }

    async function salvarEdicao() {
        if (!tarefaEditada.value.titulo.trim()) return;
        try {
            const atualizada = await updateTodo(tarefaEditada.value.id, tarefaEditada.value);
            const index = tarefas.value.findIndex(t => t.id === atualizada.id);
            if (index !== -1) tarefas.value[index] = atualizada;
            modoEdicao.value = false;
            tarefaEditada.value = {};
        } catch (error) {
            console.error(error);
            alert('Erro ao salvar edição');
        }
    }

    function cancelarEdicao() {
        modoEdicao.value = false;
        tarefaEditada.value = {};
    }

    function formatarData(dataString) {
        if (!dataString) return 'Data não disponível';
        const data = new Date(dataString);
        return data.toLocaleDateString('pt-BR', {
            day: '2-digit', month: '2-digit', year: 'numeric',
            hour: '2-digit', minute: '2-digit'
        });
    }

    return {
        // Estado
        tarefas,
        carregando,
        filtroAtual,
        modoEdicao,
        tarefaEditada,
        modalExclusaoAtivo,
        tarefaParaExcluir,
        novaTarefa,
        // Computed
        tarefasFiltradas,
        tarefasPendentes,
        tarefasConcluidas,
        totalTarefas,
        // Métodos
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
    };
}
