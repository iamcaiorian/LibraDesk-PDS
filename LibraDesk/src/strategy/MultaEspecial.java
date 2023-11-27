package strategy;

public class MultaEspecial implements ICalculadoraMulta {
    @Override
    public double calcularMulta(int diasAtraso) {
        return ((diasAtraso - 30) * 1.5) + 30; 
    }
}
