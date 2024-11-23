package dados;

public class TransporteCargaInanimada extends Transporte {
    private boolean cargaPerigosa;

    public TransporteCargaInanimada(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem, double latitudeDestino, double longitudeOrigem, double longitudeDestino, boolean cargaPerigosa) {
        super(numero, nomeCliente, descricao, peso, latitudeOrigem, latitudeDestino, longitudeOrigem, longitudeDestino);
        this.cargaPerigosa = cargaPerigosa;
    }

    public boolean getCargaPerigosa() {
        return this.cargaPerigosa;
    }

    @Override
    public double calculaCusto(Drone drone) {
        double custoDrone = drone.calculaCustoKM();
        double acrescimos = 0;
        if (cargaPerigosa) {
            acrescimos += 500;
        }
        return (custoDrone * getDistancia()) + acrescimos;
    }

    public String toString(){
        return "Número: "+getNumero()+", Nome Cliente: "+getNomeCliente()+", Descrição: "+getDescricao()+", Peso: "+getPeso()+", Latitude de Origem: "+getLatitudeOrigem()+"º, Latitude de Destino: "+getLatitudeDestino()+"º, Longitude de Origem: "+getLongitudeOrigem()+"º, Longitude de Destino: "+getLongitudeDestino()+"º, Situação: "+getSituacao()+", Distância: "+getDistancia()+" km, Carga Perigosa: "+this.cargaPerigosa+"\n";
    }
}

