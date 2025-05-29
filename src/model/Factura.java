package model;

import java.time.Duration;

public class Factura {
    private RegistroParqueo registro;
    private double monto;

    public Factura(RegistroParqueo registro) {
        this.registro = registro;
        this.monto = calcularMonto();
    }

    private double calcularMonto() {
        if (registro.getHoraSalida() == null || registro.getHoraIngreso() == null) return 0;
        long minutos = Duration.between(registro.getHoraIngreso(), registro.getHoraSalida()).toMinutes();
        double tarifaHora = 5.0;
        return (minutos / 60.0) * tarifaHora;
    }

    public RegistroParqueo getRegistro() {
        return registro;
    }

    public double getMonto() {
        return monto;
    }

    @Override
    public String toString() {
        return "Factura [registro=" + registro + ", monto=" + monto + "]";
    }
}
