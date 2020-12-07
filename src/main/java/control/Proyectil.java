package control;

import static control.Globales.ALTO_PROYECTIL;
import static control.Globales.VELOCIDAD_PROYECTIL;
import static control.Globales.ANCHO_PROYECTIL;

public class Proyectil extends Movimiento {

    Proyectil(int x, int y) {
        super(x, y);
        loadImage("missile.png");
        ancho=ANCHO_PROYECTIL;
        alto=ALTO_PROYECTIL;
        dy=-VELOCIDAD_PROYECTIL;
    }

    @Override
    public void mover() {
        if(y<=0)
            this.muerto();
        super.mover();
    }

}
