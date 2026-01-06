package view;

import entity.Catalogo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ConsolaView {
    private Scanner sc = new Scanner(System.in);

    public int menu() {
        System.out.println("1. Alta infante\n" +
                "2. Alta regalo\n" +
                "3. Modificación de regalo.\n" +
                "4. Alta asistente\n" +
                "5. Baja asistente BÁSICO (queda revocada la carta)\n" +
                "6. Listado de regalos del catálogo que no ha pedido ningún infante.\n" +
                "7. Listado de regalos con destino a una ciudad concreta dada por el usuario.\n" +
                "8. Listado de infantes que tendrán los regalos en un mismo dia y hora dados por el usuario\n" +
                "9. Busca de regalos por coincidencia en la descripción de 3 letras seguidas dadas el usuario.\n" +
                "10. OPCIONAL: Baja asistente AVANZADO.\n" +
                "11. OPCIONAL: Listado de regals pedidos completo (nombre del infante, descripción de todos los\n" +
                "regalos que ha pedido, fecha y hora de entrega y el nombre del asistente que ha preparado el\n" +
                "regalo. Si no tiene asistente, indicar “En espera de asignación de asistente”\n" +
                "12. Salir\n");
        return leerEntero();
    }

    public int pedirInt(String msg){
        System.out.println(msg+ ": ");
        return leerEntero();
    }

    public String pedirString(String msg) {
        System.out.println(msg+": ");
        return sc.nextLine().trim();
    }

    public LocalDateTime pedirLocalDate(String msg) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        while (true) {
            System.out.print(msg + " (formato dd/MM/yyyy HH:mm:ss): ");
            String input = sc.nextLine();
            try {
                return LocalDateTime.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Fecha inválida. Intenta de nuevo (ej: 25/12/2025 23:00:00).");
            }
        }
    }

    public void info(String mensaje) {
        System.out.println(mensaje);
    }

    public void error(String mensaje) {
        System.out.println("Error: " + mensaje);
    }

    private int leerEntero() {
        while (true) {
            String s = sc.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número entero válido");
            }
        }
    }

    public void mostrarCatalogo(List<Catalogo> regalos) {
        System.out.println("Catalogo");
        if (regalos.isEmpty()) {
            System.out.println(("sin registros"));
        } else {
            regalos.forEach(System.out::println);
        }
    }
}


