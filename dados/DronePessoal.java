package dados;

public class DronePessoal extends Drone {
    private int qtdMaxPessoas;

    public DronePessoal (int codigo, double custoFixo, double autonomia, int qtdMaxPessoas){
        super(codigo, custoFixo, autonomia);
        this.qtdMaxPessoas = qtdMaxPessoas;
    }

    public int getQtdMaxPessoas(){
        return this.qtdMaxPessoas;
    }

    @Override
    public double calculaCustoKM(){
        double custoFixo = getCustoFixo();
        double custoVariado = qtdMaxPessoas * 2;
        return custoFixo + custoVariado;
    }
}
