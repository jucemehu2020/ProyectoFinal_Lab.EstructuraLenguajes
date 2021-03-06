package control;

import java.awt.event.KeyEvent;
import static control.Globales.*;

public class Nave extends Movimiento {

    private Proyectil m;

    public Nave (int x, int y) {
        super(x, y);
        cargarImagen("player.png");
        ancho=ANCHO_NAVE;
        alto=ALTO_NAVE;
        m = new Proyectil(0, 0);
        m.muerto();
    }

    public Proyectil getM() {
        return m;
    }

    public void revive() {
        cargarImagen("player.png");
        setImpacto(false);
        x=ANCHO_FRAME/2;
    }
    
    //Movimiento del misil
    public void movMisil() {
        if(m.isVisible()) {
            m.mover();
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -NAVE_VELOCIDAD;
        }
        if (key == KeyEvent.VK_RIGHT) {
            dx = NAVE_VELOCIDAD;
        }
        if (key == KeyEvent.VK_SPACE) {
            if(!m.visible) {
                m.visible=true;
                m.x=this.x + ANCHO_NAVE/2;
                m.y=this.y;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if(key==KeyEvent.VK_LEFT) {
            dx=0;
        }
        if(key==KeyEvent.VK_RIGHT) {
            dx=0;
        }
    }

    @Override
    public void mover() {
        if(x>ANCHO_FRAME-ANCHO_NAVE)
            x=ANCHO_FRAME-ANCHO_NAVE;
        else if(x<0)
            x=0;
        else
            super.mover();
    }

}
