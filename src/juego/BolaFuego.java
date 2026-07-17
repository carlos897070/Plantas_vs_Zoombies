package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class BolaFuego extends Proyectil {
	
	
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
	

}
