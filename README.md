Padrões de Projeto - 2026.1

Prof - Felipe Gustavo de Souza Gomes

Alunos - Cassio Silva dos Santos, Paulo Victor Chagas de Jesus
         
Projeto final - Gerenciamento de pedidos

Rode os comandos abaixo no terminal: 

javac -d bin src/Main.java

java -cp bin Main

---------------------------------------------

Aplicação desenvolvida em Java orientada a objetos para simulação, gerenciamento e processamento de pedidos de um e-commerce em memória (execução via Console). O projeto foca no uso consciente de Padrões de Projeto e aplicação dos princípios SOLID. 

---------------------------------------------

1. Singleton (Criacional)

Problema: Múltiplas partes da aplicação precisam manipular a lista global de pedidos em memória. Criar várias instâncias de um repositório resultaria em incoerência, duplicidade e perda de dados entre operações.

Por que utilizar: Garante a existência de uma e apenas uma instância do gerenciador de pedidos durante toda a execução do programa, fornecendo um ponto de acesso global e centralizado para persistência e geração de IDs únicos.

Como foi implementado: Construtor privado para evitar instanciações externas, atributo estático privado e final contendo a instância única (INSTANCIA) e o método estático público getInstancia().

Classes e/ou interfaces envolvidas: GerenciadorPedidos.

Benefícios: Centralização da base de dados em memória, consistência no estado global dos pedidos e gerenciamento thread-safe do sequencial de IDs.

Localização no código: src/repository/GerenciadorPedidos.java


2. Builder (Criacional)

Problema: A criação do objeto Pedido exige a adição incremental de múltiplos componentes (cliente, lista de itens, forma de pagamento, modalidade de frete e observadores), o que geraria construtores gigantes e confusos.

Por que utilizar: Permite a construção fluente, intuitiva  de objetos complexos.

Como foi implementado: Foi criada a classe PedidoBuilder com métodos encadeados que retornam a própria instância (paraCliente(), comItem(), comEntrega(), comPagamento(), comObservador()) e o método finalizador build().

Classes e/ou interfaces envolvidas: PedidoBuilder, Pedido, Cliente, ItemPedido, 
ObservadorStatus, ProcessadorPagamento.

Benefícios: Clareza na instanciação de pedidos.

Localização no código: src/pattern/builder/PedidoBuilder.java


3. Factory Method (Criacional)

Problema: A definição da estratégia de frete adequada depende do tipo selecionado pelo usuário (TipoEntrega), o que espalharia estruturas de decisão switch/if nas camadas superiores.

Por que utilizar: Encapsula e centraliza a lógica de instanciação dos algoritmos de frete, isolando a decisão de criação das classes concretas.

Como foi implementado: A classe FreteFactory fornece o método estático criarCalculador(TipoEntrega tipo), que avalia o enum informado e retorna a instância apropriada da interface CalculadorFrete.

Classes e/ou interfaces envolvidas: FreteFactory, CalculadorFrete, FretePac, FreteSedex, FreteTransportadora, TipoEntrega.

Benefícios: Redução do acoplamento entre as camadas de negócio e as implementações concretas de frete, facilitando a inclusão de novas modalidades.

Localização no código: src/pattern/strategy/frete/FreteFactory.java


4. Facade (Estrutural)

Problema: A execução de operações na interface de console exigiria a manipulação direta e complexa de repositórios, construtores (Builders), instâncias de pagamento, frete e observadores.

Por que utilizar: Oferece uma interface simplificada e unificada para o conjunto de subsistemas mais complexos da aplicação.

Como foi implementado: A classe SistemaPedidosFacade agrupa e coordena casos de uso de alto nível (criar pedido simples, listar pedidos, buscar por ID e exibir resumos), gerenciando internamente o GerenciadorPedidos, o PedidoBuilder e os observadores.

Classes e/ou interfaces envolvidas: SistemaPedidosFacade, GerenciadorPedidos, PedidoBuilder, Pedido, Main.

Benefícios: Simplificação da camada de apresentação (Main) e alto grau de desacoplamento das regras internas do sistema.

Localização no código: src/pattern/facade/SistemaPedidosFacade.java


5. Strategy (Comportamental)

Problema: O cálculo do valor total varia de acordo com as regras financeiras da forma de pagamento (descontos no Pix e Boleto, acréscimos no Cartão Parcelado) e da modalidade de frete selecionada (PAC, Sedex, Transportadora). Tratar essas variações com condicionais na classe Pedido violaria o princípio SOLID Open/Closed (OCP).

Por que utilizar: Permite isolar os algoritmos de cálculo em classes independentes com a mesma interface, possibilitando a troca dinâmica do comportamento em tempo de execução.

Como foi implementado: Foram criadas as interfaces ProcessadorPagamento e CalculadorFrete, implementadas por classes concretas com suas respetivas regras matemáticas.

Classes e/ou interfaces envolvidas: ProcessadorPagamento, PagamentoPix, PagamentoBoleto, PagamentoCartaoVista, PagamentoCartaoParcelado, CalculadorFrete, FretePac, FreteSedex, FreteTransportadora, Pedido.

Benefícios: Conformidade com o Princípio do Aberto/Fechado (OCP). Novas políticas de desconto, taxas ou frete podem ser adicionadas sem alterar o código existente.

Localização no código: Pacote src/pattern/strategy/


6. Observer (Comportamental)

Problema: Quando o status de um pedido sofre alteração, ações secundárias (como envio de e-mails de notificação e registro de auditoria em log) precisam ser disparadas sem acoplar a classe de domínio Pedido a serviços de infraestrutura.

Por que utilizar: Estabelece uma relação de dependência um-para-muitos entre objetos, de forma que quando o objeto principal muda de estado, seus dependentes são notificados e atualizados automaticamente.

Como foi implementado: Definida a interface ObservadorStatus com o método onStatusAlterado(). A classe Pedido mantém uma lista interna de observadores e invoca suas atualizações dentro do método setStatus().

Classes e/ou interfaces envolvidas: ObservadorStatus, NotificadorEmail, NotificadorLog, Pedido.

Benefícios: Desacoplamento total entre as entidades de negócio e o envio de notificações ou geração de logs.

Localização no código: Pacote src/pattern/observer/
