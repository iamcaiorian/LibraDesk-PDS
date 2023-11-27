package strategy;

public class MultaInicial implements ICalculadoraMulta {
    @Override
    public double calcularMulta(int diasAtraso) {
        return diasAtraso;
    }
}
