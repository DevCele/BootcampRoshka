import java.util.*;

public class Poker2 {
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


        Carta[] cartas1 = mazo.subList(0, 5).toArray(new Carta[5]);
        Carta[] cartas2 = mazo.subList(5,10).toArray(new Carta[5]);

        ManoDePoker mano1 = new ManoDePoker(cartas1);
        ManoDePoker mano2 = new ManoDePoker(cartas2);

        // Mostrar Mano 1
        System.out.println("=== Mano 1 ===");
        imprimir(cartas1);
        System.out.println("Jugada: " + mano1.getJugada());
        System.out.println();

        // Mostrar Mano 2
        System.out.println("=== Mano 2 ===");
        imprimir(cartas2);
        System.out.println("Jugada: " + mano2.getJugada());
        System.out.println();

        // Determinar ganadora
        ManoDePoker[] manos = { mano1, mano2 };
        ManoDePoker ganadora = manoGanadora(manos);
        String cual = (ganadora == mano1) ? "Mano 1" : "Mano 2";
        System.out.println("Ganadora: " + cual + " (" + ganadora.getJugada() + ")");
    }


    private static void imprimir(Carta[] cartas) {
        for (Carta c : cartas) {
            System.out.println(c.getValor() + " de " + paloToString(c.getPalo()));
        }
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

    private static ManoDePoker manoGanadora(ManoDePoker[] manos) {
        ManoDePoker mejor = manos[0];
        for (int i = 1; i < manos.length; i++) {
            if (esMejor(manos[i], mejor)) {
                mejor = manos[i];
            }
        }
        return mejor;
    }

    private static boolean esMejor(ManoDePoker m1, ManoDePoker m2) {
        if (m1.getRanking().getValor() > m2.getRanking().getValor()) return true;
        if (m1.getRanking().getValor() < m2.getRanking().getValor()) return false;
        return m1.getCartaMasAlta() > m2.getCartaMasAlta();
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
