package vista;

import javax.swing.*;
import control.Tablero;
import static control.Globales.ALTO_FRAME;
import static control.Globales.ANCHO_FRAME;

public class Jugar extends JFrame {

    //Metodo que inicia el tablero con sus respectivas configuraciones
    public Jugar() {
        this.add(new Tablero());
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
