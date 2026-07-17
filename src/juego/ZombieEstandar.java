package juego;

import java.awt.Image;

import entorno.Entorno;

public class ZombieEstandar {

	double x, y, escala, velocidad, vida, tiempoUltimoDisparo, intervaloDisparo;
	Image img, explocion;
	Entorno e;
	
	public void mover()
	{
		this.x -= velocidad;
	}
	
	public void dibujar()
	{
		e.dibujarImagen(img, this.x, this.y, 0, escala);
	}
}
