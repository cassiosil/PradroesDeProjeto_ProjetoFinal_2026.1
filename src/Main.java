import java.util.Optional;
import java.util.Scanner;
import model.Pedido;
import model.TipoEntrega;
import pattern.facade.SistemaPedidosFacade;
import pattern.strategy.pagamento.*;

public class Main {

        public static void main(String[] args) {
                SistemaPedidosFacade sistema = new SistemaPedidosFacade();
                Scanner scanner = new Scanner(System.in);
                boolean executando = true;

                while (executando) {
                        limparTela();
                        exibirMenu();
                        System.out.print("Escolha uma opção: ");

                        String opcaoInput = scanner.nextLine();
                        int opcao;

                        try {
                                opcao = Integer.parseInt(opcaoInput);
                        } catch (NumberFormatException e) {
                                limparTela();
                                System.out.println("⚠️ Opção inválida! Digite apenas números.");
                                pausar(scanner);
                                continue;
                        }

                        switch (opcao) {
                                case 1:
                                        limparTela();
                                        criarNovoPedido(sistema, scanner);
                                        pausar(scanner);
                                        break;
                                case 2:
                                        limparTela();
                                        listarTodosPedidos(sistema);
                                        pausar(scanner);
                                        break;
                                case 3:
                                        limparTela();
                                        buscarPedidoPorId(sistema, scanner);
                                        pausar(scanner);
                                        break;
                                case 4:
                                        limparTela();
                                        avancarStatusPedido(sistema, scanner);
                                        pausar(scanner);
                                        break;
                                case 5:
                                        limparTela();
                                        cancelarPedido(sistema, scanner);
                                        pausar(scanner);
                                        break;
                                case 0:
                                        executando = false;
                                        limparTela();
                                        System.out.println("\nEncerrando o sistema... Até logo!");
                                        break;
                                default:
                                        limparTela();
                                        System.out.println("⚠️ Opção inválida! Tente novamente.");
                                        pausar(scanner);
                        }
                }

                scanner.close();
        }

        private static void limparTela() {
                try {
                        if (System.getProperty("os.name").contains("Windows")) {
                                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                        } else {
                                new ProcessBuilder("clear").inheritIO().start().waitFor();
                        }
                } catch (Exception e) {

                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                }
        }

        private static void pausar(Scanner scanner) {
                System.out.println("\nPressione [ENTER] para continuar...");
                scanner.nextLine();
        }

        private static void exibirMenu() {
                System.out.println("=== BEM-VINDO AO SISTEMA DE GESTÃO DE PEDIDOS ===");
                System.out.println("---------------- MENU PRINCIPAL ----------------");
                System.out.println("1. Criar Novo Pedido");
                System.out.println("2. Listar Todos os Pedidos");
                System.out.println("3. Buscar Detalhes do Pedido por ID");
                System.out.println("4. Avançar Status do Pedido");
                System.out.println("5. Cancelar Pedido");
                System.out.println("0. Sair");
                System.out.println("------------------------------------------------");
        }

        private static void criarNovoPedido(SistemaPedidosFacade sistema, Scanner scanner) {
                System.out.println("--- [ NOVO PEDIDO ] ---");

                System.out.print("Nome do Cliente: ");
                String nome = scanner.nextLine();

                System.out.print("E-mail do Cliente: ");
                String email = scanner.nextLine();

                System.out.print("Nome do Produto: ");
                String produto = scanner.nextLine();

                System.out.print("Quantidade: ");
                int quantidade = Integer.parseInt(scanner.nextLine());

                System.out.print("Preço Unitário (ex: 150.00): ");
                double preco = Double.parseDouble(scanner.nextLine().replace(",", "."));

                System.out.println("\nEscolha a Modalidade de Entrega:");
                System.out.println("1. PAC");
                System.out.println("2. SEDEX");
                System.out.println("3. TRANSPORTADORA");
                System.out.print("Opção: ");
                int opEntrega = Integer.parseInt(scanner.nextLine());

                TipoEntrega entrega;
                switch (opEntrega) {
                        case 2:
                                entrega = TipoEntrega.SEDEX;
                                break;
                        case 3:
                                entrega = TipoEntrega.TRANSPORTADORA;
                                break;
                        default:
                                entrega = TipoEntrega.PAC;
                }

                System.out.println("\nEscolha a Forma de Pagamento:");
                System.out.println("1. PIX (10% de Desconto)");
                System.out.println("2. Boleto (5% de Desconto)");
                System.out.println("3. Cartão à Vista");
                System.out.println("4. Cartão Parcelado (5% de Acréscimo)");
                System.out.print("Opção: ");
                int opPagamento = Integer.parseInt(scanner.nextLine());

                ProcessadorPagamento pagamento;
                switch (opPagamento) {
                        case 1:
                                pagamento = new PagamentoPix();
                                break;
                        case 2:
                                pagamento = new PagamentoBoleto();
                                break;
                        case 4:
                                pagamento = new PagamentoCartaoParcelado();
                                break;
                        default:
                                pagamento = new PagamentoCartaoVista();
                }

                Pedido novoPedido = sistema.criarPedidoSimples(
                                nome, email, produto, quantidade, preco, entrega, pagamento);

                System.out.println("\n✅ Pedido #" + novoPedido.getId() + " criado com sucesso!");
        }

        private static void listarTodosPedidos(SistemaPedidosFacade sistema) {
                System.out.println("--- [ LISTA DE PEDIDOS ] ---");
                if (sistema.listarPedidos().isEmpty()) {
                        System.out.println("Nenhum pedido cadastrado até o momento.");
                        return;
                }
                sistema.listarPedidos().forEach(sistema::exibeResumoPedido);
        }

        private static void buscarPedidoPorId(SistemaPedidosFacade sistema, Scanner scanner) {
                System.out.println("--- [ BUSCAR PEDIDO ] ---");
                System.out.print("Digite o ID do Pedido: ");
                int id = Integer.parseInt(scanner.nextLine());

                Optional<Pedido> pedidoOpt = sistema.buscarPorId(id);
                if (pedidoOpt.isPresent()) {
                        sistema.exibeResumoPedido(pedidoOpt.get());
                } else {
                        System.out.println("⚠️ Pedido com ID #" + id + " não encontrado.");
                }
        }

        private static void avancarStatusPedido(SistemaPedidosFacade sistema, Scanner scanner) {
                System.out.println("--- [ AVANÇAR STATUS ] ---");
                System.out.print("Digite o ID do Pedido que deseja avançar o status: ");
                int id = Integer.parseInt(scanner.nextLine());

                Optional<Pedido> pedidoOpt = sistema.buscarPorId(id);
                if (pedidoOpt.isPresent()) {
                        System.out.println();
                        pedidoOpt.get().avancarStatus();
                } else {
                        System.out.println("⚠️ Pedido com ID #" + id + " não encontrado.");
                }
        }

        private static void cancelarPedido(SistemaPedidosFacade sistema, Scanner scanner) {
                System.out.println("--- [ CANCELAR PEDIDO ] ---");
                System.out.print("Digite o ID do Pedido que deseja cancelar: ");
                int id = Integer.parseInt(scanner.nextLine());

                Optional<Pedido> pedidoOpt = sistema.buscarPorId(id);
                if (pedidoOpt.isPresent()) {
                        System.out.println();
                        pedidoOpt.get().cancelarPedido();
                } else {
                        System.out.println("⚠️ Pedido com ID #" + id + " não encontrado.");
                }
        }
}