package juego;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Image;

public class Explosion {
    double x, y, tiempoInicio, duracion, escala;
    Image img;
    boolean activa;
    Entorno e;

    public Explosion(double x, double y, Entorno e, double escala) {
        this.x = x;
        this.y = y;
        this.escala = escala;
        this.tiempoInicio = e.tiempo()/1000;
        this.duracion = 1; // segundos
        this.img = Herramientas.cargarImagen("Imagenes/explosion.gif");
        this.activa = true;
        this.e = e;
    }

    public void dibujar(Entorno e) {
    	e.dibujarImagen(img, x, y, 0, escala);
    }

    public boolean expirada() {
        return (e.tiempo()/1000 - tiempoInicio) > duracion;
    }
}