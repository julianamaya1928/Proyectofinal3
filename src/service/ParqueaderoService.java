package service;

import model.Cliente;
import model.Vehiculo;
import model.RegistroParqueo;
import model.Moto;
import model.Automovil;
import model.Camion;
import model.Membresia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ParqueaderoService {

    private ArrayList<Cliente> clientes;
    private ArrayList<Vehiculo> vehiculos;
    private ArrayList<RegistroParqueo> registros;
    private ArrayList<Membresia> membresias;

    private Map<String, Double> tarifasPorTipoVehiculo;

    // Capacidades máximas
    private int capacidadMotos = 10;
    private int capacidadAutomoviles = 20;
    private int capacidadCamiones = 5;

    // Espacios ocupados
    private int ocupadosMotos = 0;
    private int ocupadosAutomoviles = 0;
    private int ocupadosCamiones = 0;

    public ParqueaderoService() {
        clientes = new ArrayList<>();
        vehiculos = new ArrayList<>();
        registros = new ArrayList<>();
        membresias = new ArrayList<>();
        tarifasPorTipoVehiculo = new HashMap<>();

        tarifasPorTipoVehiculo.put("Moto", 1500.0);
        tarifasPorTipoVehiculo.put("Automovil", 3000.0);
        tarifasPorTipoVehiculo.put("Camion", 5000.0);
    }

    // Métodos de clientes y vehículos
    
    public void registrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public Cliente buscarClientePorCedula(String cedula) {
        for (Cliente cliente : clientes) {
            if (cliente.getCedula().equalsIgnoreCase(cedula)) {
                return cliente;
            }
        }
        return null;
    }

    public void modificarCliente(Scanner scanner) {
        System.out.print("Ingrese la cédula del cliente a modificar: ");
        String cedula = scanner.nextLine();

        Cliente cliente = buscarClientePorCedula(cedula);
        if (cliente == null) {
            System.out.println("❌ Cliente no encontrado.");
            return;
        }

        int opcion;
        do {
            System.out.println("\n--- Modificar Cliente ---");
            System.out.println("1. Modificar nombre");
            System.out.println("2. Modificar cédula");
            System.out.println("3. Modificar teléfono");
            System.out.println("4. Modificar correo");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Nuevo nombre: ");
                    cliente.setNombre(scanner.nextLine());
                    System.out.println("✅ Nombre actualizado.");
                    break;
                case 2:
                    System.out.print("Nueva cédula: ");
                    cliente.setCedula(scanner.nextLine());
                    System.out.println("✅ Cédula actualizada.");
                    break;
                case 3:
                    System.out.print("Nuevo teléfono: ");
                    cliente.setTelefono(scanner.nextLine());
                    System.out.println("✅ Teléfono actualizado.");
                    break;
                case 4:
                    System.out.print("Nuevo correo: ");
                    cliente.setCorreo(scanner.nextLine());
                    System.out.println("✅ Correo actualizado.");
                    break;
                case 0:
                    System.out.println("Saliendo de la modificación...");
                    break;
                default:
                    System.out.println("❌ Opción inválida.");
            }
        } while (opcion != 0);
    }

    public void registrarVehiculo(Vehiculo vehiculo) {
        vehiculos.add(vehiculo);
        vehiculo.getCliente().agregarVehiculo(vehiculo);
    }

    public Vehiculo buscarVehiculoPorPlaca(String placa) {
        for (Vehiculo v : vehiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                return v;
            }
        }
        return null;
    }

    public void modificarVehiculo(Scanner scanner) {
        System.out.print("Ingrese la placa del vehículo a modificar: ");
        String placa = scanner.nextLine();

        Vehiculo vehiculo = buscarVehiculoPorPlaca(placa);

        if (vehiculo != null) {
            System.out.println("Vehículo encontrado: " + vehiculo);

            System.out.print("Nuevo color (actual: " + vehiculo.getColor() + "): ");
            String nuevoColor = scanner.nextLine();

            System.out.print("Nuevo modelo (actual: " + vehiculo.getModelo() + "): ");
            String nuevoModelo = scanner.nextLine();

            vehiculo.setColor(nuevoColor);
            vehiculo.setModelo(nuevoModelo);

            System.out.println("Vehículo modificado correctamente:");
            System.out.println(vehiculo);
            System.out.println("✅ El vehículo ha sido modificado exitosamente.");
        } else {
            System.out.println("❌ Vehículo no encontrado.");
        }
    }

    public void mostrarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            for (Cliente c : clientes) {
                System.out.println(c);
            }
        }
    }

    public void mostrarVehiculos() {
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados.");
        } else {
            for (Vehiculo v : vehiculos) {
                System.out.println(v);
            }
        }
    }

    // Gestión de cupos y registros

    public int cuposDisponibles(String tipo) {
        switch (tipo) {
            case "Moto": return capacidadMotos - ocupadosMotos;
            case "Automovil": return capacidadAutomoviles - ocupadosAutomoviles;
            case "Camion": return capacidadCamiones - ocupadosCamiones;
            default: return 0;
        }
    }

    public void mostrarCupos() {
        System.out.println("=== Cupos disponibles ===");
        System.out.println("Motos: " + cuposDisponibles("Moto"));
        System.out.println("Automóviles: " + cuposDisponibles("Automovil"));
        System.out.println("Camiones: " + cuposDisponibles("Camion"));
    }

    public void registrarIngreso(String placa) {
        Vehiculo vehiculo = buscarVehiculoPorPlaca(placa);
        if (vehiculo != null) {
            String tipo = vehiculo.getClass().getSimpleName();
            int disponibles = cuposDisponibles(tipo);

            if (disponibles <= 0) {
                System.out.println("No hay cupos disponibles para " + tipo.toLowerCase() + "s.");
                return;
            }

            double tarifa = tarifasPorTipoVehiculo.getOrDefault(tipo, 3000.0);
            RegistroParqueo registro = new RegistroParqueo(vehiculo, tarifa);
            registros.add(registro);

            switch (tipo) {
                case "Moto": ocupadosMotos++; break;
                case "Automovil": ocupadosAutomoviles++; break;
                case "Camion": ocupadosCamiones++; break;
            }

            System.out.println("Ingreso registrado correctamente.");
        } else {
            System.out.println("Vehículo no encontrado.");
        }
    }

    public void registrarSalida(String placa) {
        for (RegistroParqueo r : registros) {
            if (r.getVehiculo().getPlaca().equalsIgnoreCase(placa) && r.getHoraSalida() == null) {
                r.registrarSalida();

                String tipo = r.getVehiculo().getClass().getSimpleName();
                String placaSalida = r.getVehiculo().getPlaca();

                // Liberar espacio
                switch (tipo) {
                    case "Moto": ocupadosMotos--; break;
                    case "Automovil": ocupadosAutomoviles--; break;
                    case "Camion": ocupadosCamiones--; break;
                }

                System.out.println("✅ Salió un " + tipo + " con placa " + placaSalida);
                System.out.println("💰 Total a pagar: $" + r.calcularMonto());
                return;
            }
        }
        System.out.println("❌ No se encontró un vehículo en parqueo con esa placa.");
    }

    public void mostrarRegistros() {
        if (registros.isEmpty()) {
            System.out.println("No hay registros de parqueo.");
        } else {
            System.out.println("Historial de parqueo:");
            for (RegistroParqueo r : registros) {
                System.out.println(r);
            }
        }
    }

    // Vehículo temporal

    public void ingresarVehiculoTemporal(String placa, String tipo, String color, String modelo) {
        tipo = tipo.trim().toLowerCase();
        tipo = tipo.substring(0, 1).toUpperCase() + tipo.substring(1);

        int disponibles = cuposDisponibles(tipo);
        if (disponibles <= 0) {
            System.out.println("❌ No hay cupos disponibles para " + tipo.toLowerCase() + "s.");
            return;
        }

        Cliente temporal = new Cliente("Temporal", "0000", "N/A", "N/A");

        Vehiculo vehiculo;
        switch (tipo) {
            case "Moto":
                vehiculo = new Moto(placa, color, modelo, temporal);
                break;
            case "Automovil":
                vehiculo = new Automovil(placa, color, modelo, temporal);
                break;
            case "Camion":
                vehiculo = new Camion(placa, color, modelo, temporal);
                break;
            default:
                System.out.println("Tipo de vehículo inválido.");
                return;
        }

        registrarVehiculo(vehiculo);
        registrarIngreso(placa);
    }

    // *** MÉTODOS DE MEMBRESÍAS ***

    // 1. Registrar membresía para cliente
    public void registrarMembresia(String cedula, Membresia.Tipo tipo) {
        Cliente cliente = buscarClientePorCedula(cedula);
        if (cliente == null) {
            System.out.println("Cliente no encontrado para registrar membresía.");
            return;
        }

        LocalDate inicio = LocalDate.now();
        LocalDate fin;

        switch (tipo) {
            case MENSUAL:
                fin = inicio.plusMonths(1);
                break;
            case TRIMESTRAL:
                fin = inicio.plusMonths(3);
                break;
            case ANUAL:
                fin = inicio.plusYears(1);
                break;
            default:
                fin = inicio;
        }

        Membresia membresia = new Membresia(cliente, tipo, inicio, fin);
        membresias.add(membresia);
        System.out.println("Membresía registrada para " + cliente.getNombre() + " de tipo " + tipo);
    }

    // 2. Verificar si una membresía está activa para un cliente
    public boolean membresiaActiva(String cedula) {
        LocalDate hoy = LocalDate.now();
        for (Membresia m : membresias) {
            if (m.getCliente().getCedula().equalsIgnoreCase(cedula)) {
                if ((m.getFechaInicio().isBefore(hoy) || m.getFechaInicio().isEqual(hoy)) &&
                    (m.getFechaFin().isAfter(hoy) || m.getFechaFin().isEqual(hoy))) {
                    return true;
                }
            }
        }
        return false;
    }

    // 3. Mostrar historial de vehículos y estado de membresía de un cliente
    public void verHistorialYEstadoMembresia(String cedula) {
        Cliente cliente = buscarClientePorCedula(cedula);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        System.out.println("Historial de vehículos de " + cliente.getNombre() + ":");
        for (Vehiculo v : vehiculos) {
            if (v.getCliente().getCedula().equalsIgnoreCase(cedula)) {
                System.out.println(v);
            }
        }
        boolean activa = membresiaActiva(cedula);
        System.out.println("Estado de membresía: " + (activa ? "Activa" : "Inactiva o no existe"));
    }

    // 4. Listar clientes con membresías activas o próximas a vencer en 7 días
    
    public void clientesMembresiasActivasYProximasAVencer() {
        LocalDate hoy = LocalDate.now();
        boolean hayClientes = false;

        for (Membresia m : membresias) {
            if (m.getFechaFin().isAfter(hoy.minusDays(1))) {
                long diasRestantes = ChronoUnit.DAYS.between(hoy, m.getFechaFin());

                if (diasRestantes >= 0 && diasRestantes <= 7) {
                    System.out.println("Cliente: " + m.getCliente().getNombre() +
                            " - Membresía próxima a vencer en " + diasRestantes + " día(s).");
                    hayClientes = true;
                } else if (diasRestantes > 7) {
                    System.out.println("Cliente: " + m.getCliente().getNombre() + " - Membresía activa.");
                    hayClientes = true;
                }
            }
        }

        if (!hayClientes) {
            System.out.println("No hay clientes con membresías activas o próximas a vencer.");
        }
    }
}

    
   