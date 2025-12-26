<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>README - Sistema Hospitalar</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            line-height: 1.6;
            color: #333;
            max-width: 900px;
            margin: 0 auto;
            padding: 20px;
            background-color: #f4f7f6;
        }
        header {
            background-color: #2c3e50;
            color: white;
            padding: 20px;
            text-align: center;
            border-radius: 8px 8px 0 0;
        }
        section {
            background: white;
            padding: 20px;
            margin-bottom: 20px;
            border-radius: 0 0 8px 8px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        h2 {
            color: #2980b9;
            border-bottom: 2px solid #eee;
            padding-bottom: 10px;
        }
        .service-card {
            border-left: 5px solid #2980b9;
            background: #eef7fa;
            padding: 15px;
            margin: 10px 0;
        }
        .highlight {
            font-weight: bold;
            color: #c0392b;
        }
        ul {
            padding-left: 20px;
        }
        li {
            margin-bottom: 10px;
        }
        code {
            background: #eee;
            padding: 2px 5px;
            border-radius: 4px;
            font-family: 'Courier New', Courier, monospace;
        }
    </style>
</head>
<body>

<header>
    <h1>🏥 Sistema de Gerenciamento Hospitalar</h1>
    <p>Arquitetura de Microsserviços com Spring Boot e RabbitMQ</p>
</header>

<section>
    <h2>🏗️ Arquitetura do Sistema</h2>
    <p>O ecossistema é dividido em três serviços principais que colaboram para gerenciar o fluxo do paciente:</p>

    <div class="service-card">
        <strong>1. Agendamento Service (Porta 8081):</strong> 
        Responsável pelo cadastro de pacientes e marcação inicial de consultas e exames.
    </div>
    
    <div class="service-card">
        <strong>2. Clínica Service (Porta 8082):</strong> 
        Gerencia o corpo médico, catálogo de sintomas e realiza o atendimento clínico com diagnósticos.
    </div>
    
    <div class="service-card">
        <strong>3. Centro Laboratório (Porta 8083):</strong> 
        Processa exames de alta complexidade e cirurgias, gerenciando prioridades críticas.
    </div>
</section>

<section>
    <h2>🚀 Principais Funcionalidades</h2>
    <ul>
        <li><strong>Validação Síncrona:</strong> O Agendamento consulta a Clínica em tempo real para verificar se o médico está disponível antes de confirmar.</li>
        <li><strong>Mensageria Assíncrona:</strong> Utiliza CloudAMQP (RabbitMQ) para enviar pedidos de exames da Clínica para o Laboratório sem travar o sistema.</li>
        <li><strong>Regra de Conflito:</strong> Impede que um mesmo CPF tenha agendamentos duplicados no mesmo horário (Retorno <code>409 Conflict</code>).</li>
        <li><strong>Sistema de Emergência:</strong> No laboratório, atendimentos "Emergenciais" podem ocupar horários já agendados por exames simples.</li>
    </ul>
</section>



<section>
    <h2>🛠️ Dicas para Iniciar o Fluxo</h2>
    <ol>
        <li><strong>Configuração RabbitMQ:</strong> Certifique-se de que a variável <code>${rabbit}</code> em todos os <code>application.yml</code> contém a URL <code>amqps://</code> do CloudAMQP.</li>
        <li><strong>População de Dados:</strong> Comece cadastrando Médicos e Sintomas na Clínica (Porta 8082) e Pacientes no Agendamento (Porta 8081).</li>
        <li><strong>Iniciando Atendimento:</strong> Após agendar uma consulta, use o endpoint <code>/AtenderConsulta</code> na Clínica para simular o diagnóstico.</li>
        <li><strong>Documentação:</strong> Use o Swagger em cada porta (8081, 8082, 8083) no caminho <code>/swagger-ui/index.html</code> para testar os endpoints interativamente.</li>
    </ol>
</section>

<section>
    <h2>⚠️ Observações Importantes</h2>
    <p>
        Para que o fluxo de exames funcione, a Clínica deve encontrar um agendamento prévio. Caso receba <span class="highlight">404 Not Found</span>, verifique se a mensagem do Agendamento chegou corretamente à tabela <code>db_requisicao_consulta</code> da Clínica via RabbitMQ.
    </p>
</section>

</body>
</html>