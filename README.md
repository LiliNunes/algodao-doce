# 🐰 Algodão Doce - Especial Páscoa 2026

Landing page premium e gamificada para a campanha de Páscoa da marca de doces artesanais *Algodão Doce*.

## 🌟 Características
- **Design Elegante e Rústico**: Uma paleta focada no artesanal (tons de bege cru, dourado suave, azul xadrez e marrom chocolate).
- **Sem Banco de Dados (Serverless)**: Foco total na privacidade do usuário e na segurança da transação. Nenhum dado de compra é salvo em servidores terceiros.
- **Integração Nativa WhatsApp**: Carrinho de compras robusto com conversão de pedido para uma string de texto elegante e formatada diretamente para a API do WhatsApp.

## 🛡️ Arquitetura de Segurança (Anti-MITM)
1. **HTTPS Forçado**: Ao hospedar este projeto no Vercel ou GitHub Pages, a criptografia SSL/TLS em trânsito é obrigatória. Um atacante *Homem-do-Meio* (MITM) na rede Wi-Fi não conseguirá injetar scripts maliciosos ou capturar os dados do cliente lendo a página.
2. **Sem Backend State**: Os dados sensíveis digitados no formulário de checkout ficam apenas na memória RAM local do navegador do cliente. Não existe API intermediária que possa ser hackeada para extração das informações.
3. **End-to-End Encryption (E2EE)**: A etapa de finalização redireciona a intenção de compra formatada para o App do WhatsApp. Assim, toda a comunicação entre o cliente e a doceria é criptografada ponta-a-ponta pelos protocolos invioláveis do WhatsApp/Signal, garantindo 100% de sigilo da transação.

## 🚀 Como Executar
1. Clone o repositório.
2. Abra `index.html` em seu navegador. (Futuramente será portado para Vite+React)
