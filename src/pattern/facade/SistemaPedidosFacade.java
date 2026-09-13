package pattern.facade;

import model.Pedido;
import model.TipoEntrega;
import pattern.builder.PedidoBuilder;
import pattern.observer.NotificadorEmail;
import pattern.observer.NotificadorLog;
import pattern.strategy.pagamento.ProcessadorPagamento;
import repository.GerenciadorPedidos;

import java.util.List;
import java.util.Optional;

public class SistemaPedidosFacade {

    private final GerenciadorPedidos gerenciador;

    public SistemaPedidosFacade() {
        this.gerenciador = GerenciadorPedidos.getInstancia();
    }

    public Pedido criarPedidoSimples(String nomeCliente, String email, String produto,
            int quantidade, double preco, TipoEntrega entrega,
            ProcessadorPagamento pagamento) {

        int novoId = gerenciador.proximoId();

        Pedido pedido = new PedidoBuilder()
                .comId(novoId)
                .paraCliente(nomeCliente, email)
                .comItem(produto, quantidade, preco)
                .comEntrega(entrega)
                .comPagamento(pagamento)
                .comObservador(new NotificadorEmail())
                .comObservador(new NotificadorLog())
                .build();

        gerenciador.salvar(pedido);
        return pedido;
    }

    public List<Pedido> listarPedidos() {
        return gerenciador.listarTodos();
    }

    public Optional<Pedido> buscarPorId(int id) {
        return gerenciador.buscarPorId(id);
    }

    public void exibeResumoPedido(Pedido p) {
        System.out.println("==================================================");
        System.out.println("PEDIDO #" + p.getId() + " - Status: " + p.getStatus());
        System.out.println("Cliente: " + p.getCliente().getNome() + " (" + p.getCliente().getEmail() + ")");
        System.out.println("Itens:");
        p.getItens().forEach(i -> System.out.printf(" - %s x%d (R$ %.2f un) = R$ %.2f%n",
                i.getProduto(), i.getQuantidade(), i.getPrecoUnitario(), i.getSubtotal()));
        System.out.printf("Subtotal: R$ %.2f%n", p.getSubtotalItens());
        System.out.printf("Frete (%s): R$ %.2f%n", p.getTipoEntrega(), p.getValorFrete());
        System.out.printf("Forma de Pagamento: %s%n", p.getFormaPagamento().getDescricao());
        System.out.printf("VALOR FINAL COM AJUSTES/FRETE: R$ %.2f%n", p.getValorTotal());
        System.out.println("==================================================");
    }
}