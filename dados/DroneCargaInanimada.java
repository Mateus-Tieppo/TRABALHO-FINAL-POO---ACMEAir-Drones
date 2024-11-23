package dados;

public class DroneCargaInanimada extends DroneCarga {
    private boolean protecao;

    public DroneCargaInanimada(int codigo, double custoFixo, double autonomia, double pesoMaximo, boolean protecao) {
        super(codigo, custoFixo, autonomia, pesoMaximo);
        this.protecao = protecao;
    }

    public boolean getProtecao() {
        return protecao;
    }

    @Override
    public double calculaCustoKM() {
        double custoFixo = getCustoFixo();
        double custoVariado = protecao ? 10 : 5;
        return custoFixo + custoVariado;
    }
}
