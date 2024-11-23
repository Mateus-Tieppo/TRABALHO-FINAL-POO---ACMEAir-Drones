package dados;

public abstract class Drone {
    private int codigo;
    private double autonomia;
    private double custoFixo;

    public Drone(int codigo, double custoFixo, double autonomia) {
        this.codigo = codigo;
        this.custoFixo = custoFixo;
        this.autonomia = autonomia;
    }

    public int getCodigo() {
        return codigo;
    }

    public double getAutonomia() {
        return autonomia;
    }

    public double getCustoFixo() {
        return custoFixo;
    }

    // Método abstrato que será implementado nas subclasses
    public abstract double calculaCustoKM();
}
