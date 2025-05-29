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

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public LocalDateTime getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(LocalDateTime horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public LocalDateTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalDateTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public void registrarSalida() {
        this.horaSalida = LocalDateTime.now();
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }
    }
