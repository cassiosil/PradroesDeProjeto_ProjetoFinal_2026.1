package repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import model.Pedido;

public class GerenciadorPedidos {

    private static final GerenciadorPedidos INSTANCIA = new GerenciadorPedidos();

    private final List<Pedido> repositorioPedidos;
    private int contadorId;

    private GerenciadorPedidos() {
        this.repositorioPedidos = new ArrayList<>();
        this.contadorId = 1;
    }

    public static GerenciadorPedidos getInstancia() {
        return INSTANCIA;
    }

    public synchronized int proximoId() {
        return contadorId++;
    }

    public synchronized void salvar(Pedido pedido) {
        repositorioPedidos.add(pedido);
    }

    public List<Pedido> listarTodos() {
        return Collections.unmodifiableList(repositorioPedidos);
    }

    public Optional<Pedido> buscarPorId(int id) {
        return repositorioPedidos.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }
}