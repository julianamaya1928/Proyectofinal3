package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class RegistroParqueo {

    private Vehiculo vehiculo;
    private LocalDateTime horaIngreso;
    private LocalDateTime horaSalida;
    private double tarifaPorHora;

    public RegistroParqueo(Vehiculo vehiculo, double tarifaPorHora) {
        this.vehiculo = vehiculo;
        this.horaIngreso = LocalDateTime.now();
        this.tarifaPorHora = tarifaPorHora;
    }

    public void registrarSalida() {
        this.horaSalida = LocalDateTime.now();
    }

    public long calcularHoras() {
        if (horaSalida == null) return 0;
        Duration duracion = Duration.between(horaIngreso, horaSalida);
        long minutos = duracion.toMinutes();
        return (minutos + 59) / 60;
    }

    public double calcularMonto() {
        return calcularHoras() * tarifaPorHora;
    }

    public Vehiculo getVehiculo() { return vehiculo; }
    public LocalDateTime getHoraIngreso() { return horaIngreso; }
    public LocalDateTime getHoraSalida() { return horaSalida; }

    @Override
    public String toString() {
        return "Vehículo: " + vehiculo.getPlaca() +
               ", Ingreso: " + horaIngreso +
               ", Salida: " + (horaSalida != null ? horaSalida : "Aún en parqueo") +
               ", Total: $" + (horaSalida != null ? calcularMonto() : 0);
    }
}
