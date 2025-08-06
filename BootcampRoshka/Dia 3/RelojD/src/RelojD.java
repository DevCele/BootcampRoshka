import java.util.Scanner;

public class RelojD {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese los segundos: ");
        int segundos = scanner.nextInt();
        Reloj reloj1 = new Reloj(segundos);
        System.out.println("La hora es: " + String.valueOf(reloj1));

        for(int i = 0; i < 10; ++i) {
            reloj1.tick();
            System.out.println("Tick" + (i + 1) + ":" + String.valueOf(reloj1));
        }
        System.out.println("Los minutos son: " + reloj1.getMinutos());
        System.out.println("Los segundos son: " + reloj1.getSegundos());
        Reloj reloj2 = new Reloj(3600);
        System.out.println("Reloj 2 " + String.valueOf(reloj2));
        int diferencia = reloj1.restaReloj(reloj2);
        System.out.println("Diferencia en segundos: " + diferencia);
        scanner.close();
    }
}
