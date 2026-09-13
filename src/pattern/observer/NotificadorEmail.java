package pattern.observer;

import model.Pedido;
import model.StatusPedido;

public class NotificadorEmail implements ObservadorStatus {
    @Override
    public void onStatusAlterado(Pedido pedido, StatusPedido statusAntigo, StatusPedido novoStatus) {
        System.out.printf("[E-MAIL] Notificando %s (%s): O seu pedido #%d mudou de %s para %s.%n",
                pedido.getCliente().getNome(), pedido.getCliente().getEmail(), pedido.getId(), statusAntigo,
                novoStatus);
    }
}