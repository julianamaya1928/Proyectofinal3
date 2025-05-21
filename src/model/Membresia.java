package model;

import java.time.LocalDate;

public class Membresia {

    public enum Tipo {
        MENSUAL, TRIMESTRAL, ANUAL
    }

    private Cliente cliente;
    private Tipo tipo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public Membresia(Cliente cliente, Tipo tipo, LocalDate fechaInicio, LocalDate fechaFin) {
        this.cliente = cliente;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public boolean estaActiva() {
        LocalDate hoy = LocalDate.now();
        return (hoy.isEqual(fechaInicio) || hoy.isAfter(fechaInicio)) && hoy.isBefore(fechaFin.plusDays(1));
    }

    public boolean proximaAVencer() {
        LocalDate hoy = LocalDate.now();
        return estaActiva() && !hoy.plusDays(5).isBefore(fechaFin);
    }

    @Override
    public String toString() {
        return "Membresía: " + tipo + " | Inicio: " + fechaInicio + " | Fin: " + fechaFin;
    }
}

