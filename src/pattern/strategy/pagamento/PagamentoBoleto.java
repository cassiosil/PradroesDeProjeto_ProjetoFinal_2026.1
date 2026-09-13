package pattern.strategy.pagamento;

public class PagamentoBoleto implements ProcessadorPagamento {
    @Override
    public double calcularValorAjustado(double valorBase) {
        return valorBase * 0.95; // 5% desconto
    }

    @Override
    public String getDescricao() {
        return "Boleto (5% de desconto)";
    }
}
