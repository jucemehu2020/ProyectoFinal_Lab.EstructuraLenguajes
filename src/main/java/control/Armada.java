package control;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import static control.Globales.*;

public class Armada {

    private List<Enemigo> enemigos;
    private Integer numeroDeEnemigos;
    private int velocidadEnemigos;

    //Obtiene enemigos iniciales
    public List<Enemigo> getEnemigos() {
        return enemigos;
    }

    //Obtiene el numero de enemigos actuales
    public Integer getNumeroEnemigo() {
        return numeroDeEnemigos;
    }

    //Disminuye enemigos 
    public void disminuirNumeroEnemigos() {
        numeroDeEnemigos--;
    }

    //Metodo encargado de llenar la lista de enemigos 
    public Armada() {
        enemigos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                enemigos.add(new Enemigo(ALIEN_X + 32 * j, ALIEN_Y + 32 * i));
            }
        }
        numeroDeEnemigos = 50;
        velocidadEnemigos = 1;
    }

    //Metodo para dibujar la armada en la 
    public void dibujarArmada(Graphics g, Tablero board) {
        for (Enemigo enemigo : enemigos) {
            if (enemigo.visible) {
                enemigo.draw(g, board);
            }
            if (enemigo.bomba.visible) {
                enemigo.bomba.draw(g, board);
            }
        }
    }

    //Metodo que pregunta si toco el suelo cada vez que baja 
    public boolean tocoSuelo() {
        for (Enemigo enemigo : enemigos) {
            if (enemigo.visible && enemigo.y + enemigo.alto > GUARD_POSY) {
                return true;
            }
        }
        return false;
    }

    //Metodo que valida que aliens estan muertos
    public void fixStatus() {
        for (Enemigo enemigo : enemigos) {
            if (enemigo.dying) {
                enemigo.setAlmostDied(true);
                enemigo.setDying(false);
            } else if (enemigo.almostDied) {
                enemigo.muerto();
                enemigo.setAlmostDied(false);
            } else if (enemigo.visible) {
                enemigo.mover();
            }
        }
    }

    //Movimiento de la bomba(Hacia abajo)
    public void movimientoBomba() {
        for (Enemigo enemigo : enemigos) {
            if (enemigo.bomba.visible) {
                enemigo.bomba.mover();
            }
        }
    }

    //Metodo del disparo de los enemigos
    public void disparo() {
        for (Enemigo enemigo : enemigos) {
            enemigo.tratarDisparar();
        }
    }

    //Metodo que acelera a los enemigos mientras menos van quedando
    public void acelerarEnemigos() {
        boolean bandera = false;
        if (numeroDeEnemigos == 30) {
            velocidadEnemigos = 1;
            bandera = true;
        }
        if (numeroDeEnemigos == 15) {
            velocidadEnemigos = 2;
            bandera = true;
        }
        if (bandera) {
            for (Enemigo enemigo : enemigos) {
                if (enemigo.dx > 0) {
                    enemigo.dx = velocidadEnemigos;
                } else {
                    enemigo.dx = -velocidadEnemigos;
                }
            }
        }
    }

    //Metodo que verifica si la armada toco la pared, si es asi los devuelve y los baja
    public void tocoPared() {
        for (Enemigo enemigo : enemigos) {
            if (enemigo.x > ANCHO_FRAME - ANCHO_ALIEN) {
                for (Enemigo reversaEnemigos : enemigos) {
                    reversaEnemigos.dx = -velocidadEnemigos;
                    reversaEnemigos.y += 15;
                }
                return;
            }
            if (enemigo.x < 0) {
                for (Enemigo reversaEnemigos : enemigos) {
                    reversaEnemigos.dx = velocidadEnemigos;
                    reversaEnemigos.y += 15;
                }
                return;
            }
        }
    }
}
