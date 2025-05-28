package model;

public abstract class Vehiculo {
    protected String placa;
    protected String color;
    protected String modelo;
    protected Cliente propietario;

    public Vehiculo(String placa, String color, String modelo, Cliente propietario) {
        this.placa = placa;
        this.color = color;
        this.modelo = modelo;
        this.propietario = propietario;
    }

    public String getPlaca() { return placa; }
    public String getModelo() { return modelo; }  // <--- Agregado getter para modelo
    public void setColor(String color) { this.color = color; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public Cliente getPropietario() { return propietario; }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "placa='" + placa + '\'' +
                ", color='" + color + '\'' +
                ", modelo='" + modelo + '\'' +
                ", propietario=" + (propietario != null ? propietario.getNombre() : "N/A") +
                '}';
    }
}
