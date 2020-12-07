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

    private Nave player;
    private Armada enemyWave;
    private List<PosicionBloques> guards;

    private boolean inGame;
    private Integer lives;
    private String message;     //message for the end of a game

    public Tablero() {

        inGame = true;
        lives = 3;

        player = new Nave(START_X, START_Y);
        enemyWave = new Armada();

        guards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            guards.add(new PosicionBloques(GUARD_POSX + i * 125, GUARD_POSY));
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

        while (inGame) {
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
        gameOver();
    }

    @Override
    public void paintComponent(Graphics g
    ) {

        super.paintComponent(g);

        Font font = new Font("Helvetica", Font.PLAIN, 15);
        g.setColor(Color.WHITE);
        g.setFont(font);

        g.drawString("Vidas: " + lives.toString(), 20, 30);
        g.drawString("Enemigos restantes: " + enemyWave.getNumeroEnemigo().toString(), 20, 50);

        g.setColor(Color.GREEN);
        g.drawLine(0, GROUND, ANCHO_FRAME, GROUND);

        player.draw(g, this);
        if (player.getM().isVisible()) {
            player.getM().draw(g, this);
        }

        enemyWave.dibujarArmada(g, this);

        for (PosicionBloques guard : guards) {
            guard.draw(g);
        }
    }

    private void animationCycle() {
        if (enemyWave.getNumeroEnemigo() == 0) {
            inGame = false;
            message = "Ganaste!! Felicitaciones!!";
        }

        if (player.isDying()) {
            lives--;
            if (lives != 0) {
                player.revive();
            } else {
                inGame = false;
                message = "Fin del juego!";
            }
        }

        if (enemyWave.tocoSuelo()) {
            inGame = false;
            message = "Game Over!";
        }

        player.mover();
        player.missleMove();
        enemyWaveMove();
        collisionMissileEnemies();
        collisionBombPlayer();
        collisionWithGuards();
    }

    private void enemyWaveMove() {
        enemyWave.fixStatus();
        enemyWave.bombMove();
        enemyWave.disparo();
        enemyWave.accelerateIfNeeded();
        enemyWave.turnAroundIfHitTheWall();
    }

    private void collisionMissileEnemies() {
        if (player.getM().isVisible()) {
            for (Enemigo enemy : enemyWave.getEnemigos()) {
                if (enemy.isVisible() && player.getM().collisionWith(enemy)) {
                    enemy.explosion();
                    enemyWave.disminuirNumeroEnemigos();
                    player.getM().die();
                }
            }
        }
    }

    private void collisionBombPlayer() {
        for (Enemigo enemy : enemyWave.getEnemigos()) {
            if (enemy.getBomb().isVisible() && enemy.getBomb().collisionWith(player)) {
                player.explosion();
                enemy.getBomb().die();
            }
        }
    }

    private void collisionWithGuards() {
        for(PosicionBloques guard : guards) {
            guard.collisionWith(player.getM());
            for (Enemigo enemy : enemyWave.getEnemigos()) {
               guard.collisionWith(enemy.getBomb());
            }
        }
    }
    
    private void gameOver() {
        Graphics g = this.getGraphics();
        super.paintComponent(g);

        Font font = new Font("Helvetica", Font.BOLD, 18);
        FontMetrics ft = this.getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(message, (ANCHO_FRAME - ft.stringWidth(message)) / 2, ALTO_FRAME / 2);
    }

    private class KAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

    }
}
