package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Cuadricula {
	
	double x, y, ancho, alto, escala;
	Image im1, im2;
	Entorno e;
	
	
	public Cuadricula(double x, double y, Entorno e) {
		this.x = x;
		this.y = y;
		this.e = e;
		this.im1 = Herramientas.cargarImagen("Imagenes/pasto3.png");
		this.im2 = Herramientas.cargarImagen("Imagenes/pasto4.png");
		this.escala = 0.208;
		this.ancho = this.im1.getWidth(null)*escala;
		this.alto = this.im2.getHeight(null)*escala;
	}
	
	public void dibujar()
	{
		 for (int i = 0; i < 10; i++) {
		        for (int k = 0; k < 5; k++) {
		            double posX = this.x + i * ancho;
		            double posY = this.y + k * alto;

		            if ( (i + k) % 2 == 0 )
		                e.dibujarImagen(im1, posX, posY, 0, escala);
		            else
		                e.dibujarImagen(im2, posX, posY, 0, escala);
		        }
		    }
	}

}
