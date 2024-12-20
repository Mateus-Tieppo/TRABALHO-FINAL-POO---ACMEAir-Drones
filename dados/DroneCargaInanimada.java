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

    public String toString(){
        return "Código: "+getCodigo()+", Custo Fixo: "+getCustoFixo()+", Autonomia: "+getAutonomia()+", Peso Máximo: "+getPesoMaximo()+", Proteção: "+this.protecao;
    }
    public String saveString(){
        return 3+";"+getCodigo()+";"+getCustoFixo()+";"+getAutonomia()+";"+getPesoMaximo()+";"+this.protecao;
    }

    @Override
    public double calculaCustoKM() {
        double custoFixo = getCustoFixo();
        double custoVariado = protecao ? 10 : 5;
        double custoKM = custoFixo + custoVariado;
        return custoKM;
    }
}
