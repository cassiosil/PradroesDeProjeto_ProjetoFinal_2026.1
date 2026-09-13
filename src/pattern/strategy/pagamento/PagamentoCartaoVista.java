package pattern.strategy.pagamento;

public class PagamentoCartaoVista implements ProcessadorPagamento {
    @Override
    public double calcularValorAjustado(double valorBase) {
        return valorBase; // 0% desconto
    }

    @Override
    public String getDescricao() {
        return "Cartão à Vista (sem desconto)";
    }
}
