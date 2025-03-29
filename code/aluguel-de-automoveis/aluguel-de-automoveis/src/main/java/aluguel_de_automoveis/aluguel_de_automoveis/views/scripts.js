const API_URL = 'http://localhost:8080/clientes';
        
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
        const clientes = await response.json();
        
        tbody.innerHTML = '';
        clientes.forEach(cliente => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${cliente.cpf}</td>
                <td>${cliente.nome}</td>
                <td>${cliente.profissao}</td>
                <td>${cliente.rg}</td>
                <td class="acoes">
                    <button class="btn btn-editar" data-cpf="${cliente.cpf}">Editar</button>
                    <button class="btn btn-excluir" data-cpf="${cliente.cpf}">Excluir</button>
                </td>
            `;
            tbody.appendChild(tr);
        });
        
        document.querySelectorAll('.btn-editar').forEach(btn => {
            btn.addEventListener('click', editarCliente);
        });
        
        document.querySelectorAll('.btn-excluir').forEach(btn => {
            btn.addEventListener('click', excluirCliente);
        });
        
    } catch (error) {
        console.error('Erro ao carregar clientes:', error);
        alert('Erro ao carregar clientes');
    }
}

async function editarCliente(event) {
    const cpf = event.target.getAttribute('data-cpf');
    try {
        const response = await fetch(`${API_URL}/${cpf}`);
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
    
    const cliente = {
        cpf: document.getElementById('cpf').value,
        nome: document.getElementById('nome').value,
        profissao: document.getElementById('profissao').value,
        rg: document.getElementById('rg').value,
        rendimentos:[],
        endereco: null
    };
    console.log(cliente);
    
    try {
        let response;
        if (clienteEmEdicao) {
            response = await fetch(`${API_URL}/${clienteEmEdicao}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(cliente)
            });
        } else {
            response = await fetch(API_URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(cliente)
            });
        }
        
        if (response.ok) {
            alert('Cliente salvo com sucesso!');
            mostrarTabela();
        } else {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Erro ao salvar cliente');
        }
    } catch (error) {
        console.error('Erro ao salvar cliente:', error);
        alert('Erro ao salvar cliente: ' + error.message);
    }
}

async function excluirCliente(event) {
    const cpf = event.target.getAttribute('data-cpf');
    if (confirm('Tem certeza que deseja excluir este cliente?')) {
        try {
            const response = await fetch(`${API_URL}/${cpf}`, {
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
            alert('Erro ao excluir cliente');
        }
    }
}