package control;

import static control.Globales.*;

public class Bomba extends Movimiento {

    Bomba(int x, int y) {
        super(x, y);
        loadImage("bomb.png");
        ancho=BOMB_WIDTH;
        alto=BOMB_HEIGHT;
        dy=BOMB_SPEED;
    }

    @Override
    public void mover() {
        if(y>GROUND-BOMB_HEIGHT)
            this.die();
        super.mover();
    }
}
