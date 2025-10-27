package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class ZombieCerebro {

	double x, y, escala, velocidad, vida;
	Image img, explocion;
	Entorno e;
	
	public ZombieCerebro(double x, double y, Entorno e) {
			
			this.x = x;
			this.y = y;
			this.e = e;
			this.img = Herramientas.cargarImagen("Imagenes/zombieCerebro2.gif");
			this.escala = 0.35;
			this.velocidad = 0.7;
			this.vida = 16;
			this.explocion = Herramientas.cargarImagen("Imagenes/humo2.gif");
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
