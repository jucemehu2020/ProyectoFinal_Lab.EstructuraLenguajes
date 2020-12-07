package control;

import javax.swing.*;
import java.awt.*;

public class Movimiento {

    private Image image;
    int x;
    int y;
    int dx;     //velocity OX
    int dy;     //velocity OY
    int ancho;
    int alto;
    boolean dying;
    boolean visible;

    Movimiento(int x, int y) {
        this.x=x;
        this.y=y;
        visible=true;
        dying=false;
    }

    void loadImage(String imageName) {
        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
    }

    public void explosion() {
        loadImage("explosion.png");
        setDying(true);
    }

    public Rectangle getBoundary() {
        return new Rectangle(x, y, ancho, alto);
    }

    public boolean collisionWith(Movimiento o) {
        return this.getBoundary().intersects(o.getBoundary());
    }

    public void draw(Graphics g, Tablero board) {
        g.drawImage(image, x, y, ancho, alto, board);
    }

    //Verifica si debe eliminar si ha sido impactado
    public void muerto() {
        visible=false;
    }

    public boolean isVisible() {
        return visible;
    }

    void setDying(boolean b) {
        dying=b;
    }

    public boolean isDying() {
        return dying;
    }

    public void mover() {
        x+=dx;
        y+=dy;
    }

}
