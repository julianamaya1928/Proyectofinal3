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
}

    