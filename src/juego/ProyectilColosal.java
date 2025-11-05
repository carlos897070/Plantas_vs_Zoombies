package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class ProyectilColosal {
	
	double x, y, escala, velocidad;
	Image img;
	Entorno e;
	
	public ProyectilColosal(double x, double y, double escala, Entorno e) {
		
		this.x = x;
		this.y = y;
		this.escala = escala;
		this.e = e;
		this.velocidad = 1;
		this.img = Herramientas.cargarImagen("Imagenes/discoAzul.gif");
	}
	
	public void dibujar()
	{
		e.dibujarImagen(this.img, this.x, this.y, 0, escala);
	}
	
	public void mover()
	{
		this.x -= velocidad;
	}

}
