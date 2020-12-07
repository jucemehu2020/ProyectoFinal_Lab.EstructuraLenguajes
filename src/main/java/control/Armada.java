package control;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import static control.Globales.*;

public class Armada {

    private List<Enemigo> enemigo;
    private Integer numeroDeEnemigos;
    private int velocidadEnemigo;

    //Obtiene enemigos iniciales
    public List<Enemigo> getEnemigos() {
        return enemigo;
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
        enemigo = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                enemigo.add(new Enemigo(ENEMIGOS_X + 32 * j, ENEMIGOS_Y + 32 * i));
            }
        }
        numeroDeEnemigos = 50;
        velocidadEnemigo = 1;
    }

    //Metodo para dibujar la armada en la 
    public void dibujarArmada(Graphics g, Tablero board) {
        for (Enemigo enemy : enemigo) {
            if (enemy.visible) {
                enemy.draw(g, board);
            }
            if (enemy.bomba.visible) {
                enemy.bomba.draw(g, board);
            }
        }
    }

    //Metodo que pregunta si toco el suelo cada vez que baja 
    public boolean tocoSuelo() {
        for (Enemigo enemy : enemigo) {
            if (enemy.visible && enemy.y + enemy.alto > GUARD_POSY) {
                return true;
            }
        }
        return false;
    }

    //Metodo que valida que aliens estan muertos
    public void fixStatus() {
        for (Enemigo enemy : enemigo) {
            if (enemy.dying) {
                enemy.setAlmostDied(true);
                enemy.setDying(false);
            } else if (enemy.almostDied) {
                enemy.muerto();
                enemy.setAlmostDied(false);
            } else if (enemy.visible) {
                enemy.mover();
            }
        }
    }

    public void bombMove() {
        for (Enemigo enemy : enemigo) {
            if (enemy.bomba.visible) {
                enemy.bomba.mover();
            }
        }
    }

    //Metodo del disparo de los enemigos
    public void disparo() {
        for (Enemigo enemy : enemigo) {
            enemy.tratarDisparar();
        }
    }

    //Metodo que acelera a los enemigos mientras menos van quedando
    public void acelerarEnemigos() {
        boolean b = false;
        if (numeroDeEnemigos == 16) {
            velocidadEnemigo = 1;
            b = true;
        }
        if (numeroDeEnemigos == 8) {
            velocidadEnemigo = 2;
            b = true;
        }
        if (b) {
            for (Enemigo enemy : enemigo) {
                if (enemy.dx > 0) {
                    enemy.dx = velocidadEnemigo;
                } else {
                    enemy.dx = -velocidadEnemigo;
                }
            }
        }
    }

    //Metodo que verifica si la armada toco la pared, si es asi los devuelve y los baja
    public void tocoPared() {
        for (Enemigo enemy : enemigo) {
            if (enemy.x > ANCHO_FRAME - ANCHO_ALIEN) {
                for (Enemigo enemyReversed : enemigo) {
                    enemyReversed.dx = -velocidadEnemigo;
                    enemyReversed.y += 15;
                }
                return;
            }

            if (enemy.x < 0) {
                for (Enemigo enemyReversed : enemigo) {
                    enemyReversed.dx = velocidadEnemigo;
                    enemyReversed.y += 15;
                }
                return;
            }
        }
    }
}
