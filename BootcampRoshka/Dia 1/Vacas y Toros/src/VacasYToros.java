import java.util.*;

public class VacasYToros {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String numeroSecreto = generarNumeroSecreto();

        System.out.println("¡Bienvenido al juego de Vacas y Toros!");
        System.out.println("Intenta adivinar el número secreto de 4 dígitos (sin dígitos repetidos):");

        while (true) {
            System.out.print("Tu intento: ");
            String intento = scanner.nextLine();

            if (!esNumeroValido(intento)) {
                System.out.println("Entrada inválida. Asegúrate de ingresar 4 dígitos diferentes.");
                continue;
            }

            int toros = contarToros(numeroSecreto, intento);
            int vacas = contarVacas(numeroSecreto, intento);

            if (toros == 4) {
                System.out.println("¡Felicidades! El número secreto era: " + numeroSecreto);
                break;
            } else {
                System.out.println(vacas + " vacas, " + toros + " toros.");
            }
        }
    }

    private static String generarNumeroSecreto() {
        List<Integer> digitos = new ArrayList<>();
        for (int i = 0; i <= 9; i++) digitos.add(i);

        Collections.shuffle(digitos);
        // Asegurar que no empieza con 0
        while (digitos.get(0) == 0) Collections.shuffle(digitos);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(digitos.get(i));
        }
        return sb.toString();
    }

    private static boolean esNumeroValido(String numero) {
        if (numero.length() != 4 || !numero.matches("\\d{4}")) return false;
        Set<Character> set = new HashSet<>();
        for (char c : numero.toCharArray()) set.add(c);
        return set.size() == 4;
    }

    private static int contarToros(String secreto, String intento) {
        int toros = 0;
        for (int i = 0; i < 4; i++) {
            if (secreto.charAt(i) == intento.charAt(i)) toros++;
        }
        return toros;
    }

    private static int contarVacas(String secreto, String intento) {
        int vacas = 0;
        for (int i = 0; i < 4; i++) {
            if (secreto.charAt(i) != intento.charAt(i) && intento.indexOf(secreto.charAt(i)) != -1) {
                vacas++;
            }
        }
        return vacas;
    }
}
