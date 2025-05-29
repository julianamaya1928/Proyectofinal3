// ReportesYConsultas.java
package model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ReportesYConsultas {

    private List<RegistroParqueo> registros;
    private List<Pago> pagos;

    public ReportesYConsultas(List<RegistroParqueo> registros, List<Pago> pagos) {
        this.registros = registros;
        this.pagos = pagos;
    }

    // Total ingresos por tipo de pago en un rango de fechas
    public double totalIngresosPorTipoYPeriodo(Pago.Tipo tipo, LocalDateTime inicio, LocalDateTime fin) {
        return pagos.stream()
                .filter(p -> p.getTipo() == tipo)
                .filter(p -> !p.getFechaPago().isBefore(inicio) && !p.getFechaPago().isAfter(fin))  // Cambiado getFecha() -> getFechaPago()
                .mapToDouble(Pago::getMonto)
                .sum();
    }

    // Vehículos que ha ingresado un cliente (por cédula)
    public List<String> vehiculosPorCliente(String cedulaCliente) {
        return registros.stream()
                .filter(r -> r.getVehiculo() != null && r.getVehiculo().getPropietario() != null)
                .filter(r -> r.getVehiculo().getPropietario().getCedula().equals(cedulaCliente))
                .map(r -> r.getVehiculo().getPlaca() + " - " + r.getVehiculo().getModelo())  // Aquí ya funciona con getModelo()
                .distinct()
                .collect(Collectors.toList());
    }

    // Membresías por vencer en los próximos 'dias' días
    public List<Cliente> membresiasPorVencer(List<Cliente> clientes, int dias) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime limite = ahora.plusDays(dias);

        return clientes.stream()
                .filter(c -> c.getMembresia() != null)
                .filter(c -> {
                    LocalDateTime vencimiento = c.getMembresia().getFechaVencimiento().atStartOfDay();
                    return vencimiento.isAfter(ahora) && vencimiento.isBefore(limite);
                })
                .collect(Collectors.toList());
    }
}


