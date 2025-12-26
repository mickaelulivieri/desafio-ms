<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>README - Sistema Hospitalar</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; line-height: 1.4; color: #333; max-width: 700px; margin: 20px auto; padding: 20px; border: 1px solid #ddd; }
        h1 { color: #2c3e50; border-bottom: 2px solid #2c3e50; }
        h2 { color: #2980b9; margin-top: 25px; font-size: 1.2em; }
        .bloco { background: #f9f9f9; padding: 10px; border-left: 4px solid #2980b9; margin: 10px 0; }
        code { background: #eee; padding: 2px 4px; border-radius: 3px; }
        .emoji { margin-right: 8px; }
    </style>
</head>
<body>

    <h1>🏥 SISTEMA HOSPITALAR</h1>
    <p>Arquitetura de microsserviços para gestão de consultas e exames.</p>

    <h2>🏗️ SERVIÇOS E PORTAS</h2>
    <div class="bloco">
        <span class="emoji">📅</span> <strong>Agendamento:</strong> Porta <code>8081</code> (Pacientes e Consultas)
    </div>
    <div class="bloco">
        <span class="emoji">🩺</span> <strong>Clínica:</strong> Porta <code>8082</code> (Médicos e Diagnósticos)
    </div>
    <div class="bloco">
        <span class="emoji">🧪</span> <strong>Laboratório:</strong> Porta <code>8083</code> (Exames e Cirurgias)
    </div>

    <h2>🚀 PASSO A PASSO PARA INICIAR</h2>
    <ul>
        <li>Configurar <code>${rabbit}</code> no <code>application.yml</code> de cada serviço.</li>
        <li>Cadastrar <strong>Médicos</strong> e <strong>Sintomas</strong> na Clínica (8082).</li>
        <li>Cadastrar <strong>Pacientes</strong> no Agendamento (8081).</li>
        <li>Realizar agendamento para disparar o fluxo de atendimento.</li>
    </ul>

    <h2>⚙️ REGRAS DO SISTEMA</h2>
    <ul>
        <li><strong>Conflitos:</strong> Erro <code>409 Conflict</code> para horários ocupados.</li>
        <li><strong>Mensageria:</strong> Integração via RabbitMQ para exames.</li>
        <li><strong>Prioridade:</strong> Casos "Emergenciais" sobrepõem horários comuns.</li>
    </ul>

    <h2>📖 DOCUMENTAÇÃO</h2>
    <p>Acesse o Swagger UI em:</p>
    <code>http://localhost:PORTA/swagger-ui/index.html</code>

</body>
</html>