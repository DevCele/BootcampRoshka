import java.util.*;

public class ManoDePoker {
    private Carta[] cartas;
    private static final Map<String, Integer> valorNumerico = new HashMap<>();

    static {
        valorNumerico.put("2", 2);
        valorNumerico.put("3", 3);
        valorNumerico.put("4", 4);
        valorNumerico.put("5", 5);
        valorNumerico.put("6", 6);
        valorNumerico.put("7", 7);
        valorNumerico.put("8", 8);
        valorNumerico.put("9", 9);
        valorNumerico.put("T", 10);
        valorNumerico.put("J", 11);
        valorNumerico.put("Q", 12);
        valorNumerico.put("K", 13);
        valorNumerico.put("A", 14);
    }

    public ManoDePoker(Carta[] cartas) {
        if (cartas.length != 5) {
            throw new IllegalArgumentException("Una mano debe tener 5 cartas.");
        }
        this.cartas = cartas;
    }

    private Map<String, Integer> contarValores() {
        Map<String, Integer> conteo = new HashMap<>();
        for (Carta c : cartas) {
            conteo.put(c.getValor(), conteo.getOrDefault(c.getValor(), 0) + 1);
        }
        return conteo;
    }

    public boolean esColor() {
        char palo = cartas[0].getPalo();
        for (Carta c : cartas) {
            if (c.getPalo() != palo) return false;
        }
        return true;
    }

    public boolean esEscalera() {
        List<Integer> valores = new ArrayList<>();
        for (Carta c : cartas) {
            valores.add(valorNumerico.get(c.getValor()));
        }
        Collections.sort(valores);

        // caso especial A-2-3-4-5
        if (valores.equals(Arrays.asList(2, 3, 4, 5, 14))) {
            return true;
        }

        for (int i = 0; i < valores.size() - 1; i++) {
            if (valores.get(i) + 1 != valores.get(i + 1)) return false;
        }
        return true;
    }

    public boolean esEscaleraDeColor() {
        return esEscalera() && esColor();
    }

    public boolean esPoker() {
        for (int count : contarValores().values()) {
            if (count == 4) return true;
        }
        return false;
    }

    public boolean esFull() {
        boolean tieneTres = false;
        boolean tieneDos = false;
        for (int count : contarValores().values()) {
            if (count == 3) tieneTres = true;
            if (count == 2) tieneDos = true;
        }
        return tieneTres && tieneDos;
    }

    public boolean esTrio() {
        for (int count : contarValores().values()) {
            if (count == 3) return true;
        }
        return false;
    }

    public boolean esDoblePar() {
        int pares = 0;
        for (int count : contarValores().values()) {
            if (count == 2) pares++;
        }
        return pares == 2;
    }

    public boolean esPar() {
        for (int count : contarValores().values()) {
            if (count == 2) return true;
        }
        return false;
    }

    public String getJugada() {
        if (esEscaleraDeColor()) return "Escalera de Color";
        if (esPoker()) return "Póker";
        if (esFull()) return "Full";
        if (esColor()) return "Color";
        if (esEscalera()) return "Escalera";
        if (esTrio()) return "Trío";
        if (esDoblePar()) return "Doble Par";
        if (esPar()) return "Par";
        return "Carta Alta";
    }

    public enum RankingMano {
        ESCALERA_COLOR(8),
        POKER(7),
        FULL(6),
        COLOR(5),
        ESCALERA(4),
        TRIO(3),
        DOBLE_PAR(2),
        PAR(1),
        CARTA_ALTA(0);

        private final int valor;
        RankingMano(int valor) { this.valor = valor; }
        public int getValor() { return valor; }
    }

    public RankingMano getRanking() {
        if (esEscaleraDeColor()) return RankingMano.ESCALERA_COLOR;
        if (esPoker()) return RankingMano.POKER;
        if (esFull()) return RankingMano.FULL;
        if (esColor()) return RankingMano.COLOR;
        if (esEscalera()) return RankingMano.ESCALERA;
        if (esTrio()) return RankingMano.TRIO;
        if (esDoblePar()) return RankingMano.DOBLE_PAR;
        if (esPar()) return RankingMano.PAR;
        return RankingMano.CARTA_ALTA;
    }

    public int getCartaMasAlta() {
        List<Integer> valores = Arrays.stream(cartas)
                .map(c -> valorNumerico.get(c.getValor()))
                .sorted()
                .toList();

        // Caso especial de escalera baja: A-2-3-4-5
        if (valores.equals(Arrays.asList(2, 3, 4, 5, 14))) {
            return 5;
        }

        return valores.get(valores.size() - 1);
    }
}
