package pattern.builder;

import model.*;
import pattern.observer.ObservadorStatus;
import pattern.strategy.pagamento.ProcessadorPagamento;

import java.util.ArrayList;
import java.util.List;

public class PedidoBuilder {
    private int id;
    private Cliente cliente;
    private final List<ItemPedido> itens = new ArrayList<>();
    private TipoEntrega tipoEntrega;
    private ProcessadorPagamento formaPagamento;
    private final List<ObservadorStatus> observadores = new ArrayList<>();

    public PedidoBuilder comId(int id) {
        this.id = id;
        return this;
    }

    public PedidoBuilder paraCliente(String nome, String email) {
        this.cliente = new Cliente(nome, email);
        return this;
    }

    public PedidoBuilder comItem(String produto, int qtd, double preco) {
        this.itens.add(new ItemPedido(produto, qtd, preco));
        return this;
    }

    public PedidoBuilder comEntrega(TipoEntrega entrega) {
        this.tipoEntrega = entrega;
        return this;
    }

    public PedidoBuilder comPagamento(ProcessadorPagamento pagamento) {
        this.formaPagamento = pagamento;
        return this;
    }

    public PedidoBuilder comObservador(ObservadorStatus obs) {
        this.observadores.add(obs);
        return this;
    }

    public Pedido build() {
        if (cliente == null)
            throw new IllegalStateException("Pedido precisa de um cliente.");
        if (itens.isEmpty())
            throw new IllegalStateException("Pedido precisa conter ao menos um item.");

        Pedido pedido = new Pedido(id, cliente);
        itens.forEach(pedido::adicionarItem);

        if (tipoEntrega != null)
            pedido.setTipoEntrega(tipoEntrega);
        if (formaPagamento != null)
            pedido.setFormaPagamento(formaPagamento);
        observadores.forEach(pedido::adicionarObservador);

        return pedido;
    }
}