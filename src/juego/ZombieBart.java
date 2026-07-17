package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class ZombieBart extends ZombieEstandar {
	
public ZombieBart(double x, double y, Entorno e) {
		
		this.x = x;
		this.y = y;
		this.e = e;
		this.img = Herramientas.cargarImagen("Imagenes/zombieBart.gif");
		this.escala = 0.25;
		this.velocidad = 0.4;
		this.vida = 16;
		this.tiempoUltimoDisparo = 0;
		this.intervaloDisparo = 10;
		
	}

	
}
