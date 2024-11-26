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
        double resultado = (custoDrone * getDistancia()) + acrescimos;
        return resultado;
    }

    public String toString(){
        return "Número: "+getNumero()+", Nome Cliente: "+getNomeCliente()+", Descrição: "+getDescricao()+", Peso: "+getPeso()+", Latitude de Origem: "+getLatitudeOrigem()+"º, Latitude de Destino: "+getLatitudeDestino()+"º, Longitude de Origem: "+getLongitudeOrigem()+"º, Longitude de Destino: "+getLongitudeDestino()+"º, Situação: "+getSituacao()+", Distância: "+getDistancia()+" km, Quantidade de pessoas: "+this.qtdPessoas+"\n";
    }
}

