package service;

import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ParqueaderoService {

    private List<Cliente> clientes = new ArrayList<>();
    private List<Vehiculo> vehiculos = new ArrayList<>();
    private List<RegistroParqueo> registros = new ArrayList<>();
    private List<Pago> pagos = new ArrayList<>();
    private final int capacidadMaxima = 50;

    // Registrar cliente
    public void registrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    // Registrar vehículo
    public void registrarVehiculo(Vehiculo vehiculo) {
        vehiculos.add(vehiculo);
    }

    // Buscar cliente por cédula
    public Cliente buscarClientePorCedula(String cedula) {
        return clientes.stream()
                .filter(c -> c.getCedula().equals(cedula))
                .findFirst().orElse(null);
    }

    // Buscar vehículo por placa
    public Vehiculo buscarVehiculoPorPlaca(String placa) {
        return vehiculos.stream()
                .filter(v -> v.getPlaca().equalsIgnoreCase(placa))
                .findFirst().orElse(null);
    }

    // Registrar ingreso dado un vehículo
    public void registrarIngreso(Vehiculo vehiculo) {
        if (vehiculo != null) {
            registros.add(new RegistroParqueo(vehiculo));
        }
    }

    // Registrar ingreso dado placa (para uso desde Main)
    public void registrarIngreso(String placa) {
        Vehiculo vehiculo = buscarVehiculoPorPlaca(placa);
        if (vehiculo != null) {
            registrarIngreso(vehiculo);
            System.out.println("Ingreso registrado para vehículo con placa: " + placa);
        } else {
            System.out.println("Vehículo no encontrado para placa: " + placa);
        }
    }

    // Registrar salida por placa y crear pago temporal
    public void registrarSalida(String placa) {
        RegistroParqueo registro = registros.stream()
                .filter(r -> r.getVehiculo().getPlaca().equalsIgnoreCase(placa) && r.getHoraSalida() == null)
                .findFirst().orElse(null);

        if (registro != null) {
            registro.registrarSalida();
            double monto = new Factura(registro).getMonto();
            pagos.add(new Pago(Pago.Tipo.TEMPORAL, LocalDateTime.now(), monto));
            System.out.println("Salida registrada para vehículo con placa: " + placa + ", monto: $" + monto);
        } else {
            System.out.println("No se encontró ingreso activo para la placa: " + placa);
        }
    }

    // Obtener todos los registros de parqueo
    public List<RegistroParqueo> getRegistrosParqueo() {
        return registros;
    }

    // Obtener cupos disponibles
    public int getCuposDisponibles() {
        long ocupados = registros.stream()
                .filter(r -> r.getHoraSalida() == null)
                .count();
        return capacidadMaxima - (int) ocupados;
    }

    // Ingresar vehículo temporal (sin cliente)
    public void ingresarVehiculoTemporal(String placa, String tipo, String color, String modelo) {
        Vehiculo vehiculo;
        switch (tipo.toLowerCase()) {
            case "moto" -> vehiculo = new Moto(placa, color, modelo, null);
            case "camion" -> vehiculo = new Camion(placa, color, modelo, null);
            default -> vehiculo = new Automovil(placa, color, modelo, null);
        }
        registrarIngreso(vehiculo);
    }

    // Registrar membresía para cliente
    public void registrarMembresia(String cedula, Membresia.Tipo tipo) {
        Cliente cliente = buscarClientePorCedula(cedula);
        if (cliente != null) {
            Membresia membresia = new Membresia(tipo);
            cliente.setMembresia(membresia);
            pagos.add(new Pago(Pago.Tipo.MEMBRESIA, LocalDateTime.now(), membresia.getCosto()));
            System.out.println("Membresía registrada para cliente: " + cedula);
        } else {
            System.out.println("Cliente no encontrado para cédula: " + cedula);
        }
    }

    // Validar si cliente tiene membresía activa
    public boolean membresiaActiva(String cedula) {
        Cliente cliente = buscarClientePorCedula(cedula);
        if (cliente != null && cliente.getMembresia() != null) {
            return !cliente.getMembresia().getFechaVencimiento().isBefore(LocalDate.now());
        }
        return false;
    }

    // Obtener lista clientes
    public List<Cliente> getClientes() {
        return clientes;
    }

    // Obtener lista vehículos
    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    // Obtener lista pagos por tipo
    public List<Pago> getPagosPorTipo(Pago.Tipo tipo) {
        return pagos.stream()
                .filter(p -> p.getTipo() == tipo)
                .collect(Collectors.toList());
    }

    // Registrar pago por membresía manualmente
    public void registrarPagoMembresia(String cedula, double monto) {
        Cliente cliente = buscarClientePorCedula(cedula);
        if (cliente != null) {
            pagos.add(new Pago(Pago.Tipo.MEMBRESIA, LocalDateTime.now(), monto));
            System.out.println("Pago de membresía registrado para cliente: " + cedula + ", monto: $" + monto);
        } else {
            System.out.println("Cliente no encontrado para cédula: " + cedula);
        }
    }

    // Calcular ingresos por tipo y periodo
    public double calcularIngresosPorTipoYPeriodo(Pago.Tipo tipo, LocalDate inicio, LocalDate fin) {
        return pagos.stream()
                .filter(p -> p.getTipo() == tipo)
                .filter(p -> {
                    LocalDate fechaPago = p.getFechaPago().toLocalDate();
                    return (fechaPago.isEqual(inicio) || fechaPago.isAfter(inicio)) &&
                           (fechaPago.isEqual(fin) || fechaPago.isBefore(fin));
                })
                .mapToDouble(Pago::getMonto)
                .sum();
    }

    // Calcular ingresos totales (todos los pagos)
    public double calcularIngresosTotales() {
        return pagos.stream()
                .mapToDouble(Pago::getMonto)
                .sum();
    }

    // Obtener historial de vehículos por cliente
    public List<RegistroParqueo> getHistorialVehiculosPorCliente(String cedula) {
        return registros.stream()
                .filter(r -> r.getVehiculo().getPropietario() != null)
                .filter(r -> r.getVehiculo().getPropietario().getCedula().equals(cedula))
                .collect(Collectors.toList());
    }

    // Obtener clientes con membresías por vencer en X días
    public List<Cliente> clientesConMembresiasPorVencer(int dias) {
        LocalDate limite = LocalDate.now().plusDays(dias);
        return clientes.stream()
                .filter(c -> c.getMembresia() != null)
                .filter(c -> {
                    LocalDate vencimiento = c.getMembresia().getFechaVencimiento();
                    return vencimiento.isAfter(LocalDate.now()) && vencimiento.isBefore(limite);
                })
                .collect(Collectors.toList());
    }
}
