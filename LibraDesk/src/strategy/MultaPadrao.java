package strategy;

public class MultaPadrao implements CalculadoraMulta {
    
    @Override
    public double calcularMulta(int diasAtraso) {
        return diasAtraso;
    }
}
