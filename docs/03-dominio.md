# Modelo de Domínio

## Entidades

### Usuário (Vira tabela)
- id
- nome
- email
- senha
- Produtos Cadastrados

### Produto (Vira tabela)
- id
- nome
- url
- Valor esperado (opcional)
- id do usuário

<!-- #### Informação Web do Produto (Vira tabela)
- id
- id do produto
- Url do produto
- XPATH do preço
- XPATH das estrelas
- XPATH da quantidade de avaliadores
- Nome da empresa vendedora -->

### Preço (Vira tabela)
- id
- Valor
- Data de scraping
- id do produto

<!-- ### Avaliação
- id produto
- Histórico de Estrelas
- Quantidade de avaliadores
  
### Estrelas (Vira tabela)
- Id do Produto
- Estrelas do produto
- Data de scraping -->

# Regras de Negócio

## Ações do Usuário
### RN001: Cadastro de Produto
**Descrição**: Deve ser possível cadastrar um produto para ser rastreado. 
O valor esperado para ser atingido é opcional

**Gatilho**: Requisição POST _/products_

**Exemplo**: Usuário quer rastrear uma geladeira, ele coloca as informações da geladeira e caso tudo dê certo ele fica rastreando-a

**Efeito**:
- Produto é salvo no banco
- Scraping inicial é executado
- Primeiro preço é registrado

**Regras Relacionadas**: 


### RN002: Criação de Conta
**Descrição**: Usuário para acessar sua página com seus produtos cadastrados deve fazer login. Adicionar email, uma senha e um username.

**Gatilho**: Requisição POST _/user_

**Exemplo**: 

**Efeito**:
- Conta é salva no banco

**Regras Relacionadas**: 

### RN003: Deletar os produtos cadastrados 

**Descrição**: Usuário entra no produto e deleta, o produto deverá ser excluído do banco dos todos os seus registros

**Gatilho**: DELETE _/products/\<id>_

**Exemplo**: Usuário lança o http DELETE /products/3, então é deletado do banco de dados o produto com esse _id_

**Regras Relacionadas**: 

### RN004: Atualizar produto 
**Descrição**: Usuário pode alterar qualquer dependência do produto com exceção à sua URL

**Gatilho**: PUT _/products_

**Exemplo**: Usuário escreveu o nome de um produto cadastrado errado, ele pode ir na aba de atualização e alterar o nome do produto.

**Regras Relacionadas**: 

### RN005: Alterar username 
**Descrição**: Usuário pode alterar seu username 

**Gatilho**: PUT _/user_ com o json contendo as alterações desejadas

**Exemplo**: Usuário possui username _joao123_ e altera para _joao456_

**Regras Relacionadas**: 

## Sistema
### RN101: Cadastro de produto
**Descrição**: Deve-se fazer uma coleta inicial do preço do produto cadastrado, a coleta deve conter também a data e hora

**Gatilho**: Cadastrar produto

**Exemplo**: Chega uma requisição de POST de um produto, os dados são válidos, então pega o valor do produto naquele instante e armazena

**Regras Relacionadas**: 
- RN001

### RN102: Periodicidade da coleta
**Descrição**:
O sistema deve realizar a coleta de preços dos produtos cadastrados de forma periódica.

**Gatilho**:
Agendamento automático do sistema

**Efeito**:
- Novo preço é coletado
- Histórico de preços é atualizado

**Regras Relacionadas**: 
