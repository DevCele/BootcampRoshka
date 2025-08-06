import java.util.Random;
import java.util.Scanner;

public class EjerciciosJava002 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ejercicio01(sc);
        ejercicio02(sc);
        esPalindromo(sc);
        ejercicio04(sc);


    }
    public static void ejercicio01(Scanner sc){
        //Ejercicio 01.java
        int [] numeros = new int[10];
        Random rand = new Random();

        for (int i = 0; i < numeros.length; i++) {
            numeros[i] = rand.nextInt(11)-5;
        }

        System.out.print("Array Generado: ");
        for(int num : numeros) {
            System.out.print(num + " ");
        }

        System.out.println();

        int mayor = numeros[0];
        for (int i = 1; i < numeros.length; i++) {
            if(numeros[i] > mayor) {
                mayor = numeros [i];
            }
        }
        System.out.println("El número mayor es: "+ mayor);
    }

    public static void ejercicio02(Scanner sc) {
        //Ejercicio 02.java
        int [] numeros = new int[100];
        Random rand = new Random();

        for (int i = 0; i < numeros.length; i++) {
            numeros[i] = rand.nextInt(101)-30;
        }

        System.out.print("Array Generado: ");
        for(int num : numeros) {
            System.out.print(num + " ");
        }

        System.out.println();

        int[] frecuencia = new int[101];
        for(int i = 0; i < numeros.length; i++) {
            frecuencia[numeros[i] + 30]++;
        }

        int maxFrecuencia = frecuencia[0];
        int valorMasRepetido = -30;

        for(int i = 1; i < frecuencia.length; i++) {
            if (frecuencia[i] > maxFrecuencia) {
                maxFrecuencia = frecuencia[i];
                valorMasRepetido = i - 30;
            }
        }
        System.out.println("El numero que mas se repite es: " + valorMasRepetido +
                " aparece " + maxFrecuencia + " veces");


        System.out.println("Los numeros que no aparecen: ");
        for(int i = 0; i < frecuencia.length; i++) {
            if(frecuencia[i] == 0) {
                System.out.print((i - 30) + " ");
            }
        }
        System.out.println();
    }

    public static void esPalindromo(Scanner sc) {
        //Ejercicio 03.java
        System.out.println("Ingresa una palabra o frese: ");
        String palabraOFrase = sc.nextLine();

        String palabraLimpia = palabraOFrase.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        String palabraInvertida = new StringBuilder(palabraLimpia).reverse().toString();


        if(palabraLimpia.equals(palabraInvertida)) {
            System.out.println("Es un palindromo");
        }else {
            System.out.println("No es un palindromo");
        }
    }

    public static void ejercicio04 (Scanner sc) {
        //Ejercicio 04.java
        System.out.println("Ingresa una cadena solamente de digitos: ");
        String cadenaDigitos = sc.nextLine();

        boolean esValida = true;
        for (int i = 0; i < cadenaDigitos.length(); i++) {
            if (!Character.isDigit(cadenaDigitos.charAt(i))) {
                esValida = false;
                break;
            }
        }

        if (!esValida) {
            System.out.println("Error: La cadena contiene caracteres no numéricos.");
            return;
        }

        int[] digitosConvertidos = new int[cadenaDigitos.length()];


        for (int i = 0; i < cadenaDigitos.length(); i++) {
            char c = cadenaDigitos.charAt(i);
            digitosConvertidos[i] = c - '0';
        }


        System.out.print("Array de números: ");
        for (int num : digitosConvertidos) {
            System.out.print(num + " ");
        }
        System.out.println();

    }
}
