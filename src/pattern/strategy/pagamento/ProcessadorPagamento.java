package pattern.strategy.pagamento;

public interface ProcessadorPagamento {
    double calcularValorAjustado(double valorBase);

    String getDescricao();
}
