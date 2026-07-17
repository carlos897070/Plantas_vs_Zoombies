package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class BolaCarbon extends Proyectil {
	
	double dA;
	Image[] img;
	
	
	public BolaCarbon(double x, double y, Entorno e) {

		this.x = x;
		this.y = y;
		this.e = e;
		this.img = new Image[] {
			    Herramientas.cargarImagen("Imagenes/bolaCarbon1.png"),
			    Herramientas.cargarImagen("Imagenes/bolaCarbon2.png"),
			    Herramientas.cargarImagen("Imagenes/bolaCarbon3.png")};
		this.escala = 0.055;
		this.dx = 2;
		this.dA = 0;
		this.imgDaño = Herramientas.cargarImagen("Imagenes/impacto.gif");
	}
	
	
	public double rotacionBola()
	{
		return this.dA += 8;
	}
	
	int frameActual = 0;
	int contador = 0;
	
	public void dibujar()
	{
		e.dibujarImagen(img[frameActual], x, y, this.rotacionBola(), escala);
	    contador++;
	    
	    if (contador % 15 == 0) { // cambia cada 8 ciclos
	        frameActual = (frameActual + 1) % img.length;
	    }
	}
	

}
