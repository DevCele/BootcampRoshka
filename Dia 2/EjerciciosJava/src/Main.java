import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Ejercicio1();
        Ejercicio2();
        Ejercicio3();
        Ejercicio4(sc);
        Ejercicio5(sc);
        Ejercio6(sc);
        Ejercicio7();
        Ejercicio8(sc);
        Ejercicio9(sc);
        Ejercicio10(sc);
    }

    public static void Ejercicio1() {
        // Ejercicio 01.java
        int x = 10;
        int y = 2;

        int suma = x + y;
        int resta = x - y;
        int multiplicacion = x * y;
        int division = x / y;
        int modulo = x % y;

        System.out.println("Suma: " + suma);
        System.out.println("Resta: " + resta);
        System.out.println("Multiplicación: " + multiplicacion);
        System.out.println("División: " + division);
        System.out.println("Módulo: " + modulo);
    }

    public static void Ejercicio2() {
        // Ejercicio 02.java
        int a = 90;
        int b = 80;

        if (a > b) {
            System.out.println("El número mayor es: " + a);
        } else if (b > a) {
            System.out.println("El número mayor es: " + b);
        } else {
            System.out.println("Los números son iguales");
        }
    }

    public static void Ejercicio3() {
        // Ejercicio 03.java
        String nombre = "Celeste";
        System.out.println("Bienvenida " + nombre);
    }

    public static void Ejercicio4(Scanner sc) {
        // Ejercicio 04.java
        System.out.print("Escribe tu nombre: ");
        String nombreIngresado = sc.nextLine();
        System.out.println("El nombre ingresado es: " + nombreIngresado);
    }

    public static void Ejercicio5(Scanner sc) {
        // Ejercicio 05.java
        System.out.println("Ingresa un numero");
        int numeroIngresado = sc.nextInt();

        if (numeroIngresado % 2 == 0) {
            System.out.println("El numero es divisible entre 2");
        }else {
            System.out.println("El numero no es divisible entre 2");
        }
    }

    public static void Ejercio6(Scanner sc){
        //Ejercicio 06.java
        final double IVA_10 = 0.1;

        System.out.println("Ingrese el precio del producto");
        double precioProducto = sc.nextDouble();
        double ivaProducto = precioProducto * IVA_10;
        double totalPrecioConIVA = precioProducto + ivaProducto;

        System.out.println("El precio del producto es: "+ precioProducto);
        System.out.println("El valor del IVA del producto es: " + ivaProducto);
        System.out.println("El precio del producto con IVA es: " + totalPrecioConIVA);
    }

    public static void Ejercicio7() {
        //Ejercicio 7.java
        for (int i = 1; i <= 100; i++) {
            if (i % 2 == 0 && i % 3 == 0) {
                System.out.println(i);
            }
        }
    }

    public static void Ejercicio8(Scanner sc) {
        //Ejerciio 8.java
        int numero;

        do {
            System.out.println("Ingrese un numero mayor o igual a 0: ");
            numero = sc.nextInt();
        }while (numero < 0);

        System.out.println("El numero es: " + numero);
    }

    public static void Ejercicio9(Scanner sc){
        //Ejercicio 9.java
        final String contraseña = "abc123";
        int intentos = 0;
        boolean aciertos = false;

        while(intentos < 3 && !aciertos) {
            System.out.println("Ingrese la contraseña: ");
            String intento = sc.nextLine();

            if(intento.equals(contraseña)) {
                System.out.println("Correcto!");
                aciertos = true;
            } else {
                intentos++;
                if(intentos < 3) {
                    System.out.println("Contraseña incorrecta. Intentos restantes: " + (3 - intentos));
                }
            }
        }
        if(!aciertos) {
            System.out.println("Fallaste jaja!");
        }
    }

    public static void Ejercicio10(Scanner sc) {
        //Ejercicio 10.java

        System.out.println("Ingrese un dia de la semana: ");
        String diaSemana = sc.nextLine().toLowerCase();

        switch(diaSemana) {
            case "lunes":
            case "martes":
            case "miercoles":
            case "jueves":
            case "viernes":
                System.out.println("Es un dia laboral");
                break;
            case "sabado":
            case "domingo":
                System.out.println("Es un dia no laboral");
                break;
            default:
                System.out.println("Dia no valido");
        }
    }
}
