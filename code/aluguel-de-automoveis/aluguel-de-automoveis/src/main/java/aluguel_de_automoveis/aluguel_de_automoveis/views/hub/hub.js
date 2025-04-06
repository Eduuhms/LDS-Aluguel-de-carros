document.addEventListener('DOMContentLoaded', function() {
    // Verifica se o usuário está logado
    const clienteLogado = JSON.parse(localStorage.getItem('clienteLogado'));
    
    if(!clienteLogado) {
        window.location.href = '../login/login.html';
        return;
    }
    
    // Exibe o nome do usuário
    document.getElementById('nomeUsuario').textContent = clienteLogado.nome;
    
    // Configura o botão de logout
    document.getElementById('btnLogout').addEventListener('click', function() {
        if(confirm('Tem certeza que deseja sair do sistema?')) {
            localStorage.removeItem('clienteLogado');
            window.location.href = '../login/login.html';
        }
    });
});