package control;

import java.awt.event.KeyEvent;
import static control.Globales.*;

public class Nave extends Movimiento {

    private Proyectil m;

    public Nave (int x, int y) {
        super(x, y);
        loadImage("player.png");
        ancho=ANCHO_JUGADOR;
        alto=ALTO_JUGADOR;
        m = new Proyectil(0, 0);
        m.muerto();
    }

    public Proyectil getM() {
        return m;
    }

    public void revive() {
        loadImage("player.png");
        setDying(false);
        x=ANCHO_FRAME/2;
    }

    public void missleMove() {
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
                m.x=this.x + ANCHO_JUGADOR/2;
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
        if(x>ANCHO_FRAME-ANCHO_JUGADOR)
            x=ANCHO_FRAME-ANCHO_JUGADOR;
        else if(x<0)
            x=0;
        else
            super.mover();
    }

}
