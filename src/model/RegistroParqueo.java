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



    public Vehiculo getVehiculo() { return vehiculo; }
    public LocalDateTime getHoraIngreso() { return horaIngreso; }
    public LocalDateTime getHoraSalida() { return horaSalida; }

    @Override
    public String toString() {
        return "Vehículo: " + vehiculo.getPlaca();

    }
}
