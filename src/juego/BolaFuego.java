package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class BolaFuego {
	
	double x, y, escala, dx;
	Image img, imgDaño;
	Entorno e;
	
	
	public BolaFuego(double x, double y, Entorno e) {

		this.x = x;
		this.y = y;
		this.e = e;
		this.img = Herramientas.cargarImagen("Imagenes/bola_fuego.gif");
		this.escala = 0.38;
		this.dx = 2;
		this.imgDaño = Herramientas.cargarImagen("Imagenes/impacto.gif");
	}
	
	public void dibujar()
	{
		e.dibujarImagen(img, this.x, this.y, 0, escala);
	}
	
	public void mover()
	{
		this.x += dx;
	}
	
	

}
