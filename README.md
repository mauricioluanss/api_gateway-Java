INTEGRAÇÃO API GATEWAY em Java

O código implementa um sistema de integração de pagamentos via requisições HTTP. A classe Main gerencia a interação com o usuário, oferecendo um menu para selecionar o tipo de pagamento (débito ou crédito) e capturar o valor da transação. Com base na escolha, os métodos debito e credito chamam a classe RequestController, que lida com as requisições.

RequestController autentica o usuário através do método TokenRequest, que obtém um token de um endpoint configurado via variáveis de ambiente. Esse token é usado no método chamaPagamento, responsável por enviar a requisição de pagamento à API. O corpo da requisição é estruturado pelo método body da classe Body, que monta um JSON contendo os detalhes da transação, incluindo valor, método de pagamento e um correlationId gerado pela classe MyUuidApp. O programa garante que os pagamentos sejam enviados de forma segura e estruturada, facilitando a integração com um sistema de automação comercial.
