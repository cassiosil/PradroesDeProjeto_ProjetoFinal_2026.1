package pattern.strategy.pagamento;

public class PagamentoPix implements ProcessadorPagamento {
    @Override
    public double calcularValorAjustado(double valorBase) {
        return valorBase * 0.90; // 10% desconto
    }

    @Override
    public String getDescricao() {
        return "PIX (10% de desconto)";
    }
}
