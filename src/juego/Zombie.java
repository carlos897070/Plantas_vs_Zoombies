package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Zombie extends ZombieEstandar{

	
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
	
}
