package controller;

import java.util.Scanner;

import model.*;
import service.ParqueaderoService;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ParqueaderoService parqueadero = new ParqueaderoService();

        int opcion;
        do {
            System.out.println("\n=== SISTEMA DE PARQUEADERO ===");
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Registrar Vehículo");
            System.out.println("3. Ver Clientes");
            System.out.println("4. Ver Vehículos");
            System.out.println("5. Registrar Ingreso");
            System.out.println("6. Registrar Salida");
            System.out.println("7. Mostrar Historial");
            System.out.println("8. Mostrar Cupos");
            System.out.println("9. Ingreso Vehículo Temporal");
            System.out.println("10. Buscar cliente por cédula");
            System.out.println("11. Modificar cliente por cédula");
            System.out.println("12. Buscar vehículo por placa");
            System.out.println("13. Modificar vehículo por placa");
            System.out.println("14. Registrar membresía");
            System.out.println("15. Validar membresía activa");
            System.out.println("16. Ver historial vehículos y estado membresía");
            System.out.println("17. Ver clientes con membresías activas o próximas a vencer");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir salto de línea pendiente

            switch (opcion) {
                case 1:
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Cédula: ");
                    String cedula = scanner.nextLine();
                    System.out.print("Teléfono: ");
                    String tel = scanner.nextLine();
                    System.out.print("Correo: ");
                    String correo = scanner.nextLine();
                    parqueadero.registrarCliente(new Cliente(nombre, cedula, tel, correo));
                    break;

                case 2:
                    System.out.print("Cédula del cliente: ");
                    Cliente cliente = parqueadero.buscarClientePorCedula(scanner.nextLine());
                    if (cliente == null) {
                        System.out.println("Cliente no encontrado.");
                        break;
                    }
                    System.out.print("Placa: ");
                    String placa = scanner.nextLine();
                    System.out.print("Color: ");
                    String color = scanner.nextLine();
                    System.out.print("Modelo: ");
                    String modelo = scanner.nextLine();
                    System.out.print("Tipo (1. Automóvil, 2. Moto, 3. Camión): ");
                    int tipo = scanner.nextInt();
                    scanner.nextLine();
                    Vehiculo v;
                    if (tipo == 1) v = new Automovil(placa, color, modelo, cliente);
                    else if (tipo == 2) v = new Moto(placa, color, modelo, cliente);
                    else v = new Camion(placa, color, modelo, cliente);
                    parqueadero.registrarVehiculo(v);
                    break;

                case 3:
                    parqueadero.mostrarClientes();
                    break;

                case 4:
                    parqueadero.mostrarVehiculos();
                    break;

                case 5:
                    System.out.print("Placa: ");
                    parqueadero.registrarIngreso(scanner.nextLine());
                    break;

                case 6:
                    System.out.print("Placa: ");
                    parqueadero.registrarSalida(scanner.nextLine());
                    break;

                case 7:
                    parqueadero.mostrarRegistros();
                    break;

                case 8:
                    parqueadero.mostrarCupos();
                    break;

                case 9:
                    System.out.print("Placa: ");
                    String p = scanner.nextLine();
                    System.out.print("Color: ");
                    String c = scanner.nextLine();
                    System.out.print("Modelo: ");
                    String m = scanner.nextLine();
                    System.out.print("Tipo (Moto, Automovil, Camion): ");
                    String t = scanner.nextLine();
                    parqueadero.ingresarVehiculoTemporal(p, t, c, m);
                    break;

                case 10:
                    System.out.print("Ingrese la cédula del cliente: ");
                    String cedulaBuscar = scanner.nextLine();
                    Cliente clienteEncontrado = parqueadero.buscarClientePorCedula(cedulaBuscar);
                    if (clienteEncontrado != null) {
                        System.out.println("Cliente encontrado:");
                        System.out.println(clienteEncontrado);
                    } else {
                        System.out.println("Cliente no encontrado.");
                    }
                    break;

                case 11:
                    parqueadero.modificarCliente(scanner);
                    break;

                case 12:
                    System.out.print("Ingrese la placa del vehículo: ");
                    String placaBuscar = scanner.nextLine();
                    Vehiculo vehiculoEncontrado = parqueadero.buscarVehiculoPorPlaca(placaBuscar);
                    if (vehiculoEncontrado != null) {
                        System.out.println("Vehículo encontrado:");
                        System.out.println(vehiculoEncontrado);
                    } else {
                        System.out.println("Vehículo no encontrado.");
                    }
                    break;

                case 13:
                    parqueadero.modificarVehiculo(scanner);
                    break;

                case 14:
                    System.out.print("Cédula del cliente: ");
                    String cedulaM = scanner.nextLine();
                    System.out.print("Tipo membresía (1. Mensual, 2. Trimestral, 3. Anual): ");
                    int tipoM = scanner.nextInt();
                    scanner.nextLine();

                    Membresia.Tipo tipoMembresia = null;
                    switch (tipoM) {
                        case 1: tipoMembresia = Membresia.Tipo.MENSUAL; break;
                        case 2: tipoMembresia = Membresia.Tipo.TRIMESTRAL; break;
                        case 3: tipoMembresia = Membresia.Tipo.ANUAL; break;
                        default:
                            System.out.println("Tipo inválido.");
                    }
                    if (tipoMembresia != null) {
                        parqueadero.registrarMembresia(cedulaM, tipoMembresia);
                    }
                    break;

                case 15:
                    System.out.print("Cédula del cliente: ");
                    String cedulaVal = scanner.nextLine();
                    boolean activa = parqueadero.membresiaActiva(cedulaVal);
                    System.out.println("La membresía está " + (activa ? "activa." : "inactiva o no existe."));
                    break;

                case 16:
                    System.out.print("Cédula del cliente: ");
                    String cedulaHist = scanner.nextLine();
                    parqueadero.verHistorialYEstadoMembresia(cedulaHist);
                    break;

                case 17:
                    parqueadero.clientesMembresiasActivasYProximasAVencer();
                    break;

                case 0:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
        scanner.close();
    }
}
