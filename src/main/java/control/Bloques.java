package control;

import java.awt.*;
import static control.Globales.TAMAÑO_BLOQUE;

class Bloques extends Rectangle  {

    boolean visible;

    //Contructor para la creacion del bloque
    Bloques(int x, int y) {
        super(x, y, TAMAÑO_BLOQUE, TAMAÑO_BLOQUE);
        setVisible(true);
    }

    void setVisible(boolean visible) {
        this.visible = visible;
    }

    //Metodo para generar los bloques en la pantalla
    void dibujarBloques(Graphics g) {
        g.setColor(new Color(241, 59, 53));
        g.fillRect(x, y, width, height);
    }
}
