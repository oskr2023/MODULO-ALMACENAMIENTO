package almacenamiento;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.net.*;

enum Rol {
    LIDER, SEGUIDOR
}

enum TipoOperacion {
    LECTURA, ESCRITURA
}

interface Observador {
    void recibirMensaje(String mensaje);
}

class Nodo implements Observador {
    private String nombre;
    private Rol rol;
    private List<Observador> observadores = new ArrayList<>();
    private List<String> contenidoEscrito = new ArrayList<>();
    private ServerSocket serverSocket;

    public Nodo(String nombre, Rol rol) {
        this.nombre = nombre;
        this.rol = rol;

        if (rol == Rol.SEGUIDOR) {
            // Si es seguidor, inicia el servidor para aceptar conexiones de otros nodos
            iniciarServidor();
        }
    }

    private void iniciarServidor() {
        try {
            serverSocket = new ServerSocket(0);
            System.out.println(nombre + " está escuchando en el puerto " + serverSocket.getLocalPort());

            // Hilo para aceptar conexiones entrantes
            new Thread(() -> {
                while (true) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        new Thread(() -> manejarConexionEntrante(clientSocket)).start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void manejarConexionEntrante(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String mensaje = reader.readLine();
            recibirMensaje(mensaje);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void enviarMensaje(String mensaje, int puertoDestino) {
        try (Socket socket = new Socket("localhost", puertoDestino);
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            writer.println(mensaje);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unirseComoObservador(Observador observador) {
        observadores.add(observador);
    }

    @Override
    public void recibirMensaje(String mensaje) {
        System.out.println(nombre + " ha recibido el mensaje: " + mensaje);
    }

    public String getNombre() {
        return nombre;
    }

    public Rol getRol() {
        return rol;
    }

    public void escribirContenido(String contenido, int puertoDestino) {
        if (rol == Rol.LIDER) {
            contenidoEscrito.add(contenido);
            System.out.println(nombre + " ha escrito: " + contenido);

            for (Observador seguidor : observadores) {
                if (seguidor instanceof Nodo) {
                    ((Nodo) seguidor).enviarMensaje(contenido, serverSocket.getLocalPort());
                }
            }
        } else {
            System.out.println(nombre + " no puede escribir, ya que no es el líder.");
        }
    }

    public List<String> obtenerContenidoEscrito() {
        return contenidoEscrito;
    }
}

class ManejadorRoles {
    private List<Nodo> nodos = new ArrayList<>();

    public void agregarNodo(Nodo nodo) {
        nodos.add(nodo);
        if (nodo.getRol() == Rol.LIDER) {
            asignarLider(nodo);
        }
        System.out.println(nodo.getNombre() + " se ha unido al sistema.");
    }

    public void asignarLider(Nodo nodo) {
        System.out.println(nodo.getNombre() + " ha sido asignado como líder.");
    }

    public void realizarLectura(Nodo nodoLector) {
        System.out.println(nodoLector.getNombre() + " realiza una lectura.");
        for (Nodo nodo : nodos) {
            mostrarContenidoEscrito(nodo);
        }
    }

    public void realizarEscritura(Nodo nodo, String contenido) {
        if (nodo.getRol() == Rol.LIDER) {
            nodo.escribirContenido(contenido, 0);
            enviarMensajeAEscritores("Se ha realizado una escritura.");
        } else {
            System.out.println(nodo.getNombre() + " no puede realizar una escritura, ya que no es el líder.");
        }
    }

    private void enviarMensajeAEscritores(String mensaje) {
        for (Nodo seguidor : nodos) {
            if (seguidor.getRol() == Rol.SEGUIDOR) {
                seguidor.recibirMensaje(mensaje);
            }
        }
    }

    public Nodo obtenerNodoPorNombre(String nombre) {
        for (Nodo nodo : nodos) {
            if (nodo.getNombre().equals(nombre)) {
                return nodo;
            }
        }
        return null;
    }

    private void mostrarContenidoEscrito(Nodo lider) {
        List<String> contenidoEscrito = lider.obtenerContenidoEscrito();

        for (String contenido : contenidoEscrito) {
            if (contenido != null && !contenido.trim().isEmpty()) {
                System.out.println("Contenido escrito por " + lider.getNombre() + ":");
                System.out.println(contenido);
            }
        }
    }

}