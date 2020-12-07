package control;

import static control.Globales.MISSILE_HEIGHT;
import static control.Globales.MISSILE_SPEED;
import static control.Globales.MISSILE_WIDTH;

public class Proyectil extends Movimiento {

    Proyectil(int x, int y) {
        super(x, y);
        loadImage("missile.png");
        ancho=MISSILE_WIDTH;
        alto=MISSILE_HEIGHT;
        dy=-MISSILE_SPEED;
    }

    @Override
    public void mover() {
        if(y<=0)
            this.die();
        super.mover();
    }

}
