package dados;

public abstract class Transporte {
    private int numero;
    private String nomeCliente;
    private String descricao;
    private double peso;
    private double latitudeOrigem;
    private double latitudeDestino;
    private double longitudeOrigem;
    private double longitudeDestino;
    private Estado situacao;
    private double distancia;

    public Transporte(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem, double latitudeDestino, double longitudeOrigem, double longitudeDestino) {
        this.numero = numero;
        this.nomeCliente = nomeCliente;
        this.descricao = descricao;
        this.peso = peso;
        this.latitudeOrigem = latitudeOrigem;
        this.latitudeDestino = latitudeDestino;
        this.longitudeOrigem = longitudeOrigem;
        this.longitudeDestino = longitudeDestino;
        this.situacao = Estado.PENDENTE;
        this.distancia = calcularDistancia();
    }

    public int getNumero() {
        return this.numero;
    }

    public String getNomeCliente() {
        return this.nomeCliente;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public double getPeso() {
        return this.peso;
    }

    public double getLatitudeOrigem() {
        return this.latitudeOrigem;
    }

    public double getLatitudeDestino() {
        return this.latitudeDestino;
    }

    public double getLongitudeOrigem() {
        return this.longitudeOrigem;
    }

    public double getLongitudeDestino() {
        return this.longitudeDestino;
    }

    public Estado getSituacao() {
        return this.situacao;
    }

    public void setSituacao(Estado situacao){
        this.situacao = situacao;
    }

    public double getDistancia() {
        return this.distancia;
    }

    // Método abstrato para cálculo de custo
    public abstract double calculaCusto(Drone drone);

    private double calcularDistancia() {
        double theta = longitudeOrigem - longitudeDestino;
        double distance = 60 * 1.1515 * (180/Math.PI) * Math.acos(
            Math.sin(latitudeOrigem * (Math.PI/180)) * Math.sin(latitudeDestino * (Math.PI/180)) +
            Math.cos(latitudeOrigem * (Math.PI/180)) * Math.cos(latitudeDestino * (Math.PI/180)) * Math.cos(theta *
            (Math.PI/180))
        );
        double resultado = distance * 1.609344;
        return resultado;
    }
}
