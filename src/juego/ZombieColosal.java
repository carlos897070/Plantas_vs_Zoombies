package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class ZombieColosal extends ZombieEstandar {

	Image disco;
	
	
	public ZombieColosal(double x, double y, Entorno e) {

		this.x = x;
		this.y = y;
		this.e = e;
		this.escala = 2.13;
		this.vida = 130;
		this.velocidad = 0.2;
		this.img = Herramientas.cargarImagen("Imagenes/zombieJefe.gif");
		this.disco = Herramientas.cargarImagen("Imagenes/discoAzul.gif");
		this.tiempoUltimoDisparo = 0;
		this.intervaloDisparo = 13;
	}
	
}
