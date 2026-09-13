package pattern.strategy.frete;

import model.TipoEntrega;

public class FreteFactory {
    public static CalculadorFrete criarCalculador(TipoEntrega tipo) {
        if (tipo == null) throw new IllegalArgumentException("Tipo de entrega não pode ser nulo.");
        
        switch (tipo) {
            case PAC: return new FretePac();
            case SEDEX: return new FreteSedex();
            case TRANSPORTADORA: return new FreteTransportadora();
            default: throw new IllegalArgumentException("Modalidade de entrega desconhecida.");
        }
    }
}