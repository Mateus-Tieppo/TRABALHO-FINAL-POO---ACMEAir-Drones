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

    public Transporte(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem, double latitudeDestino, double longitudeOrigem, double longitudeDestino, Estado situacao) {
        this.numero = numero;
        this.nomeCliente = nomeCliente;
        this.descricao = descricao;
        this.peso = peso;
        this.latitudeOrigem = latitudeOrigem;
        this.latitudeDestino = latitudeDestino;
        this.longitudeOrigem = longitudeOrigem;
        this.longitudeDestino = longitudeDestino;
        this.situacao = situacao;
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

    public double getDistancia() {
        return this.distancia;
    }

    // Método abstrato para cálculo de custo
    public abstract double calculaCusto(Drone drone);

    private double calcularDistancia() {
        return Math.sqrt(Math.pow(latitudeDestino - latitudeOrigem, 2) + Math.pow(longitudeDestino - longitudeOrigem, 2));
    }
}
