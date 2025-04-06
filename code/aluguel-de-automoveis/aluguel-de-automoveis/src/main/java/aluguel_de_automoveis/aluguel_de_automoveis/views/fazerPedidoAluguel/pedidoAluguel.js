document.addEventListener('DOMContentLoaded', async function() {
    // Verificar login
    const clienteLogado = JSON.parse(localStorage.getItem('clienteLogado'));
    if (!clienteLogado) {
        window.location.href = '../login/login.html';
        return;
    }

    // Elementos DOM
    const listaCarros = document.getElementById('listaCarros');
    const modal = document.getElementById('modalAluguel');
    const closeModal = document.querySelector('.close-modal');
    const formAluguel = document.getElementById('formAluguel');
    const btnCancelarAluguel = document.getElementById('btnCancelarAluguel');

    // Carregar carros disponíveis
    async function carregarCarrosDisponiveis() {
        try {
            const response = await fetch('http://localhost:8080/api/automoveis/disponiveis');
            if (!response.ok) throw new Error('Erro ao carregar carros');
            
            const carros = await response.json();
            renderizarCarros(carros);
        } catch (error) {
            console.error('Erro:', error);
            listaCarros.innerHTML = `
                <div class="empty-state">
                    <p>Erro ao carregar carros disponíveis</p>
                </div>
            `;
        }
    }

    // Renderizar lista de carros
    function renderizarCarros(carros) {
        if (carros.length === 0) {
            listaCarros.innerHTML = `
                <div class="empty-state">
                    <p>Nenhum carro disponível no momento</p>
                </div>
            `;
            return;
        }

        listaCarros.innerHTML = carros.map(carro => `
            <div class="carro-card" data-id="${carro.id}">
                <h3>${carro.marca} ${carro.modelo} ${carro.ano}</h3>
                <div class="carro-info">
                    <div class="carro-info-item">
                        <div class="carro-info-label">Matrícula</div>
                        <div class="carro-info-value">${carro.matricula}</div>
                    </div>
                    <div class="carro-info-item">
                        <div class="carro-info-label">Placa</div>
                        <div class="carro-info-value">${carro.placa}</div>
                    </div>
                </div>
                <button class="btn btn-alugar">Alugar este carro</button>
            </div>
        `).join('');

        // Adicionar eventos aos botões de alugar
        document.querySelectorAll('.btn-alugar').forEach(btn => {
            btn.addEventListener('click', function() {
                const card = this.closest('.carro-card');
                abrirModalAluguel(card.dataset.id);
            });
        });
    }

    // Abrir modal de aluguel
    async function abrirModalAluguel(carroId) {
        try {
            const response = await fetch(`http://localhost:8080/api/automoveis/${carroId}`);
            if (!response.ok) throw new Error('Erro ao carregar dados do carro');
            
            const carro = await response.json();
            
            // Preencher modal com dados do carro
            document.getElementById('automovelId').value = carro.id;
            document.getElementById('modalCarroTitulo').textContent = `${carro.marca} ${carro.modelo} ${carro.ano}`;
            document.getElementById('modalCarroDetalhes').textContent = 
                `Matrícula: ${carro.matricula} | Placa: ${carro.placa}`;
            
            // Mostrar modal
            modal.style.display = 'block';
        } catch (error) {
            console.error('Erro:', error);
            alert('Erro ao abrir modal de aluguel');
        }
    }

    // Fechar modal
    function fecharModal() {
        modal.style.display = 'none';
        formAluguel.reset();
    }

    // Eventos
    closeModal.addEventListener('click', fecharModal);
    btnCancelarAluguel.addEventListener('click', fecharModal);

    // Finalizar aluguel
    formAluguel.addEventListener('submit', async function(e) {
        e.preventDefault();
        
        const dadosAluguel = {
            automovelId: document.getElementById('automovelId').value,
            clienteId: clienteLogado.id,
            formaPagamento: document.querySelector('input[name="pagamento"]:checked').value,
            status: document.querySelector('input[name="pagamento"]:checked').value === 'CREDITO' 
                   ? 'AGUARDANDO_APROVACAO' 
                   : 'AGUARDANDO_PAGAMENTO'
        };

        try {
            // Primeiro marcar o carro como alugado
            const responseMarcar = await fetch(
                `http://localhost:8080/api/automoveis/${dadosAluguel.automovelId}/marcar-como-alugado`, 
                { method: 'PUT' }
            );
            
            if (!responseMarcar.ok) throw new Error('Erro ao marcar carro como alugado');
            
            // Depois criar o registro de aluguel
            const responseAluguel = await fetch('http://localhost:8080/api/alugueis', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(dadosAluguel)
            });
            
            if (!responseAluguel.ok) throw new Error('Erro ao registrar aluguel');
            
            alert('Aluguel solicitado com sucesso!');
            fecharModal();
            carregarCarrosDisponiveis(); // Atualiza a lista de carros
            window.location.href = '../hub/hub.html'; // Volta para o hub
        } catch (error) {
            console.error('Erro:', error);
            alert('Erro ao finalizar aluguel: ' + error.message);
        }
    });

    // Fechar modal ao clicar fora
    window.addEventListener('click', function(e) {
        if (e.target === modal) {
            fecharModal();
        }
    });

    // Inicialização
    await carregarCarrosDisponiveis();
});