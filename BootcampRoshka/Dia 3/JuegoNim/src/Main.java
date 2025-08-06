import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        int pila1 = 3;
        int pila2 = 4;
        int pila3 = 5;

        int jugadorActual = 1;

        Scanner scanner = new Scanner(System.in);

        while (pila1 > 0 || pila2 > 0 || pila3 > 0) {
            System.out.println("\n--- Estado actual de las pilas ---");
            System.out.println("Pila 1: " + pila1);
            System.out.println("Pila 2: " + pila2);
            System.out.println("Pila 3: " + pila3);
            System.out.println("----------------------------------");


            System.out.println("Turno del Jugador " + jugadorActual);

            int pilaSeleccionada = 0;
            boolean pilaValida = false;

            while(!pilaValida) {
                System.out.println("Elige una pila (1, 2, 3)");
                pilaSeleccionada = scanner.nextInt();

                if(pilaSeleccionada == 1 && pila1 > 0) pilaValida = true;
                else if(pilaSeleccionada == 2 && pila2 > 0) pilaValida = true;
                else if (pilaSeleccionada == 3 && pila3 > 0) pilaValida = true;
                else System.out.println("Pila invalida, esta vacia");

            }

            int cantidadSeleccionada = 0;
            boolean cantidadValida = false;

            while(!cantidadValida) {
                System.out.println("Selecciona 1 o mas contadores a quitar");
                cantidadSeleccionada = scanner.nextInt();

                if(cantidadSeleccionada >=1 ) {
                    if(pilaSeleccionada == 1 && cantidadSeleccionada <= pila1) cantidadValida = true;
                    else if(pilaSeleccionada == 2 && cantidadSeleccionada <= pila2) cantidadValida = true;
                    else if (pilaSeleccionada == 3 && cantidadSeleccionada <= pila3) cantidadValida = true;
                    else System.out.println("Cantidad invalida. No hay tantos contadores");
                }else {
                    System.out.println("Debes quitar al menos 1");
                }
            }

            if(pilaSeleccionada == 1) pila1 -= cantidadSeleccionada;
            else if (pilaSeleccionada == 2) pila2 -= cantidadSeleccionada;
            else if (pilaSeleccionada == 3) pila3 -= cantidadSeleccionada;

            if(pila1 == 0 && pila2 == 0 && pila3 == 0) {
                System.out.println("El jugador " + jugadorActual + " quito el ultimo contador");
                int ganador = (jugadorActual == 1) ? 2 : 1;
                System.out.println("El ganador es: " + ganador);
            }


            jugadorActual = (jugadorActual == 1) ? 2 : 1;

        }

    }
}