package pattern.observer;

import model.Pedido;
import model.StatusPedido;

public interface ObservadorStatus {
    void onStatusAlterado(Pedido pedido, StatusPedido statusAntigo, StatusPedido novoStatus);
}