// Caminho base da API (relativo ao contexto)
const basePath = window.location.pathname.replace(/\/[^/]*$/, '');
const API_URL = basePath + '/api/todos';

export async function fetchTodos() {
    const response = await fetch(API_URL);
    if (!response.ok) throw new Error('Erro ao carregar tarefas');
    return await response.json();
}

export async function createTodo(todo) {
    const response = await fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(todo)
    });
    if (!response.ok) throw new Error('Erro ao adicionar tarefa');
    return await response.json();
}

export async function updateTodo(id, todo) {
    const response = await fetch(`${API_URL}/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(todo)
    });
    if (!response.ok) throw new Error('Erro ao atualizar tarefa');
    return await response.json();
}

export async function deleteTodo(id) {
    const response = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
    if (!response.ok) throw new Error('Erro ao excluir tarefa');
}
