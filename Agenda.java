import java.io.*;
import java.util.*;

public class Agenda {
    private static final String FILE_NAME = "agenda.dat";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, String> agenda = loadAgenda();

        int option;
        do {
            System.out.println("\n--- Agenda Telefónica ---");
            System.out.println("1. Añadir contacto");
            System.out.println("2. Buscar contacto");
            System.out.println("3. Mostrar todos los contactos");
            System.out.println("4. Eliminar contacto");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            option = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (option) {
                case 1:
                    addContact(scanner, agenda);
                    break;
                case 2:
                    searchContact(scanner, agenda);
                    break;
                case 3:
                    showAllContacts(agenda);
                    break;
                case 4:
                    deleteContact(scanner, agenda);
                    break;
                case 5:
                    saveAgenda(agenda);
                    System.out.println("Agenda guardada. ¡Adiós!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (option != 5);

        scanner.close();
    }

    private static Map<String, String> loadAgenda() {
        Map<String, String> agenda = new HashMap<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            agenda = (Map<String, String>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró un archivo existente. Se creará uno nuevo.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar la agenda: " + e.getMessage());
        }
        return agenda;
    }

    private static void saveAgenda(Map<String, String> agenda) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(agenda);
        } catch (IOException e) {
            System.out.println("Error al guardar la agenda: " + e.getMessage());
        }
    }

    private static void addContact(Scanner scanner, Map<String, String> agenda) {
        System.out.print("Ingrese el nombre del contacto: ");
        String name = scanner.nextLine();
        System.out.print("Ingrese el número de teléfono: ");
        String phone = scanner.nextLine();
        agenda.put(name, phone);
        System.out.println("Contacto añadido con éxito.");
    }

    private static void searchContact(Scanner scanner, Map<String, String> agenda) {
        System.out.print("Ingrese el nombre del contacto a buscar: ");
        String name = scanner.nextLine();
        if (agenda.containsKey(name)) {
            System.out.println("Teléfono de " + name + ": " + agenda.get(name));
        } else {
            System.out.println("Contacto no encontrado.");
        }
    }

    private static void showAllContacts(Map<String, String> agenda) {
        if (agenda.isEmpty()) {
            System.out.println("La agenda está vacía.");
        } else {
            System.out.println("\n--- Lista de Contactos ---");
            for (Map.Entry<String, String> entry : agenda.entrySet()) {
                System.out.println("Nombre: " + entry.getKey() + ", Teléfono: " + entry.getValue());
            }
        }
    }

    private static void deleteContact(Scanner scanner, Map<String, String> agenda) {
        System.out.print("Ingrese el nombre del contacto a eliminar: ");
        String name = scanner.nextLine();
        if (agenda.remove(name) != null) {
            System.out.println("Contacto eliminado con éxito.");
        } else {
            System.out.println("Contacto no encontrado.");
        }
    }
}
