package vista;

import javax.swing.*;
import control.Tablero;
import java.awt.Image;
import java.awt.Toolkit;
import static control.Globales.ALTO_FRAME;
import static control.Globales.ANCHO_FRAME;

public class Jugar extends JFrame {
    public final String ICONO_APLICATION = "src/icono-aplicacion.png";
    //Metodo que inicia el tablero con sus respectivas configuraciones
    public Jugar() {
        this.add(new Tablero());
        Image iconoAplicacion = Toolkit.getDefaultToolkit().getImage(ICONO_APLICATION);
        this.setIconImage(iconoAplicacion);
        setSize(ANCHO_FRAME, ALTO_FRAME);
        setResizable(false);
        setTitle("Aliens Invasores");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Jugar();
    }
}
