package pattern.strategy.pagamento;

public class PagamentoCartaoParcelado implements ProcessadorPagamento {
    @Override
    public double calcularValorAjustado(double valorBase) {
        return valorBase * 1.05; // 5% acréscimo
    }

    @Override
    public String getDescricao() {
        return "Cartão Parcelado (5% acréscimo)";
    }
}
