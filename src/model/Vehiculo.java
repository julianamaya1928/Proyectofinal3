package model;

public abstract class Vehiculo {

    private String placa;
    private String color;
    private String modelo;
    private Cliente cliente;

    public Vehiculo(String placa, String color, String modelo, Cliente cliente) {
        this.placa = placa;
        this.color = color;
        this.modelo = modelo;
        this.cliente = cliente;
    }

    public String getPlaca() {
        return placa;
    }

    public String getColor() {
        return color;
    }

    public String getModelo() {
        return modelo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " - Placa: " + placa + ", Modelo: " + modelo + ", Color: " + color;
    }
}
