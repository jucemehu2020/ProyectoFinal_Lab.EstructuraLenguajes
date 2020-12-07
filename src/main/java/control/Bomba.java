package control;

import static control.Globales.*;

public class Bomba extends Movimiento {

    //Constructor de la bomba 
    Bomba(int x, int y) {
        super(x, y);
        loadImage("bomb.png");
        ancho=ANCHO_BOMBA;
        alto=ALTO_BOMBA;
        dy=VELOCIDAD_BOMBA;
    }

    //Metodo que permite el movimiento de las bombas
    @Override
    public void mover() {
        if(y>POSICION_SUELO-ALTO_BOMBA)
            this.muerto();
        super.mover();
    }
}
