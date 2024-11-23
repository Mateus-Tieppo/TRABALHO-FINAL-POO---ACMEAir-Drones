package dados;

public class TransportePessoal extends Transporte {
    private int qtdPessoas;

    public TransportePessoal(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem, double latitudeDestino, double longitudeOrigem, double longitudeDestino, int qtdPessoas) {
        super(numero, nomeCliente, descricao, peso, latitudeOrigem, latitudeDestino, longitudeOrigem, longitudeDestino);
        this.qtdPessoas = qtdPessoas;
    }

    public int getQtdPessoas() {
        return this.qtdPessoas;
    }

    @Override
    public double calculaCusto(Drone drone) {
        double custoDrone = drone.calculaCustoKM();
        double acrescimos = qtdPessoas * 10;
        return (custoDrone * getDistancia()) + acrescimos;
    }
}

