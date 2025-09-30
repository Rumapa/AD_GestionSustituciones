import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GestorSustituciones {
    private String[] profesores = {"Salva", "Luis", "Lola", "Guillem", "Juan"};
    private String[] dias = {"lunes", "martes", "miercoles", "jueves", "viernes"};


    private String[][] horarios = new String[5][30];
    private int[] sustituciones = new int[5];

    public GestorSustituciones() {
        cargarHorariosDesdeCSV();
    }

    private void cargarHorariosDesdeCSV() {
        for (int i = 0; i < profesores.length; i++) {
            String nombreArchivo = "data/horarios/" + profesores[i] + ".csv";
            try {
                Scanner sc = new Scanner(new File(nombreArchivo));
                if(sc.hasNextLine()) sc.nextLine();
                int index = 0;
                while (sc.hasNextLine()) {
                    String linea = sc.nextLine().trim();
                    if(linea.isEmpty()) continue;

                    String[] partes = linea.split(";");
                    if(partes.length < 4) {
                        System.out.println("Fila inválida en " + nombreArchivo + ": " + linea);
                        continue;
                    }
                    String grupo = partes[3];
                    horarios[i][index] = grupo;
                    index++;
                }
                sc.close();
            } catch (FileNotFoundException e) {
                System.out.println("No se encontró el archivo: " + nombreArchivo);
            }
        }
    }

    private int getIndiceProfesor(String nombre) {
        for (int i = 0; i < profesores.length; i++) {
            if (profesores[i].equalsIgnoreCase(nombre)) return i;
        }
        return -1;
    }

    private int getIndiceDia(String dia) {
        for (int i = 0; i < dias.length; i++) {
            if (dias[i].equalsIgnoreCase(dia)) return i;
        }
        return -1;
    }

    public void buscarSustituto(String ausente, String dia, int hora) {
        int idxProfAusente = getIndiceProfesor(ausente);
        int idxDia = getIndiceDia(dia);
        if (idxProfAusente == -1 || idxDia == -1 || hora < 1 || hora > 6) {
            System.out.println("Datos incorrectos");
            return;
        }

        int slot = idxDia * 6 + (hora - 1);
        System.out.println("\nPosibles sustitutos para " + ausente + " el " + dia + " a la " + hora + "ª hora:");
        int mejor = -1;
        for (int i = 0; i < profesores.length; i++) {
            if (i != idxProfAusente && horarios[i][slot].equalsIgnoreCase("LIBRE")) {
                System.out.println("- " + profesores[i] + " (lleva " + sustituciones[i] + " sustituciones)");
                if (mejor == -1 || sustituciones[i] < sustituciones[mejor]) {
                    mejor = i;
                }
            }
        }
        if (mejor == -1) {
            System.out.println("⚠ No hay sustitutos disponibles.");
        } else {
            System.out.println("→ Se asigna a: " + profesores[mejor]);
            sustituciones[mejor]++;
        }
    }

    public void consultarSustituciones(String nombre) {
        int idx = getIndiceProfesor(nombre);
        if (idx == -1) {
            System.out.println("Profesor no encontrado");
            return;
        }
        System.out.println(nombre + " ha hecho " + sustituciones[idx] + " sustituciones.");
    }

    public void mostrarRanking() {
        System.out.println("\n--- Ranking de sustituciones ---");
        for (int i = 0; i < profesores.length - 1; i++) {
            for (int j = 0; j < profesores.length - i - 1; j++) {
                if (sustituciones[j] < sustituciones[j + 1]) {
                    int tmpS = sustituciones[j];
                    sustituciones[j] = sustituciones[j + 1];
                    sustituciones[j + 1] = tmpS;

                    String tmpP = profesores[j];
                    profesores[j] = profesores[j + 1];
                    profesores[j + 1] = tmpP;

                    String[] tmpH = horarios[j];
                    horarios[j] = horarios[j + 1];
                    horarios[j + 1] = tmpH;
                }
            }
        }
        for (int i = 0; i < profesores.length; i++) {
            System.out.println((i+1) + ". " + profesores[i] + " → " + sustituciones[i] + " sustituciones");
        }
    }
}
