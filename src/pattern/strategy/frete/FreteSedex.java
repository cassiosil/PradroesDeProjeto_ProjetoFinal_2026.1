package pattern.strategy.frete;

public class FreteSedex implements CalculadorFrete {
    @Override
    public double calcularFrete(double valorItens) {
        return valorItens * 0.10;
    }
}
