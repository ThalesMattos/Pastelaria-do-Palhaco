# Instruções de uso

## Clone o repositório:
- Abra um terminal e execute o seguinte comando para clonar o repositório:
	```bash
	git clone https://github.com/ThalesMattos/ppl-es-2024-1-lpm-noite-pastelaria-do-palhaco.git
	```
- Alternativamente, você pode fazer download do projeto na página deste repositório no GitHub. Para isso, clique em `Code > Download ZIP`
- Faça download do banco de dados MySql no link: https://www.mysql.com/downloads/, em seguida preencha os seguintes campos do `application.yml` com os do seu banco de dados:

![image](https://github.com/PUC-DISCIPLINAS/ppl-es-2024-1-lpm-noite-pastelaria-do-palhaco/assets/103903195/2dc37ca5-f548-4414-bf2e-6a18ab7d244a)

- Após completar os passos acima, rode a aplicação.

## Cliente Http

- Utilizando o cliente http de sua escolha, segue abaixo alguns comandos JSON importantes para fazer operações nesta aplicação:

### Mesa

- Criar mesa:
*POST*
`http://localhost:8080/mesas`
```bash
{
	"capacidade": "10",
	"ocupada": "false"
}
```

- Pegar todas as mesas:
*GET*
`http://localhost:8080/mesas`

### Requisição

- Pegar a lista de espera:
*GET* `http://localhost:8080/requisicoes/lista-de-espera`

- Criar requisições de mesa:
*POST*
`http://localhost:8080/requisicoes`
```bash
{
	"nome": "exemplo5",
	"numPessoas": "7"
}
```

- Criar requisições delivery:
*POST*
`http://localhost:8080/requisicoes`
```bash
{
	"nome": "exemplo4"
	"distanciaEmKm": 12
}
```

### Pedido

- Pedido para a mesa:
*POST*
`http://localhost:8080/pedido/{idCliente}`
```bash
{
	"idPedidos": [1, 2]
}
```

- Pedido delivery:
*POST*
`http://localhost:8080/pedido/delivery/{idCliente}`
```bash
{
	"idPedidos": [1, 2]
}
```

- Atualizar pedido:
*PUT*
`http://localhost:8080/pedido/{idCliente}`
```bash
{
	"produtos": [2, 1, 3]
}
```

- Encerrar pedido:
*DELETE*
`http://localhost:8080/pedido/{idCliente}`

- Pegar pedido do cliente
*GET* `http://localhost:8080/pedido/{idCliente}`

### Produto

- Pegar todos os produtos:
*GET*
`http://localhost:8080/produtos`

- Criar bebida:
*POST*
`http://localhost:8080/produtos`
```bash
{
	"tipo": "bebida",
	"valor": "15"
}
```

- Criar prato:
*POST*
`http://localhost:8080/produtos`
```bash
{
	"tipo": "prato",
	"valor": "15"
}
```

### Cliente

- Pegar cliente pelo Id:
*GET*
`http://localhost:8080/clientes/{idCliente}`

- Pegar todos os clientes:
*GET*
`http://localhost:8080/clientes`

### Pagamento

- Pix:
*POST*
`http://localhost:8080/pagamentos/pix/{idCliente}`
```bash
{
	"nomeEmitente": "seuNome"
	"valorPago": 11
}
```

- Dinheiro:
*POST*
`http://localhost:8080/pagamentos/dinheiro/{idCliente}`
```bash
{
	"valorPago": 11
}
```

- Crédito:
*POST*
`http://localhost:8080/pagamentos/credito/{idCliente}`
```bash
{
	"bandeiraCartao": "mastercard"
	"valorPago": 11
}
```

- Débito:
*POST*
`http://localhost:8080/pagamentos/debito/{idCliente}`
```bash
{
	"nomeBanco": "santander"
	"valorPago": 11
}
```

- Pix delivery:
*POST*
`http://localhost:8080/pagamentos/delivery/pix/{idCliente}`
```bash
{
	"nomeEmitente": "seuNome"
	"valorPago": 11
}
```

- Dinheiro delivery:
*POST*
`http://localhost:8080/pagamentos/delivery/dinheiro/{idCliente}`
```bash
{
	"valorPago": 11
}
```

- Crédito delivery:
*POST*
`http://localhost:8080/pagamentos/delivery/credito/{idCliente}`
```bash
{
	"bandeiraCartao": "mastercard"
	"valorPago": 11
}
```

- Débito delivery:
*POST*
`http://localhost:8080/pagamentos/delivery/debito/{idCliente}`
```bash
{
	"nomeBanco": "santander"
	"valorPago": 11
}
```


- Pegar pagamento pelo dia:
*GET*
`http://localhost:8080/pagamentos/{dia}`

- Pegar pagamentos futuros pelo dia:
*GET*
`http://localhost:8080/pagamentos/aReceber/{dia}`
