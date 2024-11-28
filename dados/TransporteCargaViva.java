package dados;

public class TransporteCargaViva extends Transporte {
    private double temperaturaMinima;
    private double temperaturaMaxima;

    public TransporteCargaViva(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem, double latitudeDestino, double longitudeOrigem, double longitudeDestino, double temperaturaMinima, double temperaturaMaxima) {
        super(numero, nomeCliente, descricao, peso, latitudeOrigem, latitudeDestino, longitudeOrigem, longitudeDestino);
        this.temperaturaMinima = temperaturaMinima;
        this.temperaturaMaxima = temperaturaMaxima;
    }

    public double getTemperaturaMaxima() {
        return this.temperaturaMaxima;
    }

    public double getTemperaturaMinima() {
        return this.temperaturaMinima;
    }

    @Override
    public double calculaCusto(Drone drone) {
        double custoDrone = drone.calculaCustoKM();
        double acrescimos = 0;
        if (temperaturaMaxima - temperaturaMinima > 10) {
            acrescimos += 1000;
        }
        double resultado = (custoDrone * getDistancia()) + acrescimos;
        return resultado;
    }

    public String toString(){
        return "Número: "+getNumero()+", Nome Cliente: "+getNomeCliente()+", Descrição: "+getDescricao()+", Peso: "+getPeso()+", Latitude de Origem: "+getLatitudeOrigem()+"º, Latitude de Destino: "+getLatitudeDestino()+"º, Longitude de Origem: "+getLongitudeOrigem()+"º, Longitude de Destino: "+getLongitudeDestino()+"º, Situação: "+getSituacao()+", Distância: "+getDistancia()+" km, Temperatura Mínima: "+this.temperaturaMinima+"º, Temperatura Máxima: "+this.temperaturaMaxima+"º\n";
    }
    public String saveString() {
        return 5 + ";" + getNumero() + ";" + getNomeCliente() + ";" + getDescricao() + ";" + getPeso() + ";" + getLatitudeOrigem() + ";" + getLatitudeDestino() + ";" + getLongitudeOrigem() + ";" + getLongitudeDestino() + ";" + this.temperaturaMinima + ";" + this.temperaturaMaxima;
    }
}

