package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Zombie {

	double x, y, escala, velocidad, vida, tiempoUltimoDisparo, intervaloDisparo;
	Image img, explocion;
	Entorno e;
	
	public Zombie(double x, double y, Entorno e) {
		
		this.x = x;
		this.y = y;
		this.e = e;
		this.img = Herramientas.cargarImagen("Imagenes/zombieComun.gif");
		this.escala = 0.9;
		this.velocidad = 0.3;
		this.vida = 16;
		this.explocion = Herramientas.cargarImagen("Imagenes/humo2.gif");
		this.tiempoUltimoDisparo = 0;
		this.intervaloDisparo = 8;
	}
	public void dibujar()
	{
		e.dibujarImagen(img, this.x, this.y, 0, escala);
	}
	
	public void mover()
	{
		this.x -= velocidad;
	}
	
	public void muerto()
	{
		e.dibujarImagen(explocion, this.x, this.y, 0, 1);
	}
	
	
}
