package pattern.strategy.frete;

public class FreteTransportadora implements CalculadorFrete {
    @Override
    public double calcularFrete(double valorItens) {
        return valorItens * 0.15;
    }
}
