package model;

import java.time.LocalDateTime;

public class RegistroParqueo {
    private Vehiculo vehiculo;
    private LocalDateTime horaIngreso;
    private LocalDateTime horaSalida;

    public RegistroParqueo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
        this.horaIngreso = LocalDateTime.now();
    }

    public void registrarSalida() {
        this.horaSalida = LocalDateTime.now();
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }