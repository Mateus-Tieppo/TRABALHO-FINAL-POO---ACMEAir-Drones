package dados;

public class DroneCargaViva extends DroneCarga {
    private boolean climatizado;

    public DroneCargaViva (int codigo, double custoFixo, double autonomia, double pesoMaximo, boolean climatizado){
        super(codigo, custoFixo, autonomia, pesoMaximo);
        this.climatizado = climatizado;
    }

    public boolean getClimatizado(){
        return this.climatizado;
    }

    public String toString(){
        return "Código: "+getCodigo()+", Custo Fixo: "+getCustoFixo()+", Autonomia: "+getAutonomia()+", Peso Máximo: "+getPesoMaximo()+", Climatizado: "+this.climatizado;
    }
    public String saveString(){
        return 2+";"+getCodigo()+";"+getCustoFixo()+";"+getAutonomia()+";"+getPesoMaximo()+";"+this.climatizado;
    }

    @Override
    public double calculaCustoKM(){
        double custoFixo = getCustoFixo();
        double custoVariado = 0;
        if (climatizado){
            custoVariado += 20;
        } else {
            custoVariado += 10;
        }
        double custoKM = custoFixo + custoVariado;
        return custoKM;
    }
}
