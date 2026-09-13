package pattern.strategy.frete;

public class FretePac implements CalculadorFrete {
    @Override
    public double calcularFrete(double valorItens) {
        return valorItens * 0.05;
    }
}
