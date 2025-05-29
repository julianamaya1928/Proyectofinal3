// Pago.java
package model;

import java.time.LocalDateTime;

public class Pago {

    public enum Tipo {
        TEMPORAL,
        MEMBRESIA
    }

    private Tipo tipo;
    private LocalDateTime fechaPago;
    private double monto;

    public Pago(Tipo tipo, LocalDateTime fechaPago, double monto) {
        this.tipo = tipo;
        this.fechaPago = fechaPago;
        this.monto = monto;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public double getMonto() {
        return monto;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "tipo=" + tipo +
                ", fechaPago=" + fechaPago +
                ", monto=" + monto +
                '}';
    }
}
