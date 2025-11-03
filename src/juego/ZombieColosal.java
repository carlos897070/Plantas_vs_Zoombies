package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class ZombieColosal {

	double x, y, escala, vida, velocidad, ultimoTiempoDisparo, intervaloDisparo;
	Image img, disco;
	Entorno e;
	
	
	public ZombieColosal(double x, double y, Entorno e) {

		this.x = x;
		this.y = y;
		this.e = e;
		this.escala = 2.13;
		this.vida = 100;
		this.velocidad = 0.1;
		this.img = Herramientas.cargarImagen("Imagenes/zombieJefe.gif");
		this.disco = Herramientas.cargarImagen("Imagenes/discoAzul.gif");
		this.ultimoTiempoDisparo = 0;
		this.intervaloDisparo = 11;
	}
	
	public void dibujar()
	{
		//e.dibujarImagen(disco, this.x, this.y, 0, 2);
		e.dibujarImagen(img, this.x, this.y, 0, escala);
	}
	
	public void mover()
	{
		this.x -= velocidad;
	}
	
	
}
