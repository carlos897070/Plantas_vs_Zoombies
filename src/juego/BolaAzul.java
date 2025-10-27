package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class BolaAzul {

	double x, y, escala, dx;
	Image img, imgDaño;
	Entorno e;
	
	public BolaAzul(double x, double y, Entorno e) {

		this.x = x;
		this.y = y;
		this.e = e;
		this.img = Herramientas.cargarImagen("Imagenes/bolaAzul2.gif");
		this.escala = .12;
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
