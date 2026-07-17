package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Zombie4 extends ZombieEstandar {
	
	public Zombie4(double x, double y, Entorno e) {
			
			this.x = x;
			this.y = y;
			this.e = e;
			this.img = Herramientas.cargarImagen("Imagenes/zombieBomba.gif");
			this.escala = 0.45;
			this.velocidad = 0.45;
			this.vida = 16;
			this.explocion = Herramientas.cargarImagen("Imagenes/humo2.gif");
		}

	
}
