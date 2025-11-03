package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Tumba {
	
	double x, y, escala, vida;
	Entorno e;
	Image img;
	boolean enterrada;
	
	public Tumba(double x, double y, Entorno e) {
		super();
		this.x = x;
		this.y = y;
		this.e = e;
		this.img = Herramientas.cargarImagen("Imagenes/tumba.png");
		this.escala = 0.19;
		this.enterrada = false;
		this.vida = 10;
	
	}
	
	public void dibujar()
	{
		e.dibujarImagen(img, this.x + 10, this.y, 0, escala);
	}

}
