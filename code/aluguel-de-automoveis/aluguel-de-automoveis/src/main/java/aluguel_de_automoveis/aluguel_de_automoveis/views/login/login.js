document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');
    loginForm.addEventListener('submit', fazerLogin);

    // Verifica se já existe um cliente logado
    if(localStorage.getItem('clienteLogado')) {
        window.location.href = '../hub/hub.html';
    }
});

function fazerLogin(event) {
    event.preventDefault();
    
    const cpf = document.getElementById('loginCpf').value.replace(/\D/g, '');
    const nome = document.getElementById('loginNome').value.trim();

    // Validação básica
    if(cpf.length !== 11) {
        alert('CPF deve conter 11 dígitos');
        return;
    }

    if(nome.length < 3) {
        alert('Nome deve conter pelo menos 3 caracteres');
        return;
    }

    // Verifica se o cliente existe na API
    verificarCliente(cpf, nome)
        .then(cliente => {
            if(cliente) {
                // Armazena os dados do cliente no localStorage
                localStorage.setItem('clienteLogado', JSON.stringify({
                    cpf: cliente.cpf,
                    nome: cliente.nome,
                    id: cliente.id
                }));
                
                // Redireciona para a página principal
                window.location.href = '../hub/hub.html';
            } else {
                alert('Cliente não encontrado. Verifique seu CPF e nome.');
            }
        })
        .catch(error => {
            console.error('Erro ao verificar cliente:', error);
            alert('Erro ao fazer login. Tente novamente.');
        });
}

async function verificarCliente(cpf, nome) {
    try {
        const response = await fetch(`http://localhost:8080/api/clientes/cpf/${cpf}`);
        
        if(!response.ok) {
            if(response.status === 404) {
                return null; // Cliente não encontrado
            }
            throw new Error('Erro ao verificar cliente');
        }
        
        const cliente = await response.json();
        
        // Verifica se o nome corresponde (case insensitive)
        if(cliente.nome.toLowerCase() === nome.toLowerCase()) {
            return cliente;
        }
        
        return null;
    } catch (error) {
        console.error('Erro:', error);
        throw error;
    }
}