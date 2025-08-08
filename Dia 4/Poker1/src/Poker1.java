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


/*
 Probabilidades:
 1. Escalera de Color  hay una probabilidad de 1 en 72.193 manos
 2. Póker hay una probabilidad de 1 en 4.165 manos
 3. Full House hay una probabilidad de 1 en 693 manos
 4. Escalera hay una probabilidad de 1 en 255 manos
 5. Trío hay una probabilidad de 1 en 47 manos
 6. Doble Par hay una probabilidad de 1 en 21 manos
 7. Parhay una probabilidad de 1 en 2.3 manos
 8. Carta Alta hay una probabilidad de Es la opción más común
*/
