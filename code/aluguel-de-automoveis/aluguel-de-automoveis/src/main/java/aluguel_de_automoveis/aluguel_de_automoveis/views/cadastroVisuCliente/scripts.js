const API_URL = 'http://localhost:8080/api/clientes';
        
const formCadastro = document.getElementById('formCadastro');
const tabelaClientes = document.getElementById('tabelaClientes');
const clienteForm = document.getElementById('clienteForm');
const tbody = document.querySelector('#tabelaClientes tbody');

let clienteEmEdicao = null;

document.getElementById('btnCadastrar').addEventListener('click', mostrarFormulario);
document.getElementById('btnVisualizar').addEventListener('click', mostrarTabela);
document.getElementById('btnCancelar').addEventListener('click', cancelarEdicao);
clienteForm.addEventListener('submit', salvarCliente);

document.addEventListener('DOMContentLoaded', carregarClientes);

function mostrarFormulario() {
    formCadastro.style.display = 'block';
    tabelaClientes.style.display = 'none';
    clienteEmEdicao = null;
    clienteForm.reset();
    document.querySelector('#formCadastro h2').textContent = 'Cadastrar Novo Cliente';
    document.getElementById('cpf').readOnly = false; 
}

function mostrarTabela() {
    formCadastro.style.display = 'none';
    tabelaClientes.style.display = 'block';
    carregarClientes();
}

function cancelarEdicao() {
    mostrarTabela();
}

async function carregarClientes() {
    try {
        const response = await fetch(API_URL);
        
        if (!response.ok) {
            const errorData = await response.json();
            console.error('Detalhes do erro:', errorData);
            throw new Error(errorData.message || `Erro ao carregar clientes (${response.status})`);
        }
        
        const clientes = await response.json();
        renderizarClientes(clientes);
        
    } catch (error) {
        console.error('Erro completo:', error);
        alert(error.message);
    }
}

function renderizarClientes(clientes) {
    tbody.innerHTML = '';
    
    clientes.forEach(cliente => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${cliente.cpf}</td>
            <td>${cliente.nome}</td>
            <td>${cliente.profissao || '-'}</td>
            <td>${cliente.rg || '-'}</td>
            <td class="acoes">
                <button class="btn btn-editar" data-cpf="${cliente.cpf}">Editar</button>
                <button class="btn btn-excluir" data-cpf="${cliente.cpf}">Excluir</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
    
    // Adiciona eventos aos botões
    document.querySelectorAll('.btn-editar').forEach(btn => {
        btn.addEventListener('click', editarCliente);
    });
    
    document.querySelectorAll('.btn-excluir').forEach(btn => {
        btn.addEventListener('click', excluirCliente);
    });
}

async function editarCliente(event) {
    const cpf = event.target.getAttribute('data-cpf');
    try {
        const response = await fetch(`${API_URL}/cpf/${cpf}`);
        const cliente = await response.json();

        document.getElementById('cpf').value = cliente.cpf;
        document.getElementById('nome').value = cliente.nome;
        document.getElementById('profissao').value = cliente.profissao;
        document.getElementById('rg').value = cliente.rg;
        
        clienteEmEdicao = cpf;
        document.querySelector('#formCadastro h2').textContent = 'Editar Cliente';
        formCadastro.style.display = 'block';
        tabelaClientes.style.display = 'none';
        document.getElementById('cpf').readOnly = true; 

    } catch (error) {
        console.error('Erro ao carregar cliente para edição:', error);
        alert('Erro ao carregar cliente para edição');
    }
}

async function salvarCliente(event) {
    event.preventDefault();
    
    const formData = {
        cpf: document.getElementById('cpf').value,
        nome: document.getElementById('nome').value,
        profissao: document.getElementById('profissao').value,
        rg: document.getElementById('rg').value
    };

    try {
        const url = clienteEmEdicao 
            ? `${API_URL}/cpf/${clienteEmEdicao}`
            : API_URL;

        const method = clienteEmEdicao ? 'PUT' : 'POST';

        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        });

        const responseData = await response.json();

        if (!response.ok) {
            // Tratamento de erros específicos
            if (response.status === 400) {
                const errors = Array.isArray(responseData) 
                    ? responseData.join('\n') 
                    : JSON.stringify(responseData);
                throw new Error(`Erro de validação:\n${errors}`);
            }
            throw new Error(responseData.message || 'Erro desconhecido');
        }

        alert(clienteEmEdicao 
            ? 'Cliente atualizado com sucesso!' 
            : 'Cliente cadastrado com sucesso!');
        
        mostrarTabela();
        clienteForm.reset();
        clienteEmEdicao = null;

    } catch (error) {
        console.error('Erro completo:', error);
        alert('Erro ao salvar cliente:\n' + error.message);
        
        // Não limpa o formulário em caso de erro
        if (!clienteEmEdicao) {
            document.getElementById('cpf').value = formData.cpf;
            document.getElementById('nome').value = formData.nome;
            document.getElementById('profissao').value = formData.profissao;
            document.getElementById('rg').value = formData.rg;
        }
    }
}

async function excluirCliente(event) {
    const cpf = event.target.getAttribute('data-cpf');
    if (confirm('Tem certeza que deseja excluir este cliente?')) {
        try {
            // Primeiro buscamos o cliente pelo CPF para obter o ID
            const responseGet = await fetch(`${API_URL}/cpf/${cpf}`);
            const cliente = await responseGet.json();
            
            if (!cliente || !cliente.id) {
                throw new Error('Cliente não encontrado');
            }
            
            // Agora excluímos usando o ID
            const response = await fetch(`${API_URL}/${cliente.id}`, {
                method: 'DELETE'
            });
            
            if (response.ok) {
                alert('Cliente excluído com sucesso!');
                carregarClientes();
            } else {
                throw new Error('Erro ao excluir cliente');
            }
        } catch (error) {
            console.error('Erro ao excluir cliente:', error);
            alert('Erro ao excluir cliente: ' + error.message);
        }
    }
}