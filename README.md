Gestão de Estoque para Pizzaria 

Descrição do Projeto:
Este projeto consiste em um sistema de gestão de estoque desenvolvido em Java, que tem como objetivo auxiliar uma pizzaria na organização e controle de seus ingredientes.
O sistema permite que a pizzaria gerencie o estoque de ingredientes, registre entradas e saídas de produtos, e obtenha relatórios sobre o estoque atual.

Funcionalidades:
Cadastro de ingredientes: permite adicionar novos ingredientes ao estoque da pizzaria, incluindo nome e quantidade inicial.
Atualização de estoque: possibilita atualizar a quantidade de ingredientes em estoque.
Relatórios de estoque: gera relatórios detalhados sobre o estoque atual, incluindo a quantidade disponível de cada ingrediente.
Interface de usuário amigável: oferece uma interface intuitiva e fácil de usar, permitindo que os funcionários da pizzaria gerenciem o estoque de forma eficiente.

Tecnologias Utilizadas:
Linguagem de Programação: Java
Banco de Dados: MySQL
Frameworks/Libraries: Java Swing, JDBC, MySQL Connector/J

Configuração do Banco de Dados MySQL:
Instale o MySQL em seu sistema, se ainda não estiver instalado.
Crie um banco de dados chamado pizzaria.
Crie uma tabela chamada ingredientes com os campos necessários para armazenar os dados dos ingredientes. Aqui está um exemplo de script SQL para criar a tabela:

CREATE TABLE ingredientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    quantidade INT NOT NULL,
    );
    


Contribuições são bem-vindas! Se você identificar qualquer problema ou tiver sugestões de melhorias, sinta-se à vontade para abrir uma issue ou enviar um pull request.
