package pattern.observer;

import model.Pedido;
import model.StatusPedido;

public class NotificadorLog implements ObservadorStatus {
    @Override
    public void onStatusAlterado(Pedido pedido, StatusPedido statusAntigo, StatusPedido novoStatus) {
        System.out.printf("[LOG] Pedido #%d alterado: [%s -> %s].%n",
                pedido.getId(), statusAntigo, novoStatus);
    }
}
