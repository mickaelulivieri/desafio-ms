Aqui está o seu README simplificado, focado em texto e emojis, com separações claras para facilitar a leitura no terminal ou em editores de texto.

🏥 SISTEMA HOSPITALAR (MICROSERVIÇOS)
Arquitetura robusta para gestão de consultas, diagnósticos e exames laboratoriais.

🏗️ SERVIÇOS E PORTAS
📅 Agendamento (Porta: 8081)

Gestão de Pacientes e marcação de Consultas.

🩺 Clínica (Porta: 8082)

Corpo Médico, triagem de Sintomas e Atendimento.

🧪 Centro Médico / Laboratório (Porta: 8083)

Processamento de Exames e Procedimentos Cirúrgicos.

🚀 PASSO A PASSO PARA INICIAR
Mensageria: Configure a URL do CloudAMQP (${rabbit}) em todos os arquivos application.yml.

Base de Dados (Clínica): Cadastre os Médicos e os Sintomas (Porta 8082).

Base de Dados (Agendamento): Cadastre os Pacientes (Porta 8081).

Fluxo Principal: Realize um agendamento para validar a comunicação entre os serviços.

⚙️ REGRAS DE NEGÓCIO
🚫 Conflitos: Retorno 409 Conflict se o médico ou paciente já possuir agendamento no horário.

📬 Integração: Envio automático de pedidos de exames via RabbitMQ após o diagnóstico clínico.

⚠️ Emergência: Casos marcados como "Emergenciais" podem sobrepor horários já ocupados no laboratório.

📖 DOCUMENTAÇÃO (SWAGGER)
Acesse a interface interativa em cada serviço através da URL:

http://localhost:PORTA/swagger-ui/index.html