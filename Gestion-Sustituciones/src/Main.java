import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GestorSustituciones gestor = new GestorSustituciones();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Buscar sustituto");
            System.out.println("2. Consultar sustituciones de un profesor");
            System.out.println("3. Ranking de sustituciones");
            System.out.println("4. Salir");
            System.out.print("Elige una opción: ");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Profesor ausente: ");
                    String profesor = sc.nextLine();
                    System.out.print("Día (lunes, martes...): ");
                    String dia = sc.nextLine();
                    System.out.print("Hora (1-6): ");
                    int hora = sc.nextInt();
                    sc.nextLine();
                    gestor.buscarSustituto(profesor, dia, hora);
                    break;
                case 2:
                    System.out.print("Profesor: ");
                    String p = sc.nextLine();
                    gestor.consultarSustituciones(p);
                    break;
                case 3:
                    gestor.mostrarRanking();
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }
}


