package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class BolaFuego {
	
	double x, y, escala, dx, angulo;
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
		this.angulo = 0;
	}
	
	public void dibujar()
	{
		e.dibujarImagen(img, this.x, this.y, angulo, escala);
	}
	
	public void mover()
	{
		this.x += dx;
	}
	
	

}
