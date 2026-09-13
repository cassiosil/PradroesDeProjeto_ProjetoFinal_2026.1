package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import pattern.observer.ObservadorStatus;
import pattern.strategy.frete.CalculadorFrete;
import pattern.strategy.frete.FreteFactory;
import pattern.strategy.pagamento.ProcessadorPagamento;

public class Pedido {
    private final int id;
    private final Cliente cliente;
    private final List<ItemPedido> itens = new ArrayList<>();
    private final LocalDateTime dataCriacao;

    private StatusPedido status;
    private double valorFrete;
    private ProcessadorPagamento formaPagamento;
    private TipoEntrega tipoEntrega;

    private final List<ObservadorStatus> observadores = new ArrayList<>();

    public Pedido(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.dataCriacao = LocalDateTime.now();
        this.status = StatusPedido.CRIADO;
    }

    public void adicionarObservador(ObservadorStatus obs) {
        observadores.add(obs);
    }

    private void notificarObservadores(StatusPedido statusAntigo, StatusPedido novoStatus) {
        for (ObservadorStatus obs : observadores) {
            obs.onStatusAlterado(this, statusAntigo, novoStatus);
        }
    }

    public void setStatus(StatusPedido novoStatus) {
        if (this.status != novoStatus) {
            StatusPedido antigo = this.status;
            this.status = novoStatus;
            notificarObservadores(antigo, novoStatus);
        }
    }

    public void avancarStatus() {
        switch (this.status) {
            case CRIADO:
                setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
                break;
            case AGUARDANDO_PAGAMENTO:
                setStatus(StatusPedido.PAGO);
                break;
            case PAGO:
                setStatus(StatusPedido.EM_PREPARACAO);
                break;
            case EM_PREPARACAO:
                setStatus(StatusPedido.ENVIADO);
                break;
            case ENVIADO:
                setStatus(StatusPedido.ENTREGUE);
                break;
            case ENTREGUE:
                System.out.println("O pedido já foi entregue ao destino final.");
                break;
            case CANCELADO:
                System.out.println("Erro: Pedido cancelado não pode avançar de status.");
                break;
        }
    }

    public void cancelarPedido() {
        if (this.status == StatusPedido.ENVIADO || this.status == StatusPedido.ENTREGUE) {
            System.out.println("Erro: Não é possível cancelar um pedido que já foi enviado ou entregue.");
        } else if (this.status == StatusPedido.CANCELADO) {
            System.out.println("O pedido já se encontra cancelado.");
        } else {
            if (this.status == StatusPedido.PAGO || this.status == StatusPedido.EM_PREPARACAO) {
                System.out.println("Atenção: Pedido já pago. Iniciando estorno antes de cancelar...");
            }
            setStatus(StatusPedido.CANCELADO);
        }
    }

    public void adicionarItem(ItemPedido item) {
        itens.add(item);
    }

    public void setTipoEntrega(TipoEntrega tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
        CalculadorFrete calculador = FreteFactory.criarCalculador(tipoEntrega);
        this.valorFrete = calculador.calcularFrete(getSubtotalItens());
    }

    public void setFormaPagamento(ProcessadorPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public double getSubtotalItens() {
        return itens.stream().mapToDouble(ItemPedido::getSubtotal).sum();
    }

    public double getValorTotal() {
        double subtotalComFrete = getSubtotalItens() + valorFrete;
        if (formaPagamento != null) {
            return formaPagamento.calcularValorAjustado(subtotalComFrete);
        }
        return subtotalComFrete;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public double getValorFrete() {
        return valorFrete;
    }

    public ProcessadorPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public TipoEntrega getTipoEntrega() {
        return tipoEntrega;
    }
}