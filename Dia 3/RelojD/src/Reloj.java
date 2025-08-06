public class Reloj {
    private int horas;
    private int minutos;
    private int segundos;

    public Reloj() {
        this.horas = 12;
        this.minutos = 0;
        this.segundos = 0;
    }

    public Reloj(int horas, int minutos, int segundos) {
        this.setHoras(horas);
        this.setMinutos(minutos);
        this.setSegundos(segundos);
    }

    public Reloj(int totalSegundos) {
        this.setReloj(totalSegundos);
    }

    public void setReloj(int totalSegundos) {
        if (totalSegundos < 0) {
            totalSegundos = 0;
        }

        totalSegundos %= 86400;
        this.horas = totalSegundos / 3600;
        totalSegundos %= 3600;
        this.minutos = totalSegundos / 60;
        this.segundos = totalSegundos % 60;
    }

    public int getHoras() {
        return this.horas;
    }

    public void setHoras(int horas) {
        if (horas >= 0 && horas < 24) {
            this.horas = horas;
        }

    }

    public int getMinutos() {
        return this.minutos;
    }

    public void setMinutos(int minutos) {
        if (minutos >= 0 && minutos < 60) {
            this.minutos = minutos;
        }

    }

    public int getSegundos() {
        return this.segundos;
    }

    public void setSegundos(int segundos) {
        if (segundos >= 0 && segundos <= 60) {
            this.segundos = segundos;
        }

    }

    public void tick() {
        ++this.segundos;
        if (this.segundos >= 60) {
            this.segundos = 0;
            ++this.minutos;
            if (this.minutos >= 60) {
                this.minutos = 0;
                ++this.horas;
                if (this.horas >= 24) {
                    this.horas = 0;
                }
            }
        }

    }

    public void tickDecrement() {
        --this.segundos;
        if (this.segundos < 60) {
            this.segundos = 59;
            --this.minutos;
            if (this.minutos < 60) {
                this.minutos = 59;
                --this.horas;
                if (this.horas < 24) {
                    this.horas = 23;
                }
            }
        }

    }

    public void addReloj(Reloj otro) {
        int totalSegundos = this.toSegundos() + otro.toSegundos();
        this.setReloj(totalSegundos);
    }

    private int toSegundos() {
        return this.horas * 3600 + this.minutos * 60 + this.segundos;
    }

    public int restaReloj(Reloj otro) {
        int diferencia = this.toSegundos() - otro.toSegundos();
        return Math.abs(diferencia);
    }

    public String toString() {
        return String.format("[%02d:%02d:%02d]", this.horas, this.minutos, this.segundos);
    }

    //Rama para merge metodo extra
    public void mostrarHora() {
        System.out.println("Hora actual: " + toString());git
    }
}
