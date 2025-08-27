## README do Projeto: Trancy Airlines - Sistema de Emissão de Passagens

-----

### Visão Geral

Este é um sistema simples de emissão e registro de passagens aéreas para a **Trancy Airlines**, uma companhia fictícia. O projeto foi desenvolvido com o objetivo de demonstrar a aplicação de boas práticas e padrões de projeto em uma aplicação web com **Java**.

-----

### Tecnologias Utilizadas

O projeto foi construído sobre uma pilha de tecnologias modernas e robustas, garantindo escalabilidade e facilidade de manutenção:

  * **Java:** Linguagem de programação principal.
  * **Spring Boot:** Framework para o desenvolvimento rápido da aplicação web. Ele cuida de grande parte da configuração, permitindo que o foco seja na lógica de negócio.
  * **Maven:** Ferramenta para gerenciar as dependências do projeto e automatizar o processo de compilação e empacotamento.
  * **HTML e CSS:** Utilizados para a criação da interface de usuário (front-end), garantindo uma experiência simples e visualmente agradável.

-----

### Padrões de Projeto

Para uma arquitetura organizada e sustentável, o projeto adota dois padrões de projeto fundamentais:

  * **MVC (Model-View-Controller):** Este padrão separa a aplicação em três camadas distintas para isolar as responsabilidades.

      * **Model:** Contém a lógica de negócio e os dados (classes `Voo`, `Destino`).
      * **View:** É a interface de usuário (arquivos `.html`) que exibe os dados.
      * **Controller:** Atua como intermediário, recebendo as requisições do usuário, processando-as com o `Model` e retornando a `View` apropriada.

  * **Factory (Fábrica):** Este padrão centraliza a lógica de criação de objetos. Em vez de instanciar classes de voo (`VooEconomico` ou `VooExecutivo`) diretamente no `Controller`, a responsabilidade é delegada a uma `VooFactory`, que decide qual objeto criar. Isso torna o código mais flexível e fácil de estender, caso novas classes de voo sejam adicionadas no futuro.

-----

### Funcionalidades

O sistema permite que o usuário:

  * Selecione o destino e a data do voo.
  * Escolha a classe (econômica ou executiva).
  * Emita a passagem.
  * Visualize as passagens emitidas em uma "carteira" que persiste os dados em um arquivo de texto.

-----

### Como Executar o Projeto

Para rodar a aplicação, siga os passos abaixo:

1.  Clone o repositório para a sua máquina.
2.  Navegue até a pasta raiz do projeto no seu terminal.
3.  Execute o comando Maven para iniciar a aplicação:
    ```bash
    mvn spring-boot:run
    ```
4.  Acesse o endereço `http://localhost:8080` no seu navegador.