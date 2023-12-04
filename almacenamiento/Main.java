package almacenamiento;

import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ManejadorRoles manejadorRoles = new ManejadorRoles();
        boolean estaConectado = false;

        while (true) {
            System.out.println("\n=== Menú ===");
            System.out.println("1. Establecer conexión a SERVIDOR");
            System.out.println("2. Agregar Nodo");
            System.out.println("3. Realizar Lectura");
            System.out.println("4. Realizar Escritura");
            System.out.println("5. Salir");

            try {
                System.out.print("Ingrese la opción: ");
                int opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        estaConectado = establecerConexion();
                        break;
                    case 2:
                        if (estaConectado) {
                            agregarNodo(manejadorRoles, scanner);
                        } else {
                            System.out.println("No estás conectado a TCPServer. Establece la conexión primero.");
                        }
                        break;
                    case 3:
                        if (estaConectado) {
                            realizarOperacion(manejadorRoles, TipoOperacion.LECTURA, scanner);
                        } else {
                            System.out.println("No estás conectado a TCPServer. Establece la conexión primero.");
                        }
                        break;
                    case 4:
                        if (estaConectado) {
                            realizarOperacionEscritura(manejadorRoles, scanner);
                        } else {
                            System.out.println("No estás conectado a TCPServer. Establece la conexión primero.");
                        }
                        break;
                    case 5:
                        System.out.println("Saliendo del programa.");
                        System.exit(0);
                    default:
                        System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número entero.");
                scanner.nextLine(); // Limpiar el buffer del scanner
            }
        }
    }

    private static boolean establecerConexion() {
        try (Socket client = new Socket(InetAddress.getByName("localhost"), 9999)) {
            System.out.println("Conexión establecida a SERVIDOR.");
            return true;
        } catch (Throwable t) {
            System.out.println("ERROR. El SERVIDOR esta apagado encender por favor");
            return false;
        }
    }

    private static void agregarNodo(ManejadorRoles manejadorRoles, Scanner scanner) {
        System.out.print("Ingrese el nombre del nodo: ");
        String nombre = scanner.next();

        System.out.println("Seleccione el rol del nodo:");
        System.out.println("1. Líder");
        System.out.println("2. Seguidor");
        int opcionRol = scanner.nextInt();

        Rol rol = (opcionRol == 1) ? Rol.LIDER : Rol.SEGUIDOR;

        Nodo nodo = new Nodo(nombre, rol);
        manejadorRoles.agregarNodo(nodo);
    }

    private static void realizarOperacion(ManejadorRoles manejadorRoles, TipoOperacion tipoOperacion, Scanner scanner) {
        System.out.print("Ingrese el nombre del nodo que realizará la operación: ");
        String nombre = scanner.next();

        Nodo nodo = manejadorRoles.obtenerNodoPorNombre(nombre);

        if (nodo != null) {
            if (tipoOperacion == TipoOperacion.LECTURA) {
                manejadorRoles.realizarLectura(nodo);
            }
        } else {
            System.out.println("Nodo no encontrado. Asegúrese de que el nodo exista.");
        }
    }

    private static void realizarOperacionEscritura(ManejadorRoles manejadorRoles, Scanner scanner) {
        System.out.print("Ingrese el nombre del nodo que realizará la operación de escritura: ");
        String nombre = scanner.next();

        Nodo nodo = manejadorRoles.obtenerNodoPorNombre(nombre);

        if (nodo != null) {
            if (nodo.getRol() == Rol.LIDER) {
                System.out.print("Ingrese el contenido a escribir: ");
                String contenido = scanner.next();
                manejadorRoles.realizarEscritura(nodo, contenido);
            } else {
                System.out.println(nombre + " no puede realizar una escritura, ya que no es el líder.");
            }
        } else {
            System.out.println("Nodo no encontrado. Asegúrese de que el nodo exista.");
        }
    }
}
