package model;

import java.time.LocalDate;

public class Membresia {

    public enum Tipo {
        MENSUAL, TRIMESTRAL, ANUAL
    }

    private Tipo tipo;
    private LocalDate fechaInicio;
    private LocalDate fechaVencimiento;
    private double costo;

    public Membresia(Tipo tipo) {
        this.tipo = tipo;
        this.fechaInicio = LocalDate.now();
        switch (tipo) {
            case MENSUAL:
                this.fechaVencimiento = fechaInicio.plusMonths(1);
                this.costo = 50000;  // ejemplo de costo
                break;
            case TRIMESTRAL:
                this.fechaVencimiento = fechaInicio.plusMonths(3);
                this.costo = 140000;
                break;
            case ANUAL:
                this.fechaVencimiento = fechaInicio.plusYears(1);
                this.costo = 500000;
                break;
        }
    }

    // GETTERS
    public Tipo getTipo() {
        return tipo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public double getCosto() {
        return costo;
    }

    // NUEVO MÉTODO para Main: verifica si la membresía está activa
    public boolean estaActiva() {
        return fechaVencimiento.isAfter(LocalDate.now()) || fechaVencimiento.isEqual(LocalDate.now());
    }
}
