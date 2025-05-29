package controller;

import model.*;
import service.ParqueaderoService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ParqueaderoService service = new ParqueaderoService();

    public static void main(String[] args) {
        int opcion;

        do {
            mostrarMenu();
            System.out.print("Ingrese opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> registrarCliente();
                case 2 -> registrarVehiculo();
                case 3 -> mostrarClientes();
                case 4 -> mostrarVehiculos();
                case 5 -> registrarIngreso();
                case 6 -> registrarSalida();
                case 7 -> mostrarHistorial();
                case 8 -> mostrarCupos();
                case 9 -> ingresoVehiculoTemporal();
                case 10 -> buscarClientePorCedula();
                case 11 -> modificarCliente();
                
                case 13 -> buscarVehiculoPorPlaca();
                case 14 -> registrarMembresia();
                case 15 -> validarMembresiaActiva();
                case 16 -> verHistorialVehiculosEstadoMembresia();
                case 17 -> verClientesConMembresiasActivasOPorVencer();
                case 18 -> registrosDePagosTemporales();
                case 19 -> registroDePagoPorMembresia();
                case 20 -> calcularIngresosPorTipoYPeriodo();
                case 21 -> mostrarHistorialVehiculosPorCliente();
                case 22 -> reportesIngresoTotalesYPorPeriodo();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }

            System.out.println();

        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("=== SISTEMA DE PARQUEADERO ===");
        System.out.println("1. Registrar cliente");
        System.out.println("2. Registrar vehículo");
        System.out.println("3. Mostrar clientes");
        System.out.println("4. Mostrar vehículos");
        System.out.println("5. Registrar ingreso");
        System.out.println("6. Registrar salida");
        System.out.println("7. Mostrar historial");
        System.out.println("8. Mostrar cupos");
        System.out.println("9. Ingreso vehículo temporal");
        System.out.println("10. Buscar cliente por cédula");
        System.out.println("11. Modificar cliente");
        System.out.println("12. Modificar vehículo");
        System.out.println("13. Buscar vehículo por placa");
        System.out.println("14. Registrar membresía");
        System.out.println("15. Validar membresía activa");
        System.out.println("16. Ver historial vehículos y estado membresía");
        System.out.println("17. Ver clientes con membresías activas o por vencer");
        System.out.println("18. Registros de pagos temporales");
        System.out.println("19. Registro de pago por membresía");
        System.out.println("20. Calcular ingresos por tipo y período");
        System.out.println("21. Mostrar historial vehículos por cliente");
        System.out.println("22. Reportes de ingresos totales y por período");
        System.out.println("0. Salir");
    }

    private static void registrarCliente() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Cédula: ");
        String cedula = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Correo: ");
        String correo = scanner.nextLine();

        Cliente cliente = new Cliente(nombre, cedula, telefono, correo);
        service.registrarCliente(cliente);
        System.out.println("Cliente registrado.");
    }

    private static void registrarVehiculo() {
        System.out.print("Placa: ");
        String placa = scanner.nextLine();
        System.out.print("Tipo (automovil/moto/camion): ");
        String tipo = scanner.nextLine();
        System.out.print("Color: ");
        String color = scanner.nextLine();
        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();
        System.out.print("Cédula propietario: ");
        String cedula = scanner.nextLine();

        Cliente propietario = service.buscarClientePorCedula(cedula);
        if (propietario == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        Vehiculo vehiculo;
        switch (tipo.toLowerCase()) {
            case "moto" -> vehiculo = new Moto(placa, color, modelo, propietario);
            case "camion" -> vehiculo = new Camion(placa, color, modelo, propietario);
            default -> vehiculo = new Automovil(placa, color, modelo, propietario);
        }

        service.registrarVehiculo(vehiculo);
        System.out.println("Vehículo registrado.");
    }

    private static void mostrarClientes() {
        List<Cliente> clientes = service.getClientes();
        if (clientes.isEmpty()) System.out.println("No hay clientes registrados.");
        else clientes.forEach(System.out::println);
    }

    private static void mostrarVehiculos() {
        List<Vehiculo> vehiculos = service.getVehiculos();
        if (vehiculos.isEmpty()) System.out.println("No hay vehículos registrados.");
        else vehiculos.forEach(System.out::println);
    }

    private static void registrarIngreso() {
        System.out.print("Placa vehículo: ");
        String placa = scanner.nextLine();
        service.registrarIngreso(placa);
    }

    private static void registrarSalida() {
        System.out.print("Placa vehículo: ");
        String placa = scanner.nextLine();
        service.registrarSalida(placa);
    }

    private static void mostrarHistorial() {
        List<RegistroParqueo> registros = service.getRegistrosParqueo();
        if (registros.isEmpty()) System.out.println("No hay registros.");
        else registros.forEach(System.out::println);
    }

    private static void mostrarCupos() {
        int cupos = service.getCuposDisponibles();
        System.out.println("Cupos disponibles: " + cupos);
    }

    private static void ingresoVehiculoTemporal() {
        System.out.print("Placa: ");
        String placa = scanner.nextLine();
        System.out.print("Tipo (automovil/moto/camion): ");
        String tipo = scanner.nextLine();
        System.out.print("Color: ");
        String color = scanner.nextLine();
        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();

        service.ingresarVehiculoTemporal(placa, tipo, color, modelo);
        System.out.println("Vehículo temporal ingresado.");
    }

    private static void buscarClientePorCedula() {
        System.out.print("Cédula: ");
        String cedula = scanner.nextLine();
        Cliente cliente = service.buscarClientePorCedula(cedula);
        if (cliente == null) System.out.println("Cliente no encontrado.");
        else System.out.println(cliente);
    }

    private static void modificarCliente() {
        System.out.print("Cédula cliente a modificar: ");
        String cedula = scanner.nextLine();
        Cliente cliente = service.buscarClientePorCedula(cedula);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        System.out.print("Nuevo nombre: ");
        cliente.setNombre(scanner.nextLine());
        System.out.print("Nuevo teléfono: ");
        cliente.setTelefono(scanner.nextLine());
        System.out.print("Nuevo correo: ");
        cliente.setCorreo(scanner.nextLine());
        System.out.println("Cliente modificado.");
    }

    private static void modificarVehiculo() {
        System.out.print("Placa vehículo a modificar: ");
        String placa = scanner.nextLine();
        Vehiculo vehiculo = service.buscarVehiculoPorPlaca(placa);
        if (vehiculo == null) {
            System.out.println("Vehículo no encontrado.");
            return;
        }
        System.out.print("Nuevo color: ");
        vehiculo.setColor(scanner.nextLine());
        System.out.print("Nuevo modelo: ");
        vehiculo.setModelo(scanner.nextLine());
        System.out.println("Vehículo modificado.");
    }

    private static void buscarVehiculoPorPlaca() {
        System.out.print("Placa: ");
        String placa = scanner.nextLine();
        Vehiculo vehiculo = service.buscarVehiculoPorPlaca(placa);
        if (vehiculo == null) System.out.println("Vehículo no encontrado.");
        else System.out.println(vehiculo);
    }

    private static void registrarMembresia() {
        System.out.print("Cédula cliente: ");
        String cedula = scanner.nextLine();
        System.out.print("Tipo membresía (MENSUAL, TRIMESTRAL, ANUAL): ");
        String tipoStr = scanner.nextLine();

        Membresia.Tipo tipo;
        try {
            tipo = Membresia.Tipo.valueOf(tipoStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Tipo inválido.");
            return;
        }

        service.registrarMembresia(cedula, tipo);
    }

    private static void validarMembresiaActiva() {
        System.out.print("Cédula cliente: ");
        String cedula = scanner.nextLine();
        boolean activa = service.membresiaActiva(cedula);
        System.out.println("Membresía activa: " + (activa ? "Sí" : "No"));
    }

    private static void verHistorialVehiculosEstadoMembresia() {
        List<RegistroParqueo> registros = service.getRegistrosParqueo();
        if (registros.isEmpty()) System.out.println("No hay registros.");
        else {
            for (RegistroParqueo r : registros) {
                Cliente c = r.getVehiculo().getPropietario();
                String estado = "Sin membresía";
                if (c != null && c.getMembresia() != null) {
                    estado = c.getMembresia().estaActiva() ? "Activa" : "Inactiva";
                }
                System.out.println(r + " - Estado membresía cliente: " + estado);
            }
        }
    }

    private static void verClientesConMembresiasActivasOPorVencer() {
        System.out.print("Días para membresías por vencer: ");
        int dias = Integer.parseInt(scanner.nextLine());
        List<Cliente> clientes = service.clientesConMembresiasPorVencer(dias);
        if (clientes.isEmpty()) System.out.println("No hay clientes con membresías próximas a vencer.");
        else clientes.forEach(System.out::println);
    }

    private static void registrosDePagosTemporales() {
        List<Pago> pagos = service.getPagosPorTipo(Pago.Tipo.TEMPORAL);
        if (pagos.isEmpty()) System.out.println("No hay pagos temporales registrados.");
        else pagos.forEach(System.out::println);
    }

    private static void registroDePagoPorMembresia() {
        System.out.print("Cédula cliente: ");
        String cedula = scanner.nextLine();
        System.out.print("Monto pago: ");
        double monto = Double.parseDouble(scanner.nextLine());
        service.registrarPagoMembresia(cedula, monto);
        System.out.println("Pago registrado.");
    }

    private static void calcularIngresosPorTipoYPeriodo() {
        System.out.print("Fecha inicio (yyyy-MM-dd): ");
        LocalDate inicio = LocalDate.parse(scanner.nextLine());
        System.out.print("Fecha fin (yyyy-MM-dd): ");
        LocalDate fin = LocalDate.parse(scanner.nextLine());

        double ingresosTemporales = service.calcularIngresosPorTipoYPeriodo(Pago.Tipo.TEMPORAL, inicio, fin);
        double ingresosMembresia = service.calcularIngresosPorTipoYPeriodo(Pago.Tipo.MEMBRESIA, inicio, fin);

        System.out.println("Ingresos por pagos temporales: $" + ingresosTemporales);
        System.out.println("Ingresos por membresías: $" + ingresosMembresia);
        System.out.println("Total ingresos: $" + (ingresosTemporales + ingresosMembresia));
    }

    private static void mostrarHistorialVehiculosPorCliente() {
        System.out.print("Cédula cliente: ");
        String cedula = scanner.nextLine();
        List<RegistroParqueo> historial = service.getHistorialVehiculosPorCliente(cedula);
        if (historial.isEmpty()) System.out.println("No hay historial para ese cliente.");
        else historial.forEach(System.out::println);
    }

    private static void reportesIngresoTotalesYPorPeriodo() {
        System.out.println("Reporte ingresos totales:");
        double total = service.calcularIngresosTotales();
        System.out.println("Total ingresos: $" + total);
    }
}


