package control;

import control.Enemigo;
import control.Armada;
import control.Nave;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import static control.Globales.*;

public class Tablero extends JPanel implements Runnable {

    private Nave jugador;
    private Armada oleadaEnemigos;
    private List<PosicionBloques> bloques;

    private boolean jugando;
    private Integer vidas;
    private String mensaje;     //mensaje final del juego

    public Tablero() {

        jugando = true;
        vidas = 3;

        jugador = new Nave(INICIO_X, INICIO_Y);
        oleadaEnemigos = new Armada();

        bloques = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            bloques.add(new PosicionBloques(BLOQUES_POSX + i * 125, BLOQUES_POSY));
        }

        addKeyListener(new KAdapter());     //for Key events
        setFocusable(true);
        setBackground(Color.BLACK);
    }

    @Override
    public void addNotify() {
        super.addNotify();

        Thread animator = new Thread(this);
        animator.start();
    }

    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (jugando) {
            repaint();
            animationCycle();       //mechanics of a game

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = TIEMPO_DISPARO - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            beforeTime = System.currentTimeMillis();
        }
        finJuego();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font font = new Font("Helvetica", Font.PLAIN, 15);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString("Vidas: " + vidas.toString(), 20, 30);
        g.drawString("Enemigos restantes: " + oleadaEnemigos.getNumeroEnemigo().toString(), 20, 50);
        g.drawString("Puntaje: " + oleadaEnemigos.getPuntaje() ,20 ,70);
        g.setColor(Color.GREEN);
        g.drawLine(0, POSICION_SUELO, ANCHO_FRAME, POSICION_SUELO);

        jugador.dibujar(g, this);
        if (jugador.getM().isVisible()) {
            jugador.getM().dibujar(g, this);
        }

        oleadaEnemigos.dibujarArmada(g, this);

        for (PosicionBloques guard : bloques) {
            guard.dibujarB(g);
        }
    }

    private void animationCycle() {
        if (oleadaEnemigos.getNumeroEnemigo() == 0) {
            jugando = false;
            mensaje = "Ganaste!! Felicitaciones!!";
        }
        if (jugador.isDying()) {
            vidas--;
            if (vidas != 0) {
                jugador.revive();
            } else {
                jugando = false;
                mensaje = "Fin del juego!.. Te quedaste sin vidas";
            }
        }
        if (oleadaEnemigos.tocoSuelo()) {
            jugando = false;
            mensaje = "Perdiste!..Los aliens tocaron el limite";
        }
        jugador.mover();
        jugador.movMisil();
        accionesEnemigos();
        verificaImpactoAlien();
        verificaImpactoNave();
        verificaImpactoBloque();
    }

    //Metodo que llama a todo lo que deben hacer los enemigos (Disparar,acelerarlos)
    private void accionesEnemigos() {
        oleadaEnemigos.verificarVidaEnemigos();
        oleadaEnemigos.movimientoBomba();
        oleadaEnemigos.disparo();
        oleadaEnemigos.acelerarEnemigos();
        oleadaEnemigos.tocoPared();
    }

    //Metodo que elimina a los enemigos si les impacta la bala de la nave
    private void verificaImpactoAlien() {
        if (jugador.getM().isVisible()) {
            for (Enemigo enemy : oleadaEnemigos.getEnemigos()) {
                if (enemy.isVisible() && jugador.getM().colisionar(enemy)) {
                    enemy.explosion();
                    oleadaEnemigos.disminuirNumeroEnemigos();
                    jugador.getM().muerto();
                }
            }
        }
    }

    //Metodo que quita vidas al jugador si la nave ha sido impactada
    private void verificaImpactoNave() {
        for (Enemigo enemy : oleadaEnemigos.getEnemigos()) {
            if (enemy.getBomb().isVisible() && enemy.getBomb().colisionar(jugador)) {
                jugador.explosion();
                enemy.getBomb().muerto();
            }
        }
    }

    //Metodo que elimina pedazos del bloque si ha sido impactado
    private void verificaImpactoBloque() {
        for (PosicionBloques guard : bloques) {
            guard.colisionar(jugador.getM());
            for (Enemigo enemy : oleadaEnemigos.getEnemigos()) {
                guard.colisionar(enemy.getBomb());
            }
        }
    }

    //Termina el juego 
    private void finJuego() {
        Graphics g = this.getGraphics();
        super.paintComponent(g);
        Font font = new Font("Helvetica", Font.BOLD, 18);
        FontMetrics ft = this.getFontMetrics(font);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(mensaje, (ANCHO_FRAME - ft.stringWidth(mensaje)) / 2, ALTO_FRAME / 2);
    }

    private class KAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            jugador.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            jugador.keyReleased(e);
        }

    }
}
