import java.util.Arrays;
import java.util.Random;

public class Generala {
    public static void main(String[] args) {
        Generala g = new Generala();

        String dados = g.tirarDados();
        System.out.println("Tirada: " + dados);
        System.out.println("Jugada: " + g.jugada(dados));
    }

    public String tirarDados() {
        Random random = new Random();
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            int dado = random.nextInt(6) + 1; // genera número entre 1 y 6
            resultado.append(dado);
        }

        return resultado.toString();
    }

    public String jugada (String jugada) {
        if (jugada.length() != 5) {
            return "INVALIDO";
        }

        int[] contador = new int[6];

        for(int i = 0; i < jugada.length(); i++) {
            char c = jugada.charAt(i);

            if( c < '1' || c > '6') {
                return "INVALIDO";
            }
            int valor = c - '1';
            contador[valor]++;
        }

        // Verifica las combinaciones
        for(int i = 0; i < 6; i++) {
            if(contador[i] == 5) {
                return "GENERALA";
            }
        }

        for(int i = 0; i < 6; i++) {
            if(contador[i] == 4) {
                return "POKER";
            }
        }

        boolean hayTres = false;
        boolean hayDos = false;
        for(int i = 0; i < 6; i++) {
            if(contador[i] == 3) {
                hayTres = true;
            } else if (contador[i] == 2) {
                hayDos = true;
            }
        }
        if(hayTres && hayDos) {
            return "FULL";
        }

        int[] escalera1 = {1, 1, 1, 1, 1, 0};
        int[] escalera2 = {0, 1, 1, 1, 1, 1};
        int[] escalera3 = {1, 0, 1, 1, 1, 1};

        if (Arrays.equals(contador, escalera1) ||
                Arrays.equals(contador, escalera2) ||
                Arrays.equals(contador, escalera3)) {
            return "ESCALERA";
        }

        return "NADA";
    }
}
