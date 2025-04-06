document.addEventListener('DOMContentLoaded', async function() {
    // Verificar login
    const clienteLogado = JSON.parse(localStorage.getItem('clienteLogado'));
    if (!clienteLogado) {
        window.location.href = '../login/login.html';
        return;
    }

    // Elementos DOM
    const form = document.getElementById('formDados');
    const btnEditar = document.getElementById('btnEditar');
    const btnCancelar = document.getElementById('btnCancelarEdicao');
    const listaAlugueis = document.getElementById('listaAlugueis');

    // Carregar dados do cliente, pedidos e contratos
    async function carregarDadosCliente() {
        try {
            // Carrega dados do cliente
            const responseCliente = await fetch(`http://localhost:8080/api/clientes/cpf/${clienteLogado.cpf}`);
            if (!responseCliente.ok) throw new Error('Erro ao carregar dados do cliente');
            
            const cliente = await responseCliente.json();
            preencherFormulario(cliente);
            
            // Carrega pedidos e contratos do cliente
            await carregarAlugueis(cliente.id);
            
        } catch (error) {
            console.error('Erro:', error);
            alert('Erro ao carregar dados do cliente');
        }
    }

    // Preencher formulário
    function preencherFormulario(cliente) {
        if (!cliente) return;
        
        document.getElementById('dadoCpf').value = cliente.cpf || '';
        document.getElementById('dadoNome').value = cliente.nome || '';
        document.getElementById('dadoProfissao').value = cliente.profissao || '';
        document.getElementById('dadoRg').value = cliente.rg || '';
    }

    // Carregar pedidos e contratos do cliente
    async function carregarAlugueis(clienteId) {
        try {
            // Carrega pedidos
            const responsePedidos = await fetch(`http://localhost:8080/api/pedidos/${clienteId}`);
            if (!responsePedidos.ok) throw new Error('Erro ao carregar pedidos');
            const pedidos = await responsePedidos.json();
            const pedidosArray = Array.isArray(pedidos) ? pedidos : [pedidos];
            
            // Carrega contratos
            const responseContratos = await fetch(`http://localhost:8080/api/contratos/${clienteId}`);
            if (!responseContratos.ok) throw new Error('Erro ao carregar contratos');
            const contratos = await responseContratos.json();
            const contratosArray = Array.isArray(contratos) ? contratos : [contratos];
            
            // Combina e renderiza
            renderizarAlugueis(pedidosArray, contratosArray);
            
        } catch (error) {
            console.error('Erro:', error);
            listaAlugueis.innerHTML = `
                <div class="empty-state">
                    <p>Erro ao carregar seus aluguéis: ${error.message}</p>
                </div>
            `;
        }
    }

    // Renderizar lista combinada de pedidos e contratos
    function renderizarAlugueis(pedidos, contratos) {
        // Filtra e valida os dados
        const pedidosValidos = (pedidos || []).filter(pedido => 
            pedido && pedido.veiculo && typeof pedido.veiculo.marca === 'string');
        
        const contratosValidos = (contratos || []).filter(contrato => 
            contrato && contrato.automovel && typeof contrato.automovel.marca === 'string');
        
        if (pedidosValidos.length === 0 && contratosValidos.length === 0) {
            listaAlugueis.innerHTML = `
                <div class="empty-state">
                    <p>Você não possui aluguéis ou pedidos</p>
                </div>
            `;
            return;
        }

        let htmlContent = '';
        
        // Adiciona os contratos (pagamento à vista)
        if (contratosValidos.length > 0) {
            htmlContent += `
                <div class="aluguel-section">
                    <h3>Contratos de Aluguel</h3>
                    ${contratosValidos.map(contrato => {
                        const automovel = contrato.automovel;
                        const marca = automovel.marca || 'Marca não informada';
                        const modelo = automovel.modelo || 'Modelo não informado';
                        const ano = automovel.ano || '';
                        const placa = automovel.placa || 'Não informada';
                        const matricula = automovel.matricula || 'Não informada';
                        
                        return `
                            <div class="aluguel-card contrato">
                                <h4>${marca} ${modelo} ${ano}</h4>
                                <div class="aluguel-info">
                                    <div class="aluguel-info-item">
                                        <div class="aluguel-info-label">Placa</div>
                                        <div class="aluguel-info-value">${placa}</div>
                                    </div>
                                    <div class="aluguel-info-item">
                                        <div class="aluguel-info-label">Matrícula</div>
                                        <div class="aluguel-info-value">${matricula}</div>
                                    </div>
                                    <div class="aluguel-info-item">
                                        <div class="aluguel-info-label">Tipo</div>
                                        <div class="aluguel-info-value">${contrato.tipo || 'NORMAL'}</div>
                                    </div>
                                    <div class="aluguel-info-item">
                                        <div class="aluguel-info-label">Status</div>
                                        <div class="aluguel-info-value status-ativo">
                                            Contrato Ativo
                                        </div>
                                    </div>
                                </div>
                                <div class="contrato-info">
                                    <div class="aluguel-info-label">Nº Contrato</div>
                                    <div class="aluguel-info-value">${contrato.id || '--'}</div>
                                </div>
                            </div>
                        `;
                    }).join('')}
                </div>
            `;
        }
        
        // Adiciona os pedidos (pagamento por crédito)
        if (pedidosValidos.length > 0) {
            htmlContent += `
                <div class="aluguel-section">
                    <h3>Pedidos de Aluguel</h3>
                    ${pedidosValidos.map(pedido => {
                        const veiculo = pedido.veiculo;
                        const marca = veiculo.marca || 'Marca não informada';
                        const modelo = veiculo.modelo || 'Modelo não informado';
                        const ano = veiculo.ano || '';
                        const placa = veiculo.placa || 'Não informada';
                        const matricula = veiculo.matricula || 'Não informada';
                        
                        return `
                            <div class="aluguel-card pedido">
                                <h4>${marca} ${modelo} ${ano}</h4>
                                <div class="aluguel-info">
                                    <div class="aluguel-info-item">
                                        <div class="aluguel-info-label">Placa</div>
                                        <div class="aluguel-info-value">${placa}</div>
                                    </div>
                                    <div class="aluguel-info-item">
                                        <div class="aluguel-info-label">Matrícula</div>
                                        <div class="aluguel-info-value">${matricula}</div>
                                    </div>
                                    <div class="aluguel-info-item">
                                        <div class="aluguel-info-label">Status</div>
                                        <div class="aluguel-info-value status-${getStatusClass(pedido)}">
                                            ${getStatusText(pedido)}
                                        </div>
                                    </div>
                                </div>
                                ${pedido.contrato ? `
                                <div class="contrato-info">
                                    <div class="aluguel-info-label">Contrato</div>
                                    <div class="aluguel-info-value">Nº ${pedido.contrato.id || '--'}</div>
                                </div>
                                ` : ''}
                            </div>
                        `;
                    }).join('')}
                </div>
            `;
        }
        
        listaAlugueis.innerHTML = htmlContent;
    }

    // Obter classe CSS para o status
    function getStatusClass(pedido) {
        if (!pedido) return 'desconhecido';
        if (pedido.contrato) return 'ativo';
        if (pedido.aprovado) return 'aprovado';
        return 'pendente';
    }

    // Obter texto do status
    function getStatusText(pedido) {
        if (!pedido) return 'Status desconhecido';
        if (pedido.contrato) return 'Contrato Ativo';
        if (pedido.aprovado) return 'Aprovado - Aguardando contrato';
        return 'Aguardando aprovação';
    }

    // Formatar data
    function formatarData(dataString) {
        if (!dataString) return '-';
        const options = { day: '2-digit', month: '2-digit', year: 'numeric' };
        return new Date(dataString).toLocaleDateString('pt-BR', options);
    }

    // Modo edição
    btnEditar.addEventListener('click', () => {
        // Remove readonly apenas do Nome e Profissão
        const inputsEditaveis = form.querySelectorAll('#dadoNome, #dadoProfissao');
        inputsEditaveis.forEach(input => input.removeAttribute('readonly'));
        
        document.querySelector('.form-actions').style.display = 'flex';
        btnEditar.style.display = 'none';
    });
    
    // Cancelar edição - modifique esta função
    btnCancelar.addEventListener('click', () => {
        // Adiciona readonly nos campos editáveis (Nome e Profissão)
        const inputsEditaveis = form.querySelectorAll('#dadoNome, #dadoProfissao');
        inputsEditaveis.forEach(input => input.setAttribute('readonly', true));
        
        document.querySelector('.form-actions').style.display = 'none';
        btnEditar.style.display = 'block';
        carregarDadosCliente(); // Recarrega os dados originais
    });
    // Salvar edição
    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const dadosAtualizados = {
            nome: document.getElementById('dadoNome').value,
            profissao: document.getElementById('dadoProfissao').value,
            rg: document.getElementById('dadoRg').value
        };

        try {
            const response = await fetch(`http://localhost:8080/api/clientes/cpf/${clienteLogado.cpf}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(dadosAtualizados)
            });

            if (!response.ok) throw new Error('Erro ao atualizar dados');

            alert('Dados atualizados com sucesso!');
            form.classList.add('readonly');
            form.classList.remove('editing');
            document.querySelector('.form-actions').style.display = 'none';
            btnEditar.style.display = 'block';
            
            // Atualiza o nome no localStorage se foi alterado
            if (clienteLogado.nome !== dadosAtualizados.nome) {
                clienteLogado.nome = dadosAtualizados.nome;
                localStorage.setItem('clienteLogado', JSON.stringify(clienteLogado));
            }
        } catch (error) {
            console.error('Erro:', error);
            alert('Erro ao atualizar dados');
        }
    });

    // Logout
    btnLogout.addEventListener('click', () => {
        if (confirm('Tem certeza que deseja sair do sistema?')) {
            localStorage.removeItem('clienteLogado');
            window.location.href = 'login.html';
        }
    });

    // Inicialização
    await carregarDadosCliente();
});