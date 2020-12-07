package control;

import java.util.Random;
import static control.Globales.ALTO_ALIEN;
import static control.Globales.ANCHO_ALIEN;

public class Enemigo extends Movimiento {

    Bomba bomba;
    boolean muriendo;     
    private Random rand;

    public Bomba getBomb() {
        return bomba;
    }

    //Contructor para definir las imagenes de los enemigos
    Enemigo(int x, int y) {
        super(x, y);
        rand = new Random();
        if (x >= 0 && y == 30) {
            cargarImagen("enemy2.png");
        }
        if (x >= 0 && y >= 62) {
            cargarImagen("enemy3.png");
        }
        if (x >= 0 && y > 94) {
            cargarImagen("enemy.png");
        }
        ancho = ANCHO_ALIEN;
        alto = ALTO_ALIEN;
        dx = 1;
        muriendo = false;
        bomba = new Bomba(0, 0);
        bomba.muerto();
    }

    //Metodo para disparar
    void tratarDisparar() {
        int random = rand.nextInt() % 400;
        if (random == 1 && !this.bomba.visible && this.visible) {
            this.bomba.x = this.x + ANCHO_ALIEN / 2;
            this.bomba.y = this.y + ALTO_ALIEN;
            this.bomba.visible = true;
        }
    }

    // cambia el estado del atributo muriendo, que cada enemigo posee, al recibido como argumento.
    void setMuriendo(boolean muriendo) {
        this.muriendo = muriendo;
    }

    //Llama a mover para generar el movimiento de los enemigos
    @Override
    public void mover() {
        super.mover();
    }

}
