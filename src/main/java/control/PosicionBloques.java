package control;

import control.Movimiento;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import static control.Globales.TAMAÑO_BLOQUE;

public class PosicionBloques {

    private List<Bloques> squares;

    //Contructor de la posicion de los bloques
    public PosicionBloques(int x, int y) {
        squares=new ArrayList<>();
        for(int i=0; i<3; i++) {
            for (int j = 0; j < 5; j++) {
                squares.add(new Bloques(x + TAMAÑO_BLOQUE * j, y + TAMAÑO_BLOQUE * i));
            }
        }
    }

    public void collisionWith(Movimiento obj) {
        for(Bloques square : squares) {
            if(square.visible && square.intersects(obj.getBoundary())) {
                square.setVisible(false);
                obj.muerto();
            }
        }
    }

    //Metodo que llama al dibujar bloques
    public void dibujarB(Graphics g) {
        for(Bloques square : squares) {
            if(square.visible) square.dibujarBloques(g);
        }
    }

}
