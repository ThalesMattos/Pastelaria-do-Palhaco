# Comidinhas Veganas - Pastelaria do Palhaço

A pastelaria do Palhaço é um sistema de restaurante que oferece diversos serviços, des de a criação de uma mesa até o pagamento dos clientes. Toda esta aplicação foi feita seguindo os requisitos das *sprints* abaixo.

## Tecnologias usadas

- Java Spring
- MySql

## Alunos integrantes da equipe

* Thales Eduardo Mattos

## Professores responsáveis

* Hugos Bastos de Paula

# Especificação
## Sprint 1

Um restaurante atende seus clientes alocando-os em mesas por meio de 'requisições por mesas'. No momento, o restaurante possui 10 mesas: 4 com capacidade para 4 pessoas, 4 com capacidade para 6 pessoas e 2 com capacidade para 8 pessoas. O restaurante deve ser capaz de ampliar a sua capacidade com o menor esforço possível.

O cliente, ao chegar, deve declarar quantas pessoas comerão no restaurante, gerando assim uma requisição por mesa. Tão logo quanto possível deverá ser alocada uma mesa adequada para ele. Se não houver mesa livre, o cliente entra numa fila de espera. As requisições precisam registrar as datas e horas de entrada e saída do cliente.

## Sprint 2

O restaurante oferece aos clientes um cardápio simplificado, como por exemplo:

*Pratos:*
- Moqueca de Palmito
- Falafel Assado
- Salada Primavera com Macarrão Konjac
- Escondidinho de Inhame
- Strogonoff de Cogumelos
- Caçarola de Carne com Legumes

*Bebidas:*
- Água
- Suco
- Refrigerante
- Cerveja
- Taça de Vinho

Cada mesa, enquanto está sendo servida, pode fazer um pedido com quantos pratos e bebidas desejar. Novos produtos podem ser adicionados ao pedido no decorrer do atendimento. Ao encerrar o pedido, a conta incluirá uma taxa de serviço de 10% e exibirá tanto o valor total como o valor a ser dividido igualmente entre os ocupantes.

## Sprint 3

Cada conta pode ser paga por diferentes métodos: dinheiro, Pix, débito ou crédito. O que muda para cada método são o prazo para receber o valor e a taxa que o restaurante paga ao banco por este recebimento. Além disso, para pagamento em Pix, deve ser armazenado o nome do emitente do Pix. No caso de débito, deve ser armazenado o nome do banco de origem do débito. Finalmente, no caso de cartão de crédito, deve ser armazenada a bandeira do cartão (Visa, Mastercard, Elo, American Express).

Dinheiro: prazo 0 dias, desconto 0%
Pix: prazo 0 dias, desconto de 1,45%, limitado a R$10
Débito: prazo 14 dias, desconto de 1,4%
Crédito: prazo 30 dias, desconto de 3,1%
O restaurante precisa saber quanto vendeu em um dia e quanto receberá em datas futuras a serem determinadas pela gerência.

Pedidos para delivery não incluem taxa de serviço, mas incluem taxa de entrega de acordo com a distância. Estes pedidos não precisam especificar o valor individual a ser pago em uma conta.
