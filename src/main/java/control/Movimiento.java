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
    boolean impacto;
    boolean visible;

    Movimiento(int x, int y) {
        this.x=x;
        this.y=y;
        visible=true;
        impacto=false;
    }

    void cargarImagen(String imageName) {
        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
    }

    public void explosion() {
        cargarImagen("explosion.png");
        setImpacto(true);
    }

    public Rectangle getLimite() {
        return new Rectangle(x, y, ancho, alto);
    }

    public boolean colisionar(Movimiento o) {
        return this.getLimite().intersects(o.getLimite());
    }

    public void dibujar(Graphics g, Tablero board) {
        g.drawImage(image, x, y, ancho, alto, board);
    }

    //Verifica si debe eliminar si ha sido impacto
    public void muerto() {
        visible=false;
    }

    public boolean isVisible() {
        return visible;
    }

    void setImpacto(boolean b) {
        impacto=b;
    }

    public boolean isDying() {
        return impacto;
    }

    public void mover() {
        x+=dx;
        y+=dy;
    }

}
