import java.util.ArrayList;
import java.util.Iterator;
import java.util.InputMismatchException;
import java.util.Scanner;

class Articulo {
    int id;
    String nombre;
    double precio;

    public Articulo(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public void mostrar() {
        System.out.println("ID: " + id + " | Nombre: " + nombre + " | Precio: $" + precio);
    }
}

public class MenuArticulos {

    static ArrayList<Articulo> lista = new ArrayList<>();
    static Scanner teclado = new Scanner(System.in);

    // Mensajes de estado
    static final String ARTICULO_NO_ENCONTRADO = "❌ Artículo no encontrado.";
    static final String ARTICULO_EXISTE = "❌ Ya existe un artículo con ese ID.";
    static final String PRECIO_NEGATIVO = "❌ El precio no puede ser negativo.";
    static final String ARTICULO_AGREGADO = "✅ Artículo agregado correctamente.";
    static final String ARTICULO_MODIFICADO = "✅ Artículo modificado correctamente.";
    static final String ARTICULO_ELIMINADO = "🗑️ Artículo eliminado.";
    static final String NO_HAY_ARTICULOS = "📭 No hay artículos registrados.";
    static final String INGRESAR_NUMERO = "Por favor ingrese un número válido.";

    public static void main(String[] args) {
        int opcion = 0;

        while (opcion != 6) {
            System.out.println("\n----- Menú de Artículos -----");
            System.out.println("1. Crear un artículo nuevo");
            System.out.println("2. Consultar un artículo");
            System.out.println("3. Listar artículos");
            System.out.println("4. Modificar un artículo");
            System.out.println("5. Borrar un artículo");
            System.out.println("6. Salir");
            System.out.print("Ingrese una opción: ");

            // Validar entrada de opción
            opcion = obtenerNumeroValido();

            switch (opcion) {
                case 1:
                    crearArticulo();
                    break;
                case 2:
                    consultarArticulo();
                    break;
                case 3:
                    listarArticulos();
                    break;
                case 4:
                    modificarArticulo();
                    break;
                case 5:
                    borrarArticulo();
                    break;
                case 6:
                    System.out.println("Programa finalizado.");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }

        teclado.close();
    }

    public static void crearArticulo() {
        System.out.print("ID: ");
        int id = teclado.nextInt();
        teclado.nextLine();

        // Validar que no haya artículo con el mismo ID
        for (Articulo art : lista) {
            if (art.id == id) {
                System.out.println(ARTICULO_EXISTE);
                return;
            }
        }

        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();
        // Validar que el nombre no esté vacío
        if (nombre.isEmpty()) {
            System.out.println("❌ El nombre no puede estar vacío.");
            return;
        }

        System.out.print("Precio: ");
        double precio = obtenerPrecioValido();

        Articulo nuevo = new Articulo(id, nombre, precio);
        lista.add(nuevo);
        System.out.println(ARTICULO_AGREGADO);
    }

    public static void consultarArticulo() {
        System.out.print("Ingrese el ID del artículo: ");
        int id = teclado.nextInt();

        for (Articulo art : lista) {
            if (art.id == id) {
                art.mostrar();
                return;
            }
        }
        System.out.println(ARTICULO_NO_ENCONTRADO);
    }

    public static void listarArticulos() {
        if (lista.isEmpty()) {
            System.out.println(NO_HAY_ARTICULOS);
        } else {
            System.out.println("📦 Lista de artículos:");
            for (Articulo art : lista) {
                art.mostrar();
            }
        }
    }

    public static void modificarArticulo() {
        System.out.print("Ingrese el ID del artículo a modificar: ");
        int id = teclado.nextInt();
        teclado.nextLine();

        for (Articulo art : lista) {
            if (art.id == id) {
                // Validar nombre
                String nuevoNombre;
                do {
                    System.out.print("Nuevo nombre: ");
                    nuevoNombre = teclado.nextLine();
                } while (nuevoNombre.isEmpty());

                // Validar precio
                double nuevoPrecio = obtenerPrecioValido();

                art.nombre = nuevoNombre;
                art.precio = nuevoPrecio;
                System.out.println(ARTICULO_MODIFICADO);
                return;
            }
        }
        System.out.println(ARTICULO_NO_ENCONTRADO);
    }

    public static void borrarArticulo() {
        System.out.print("Ingrese el ID del artículo a borrar: ");
        int id = teclado.nextInt();

        // Usar iterador para eliminar de forma segura
        Iterator<Articulo> iterator = lista.iterator();
        while (iterator.hasNext()) {
            Articulo art = iterator.next();
            if (art.id == id) {
                iterator.remove();
                System.out.println(ARTICULO_ELIMINADO);
                return;
            }
        }
        System.out.println(ARTICULO_NO_ENCONTRADO);
    }

    // Método para obtener un número válido
    public static int obtenerNumeroValido() {
        int numero = 0;
        boolean valido = false;

        while (!valido) {
            try {
                numero = teclado.nextInt();
                valido = true;
            } catch (InputMismatchException e) {
                System.out.println(INGRESAR_NUMERO);
                teclado.nextLine();  // Limpiar el buffer
            }
        }
        return numero;
    }

    // Método para obtener un precio válido (no negativo)
    public static double obtenerPrecioValido() {
        double precio = -1;
        while (precio < 0) {
            try {
                precio = teclado.nextDouble();
                if (precio < 0) {
                    System.out.println(PRECIO_NEGATIVO);
                }
            } catch (InputMismatchException e) {
                System.out.println(INGRESAR_NUMERO);
                teclado.nextLine();  // Limpiar el buffer
            }
        }
        return precio;
    }
}
