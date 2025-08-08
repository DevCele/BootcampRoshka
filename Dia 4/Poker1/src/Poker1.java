import java.util.*;

public class Poker1 {
    public static void main(String[] args) {

        List<Carta> mazo = new ArrayList<>();
        char[] palos = { 'S', 'H', 'D', 'C' };
        String[] valores = { "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A" };

        for (String valor : valores) {
            for (char palo : palos) {
                mazo.add(new Carta(valor, palo));
            }
        }


        Collections.shuffle(mazo);


        Carta[] cartas = mazo.subList(0, 5).toArray(new Carta[5]);


        ManoDePoker mano = new ManoDePoker(cartas);

        System.out.println("Cartas en la mano:");
        for (Carta c : cartas) {
            System.out.println(c.getValor() + " de " + paloToString(c.getPalo()));
        }

        System.out.println("Jugada: " + mano.getJugada());
    }

    private static String paloToString(char palo) {
        return switch (palo) {
            case 'S' -> "Picas";
            case 'H' -> "Corazones";
            case 'D' -> "Diamantes";
            case 'C' -> "Tréboles";
            default -> "Desconocido";
        };
    }
}
