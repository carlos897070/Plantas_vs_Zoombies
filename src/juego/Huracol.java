package juego;

import java.awt.Color;

import entorno.Entorno;
import entorno.Herramientas;

public class Huracol extends Planta {

	
	public Huracol(double x, double y, Entorno e) {
		
		this.x = x;
		this.y = y;
		this.e = e;
		this.img = Herramientas.cargarImagen("Imagenes/plantaHuracol.gif");
		this.escala = 0.7;
		this.seleccion = false;
		this.plantada = false;
		this.tiempoUltimoDisparo = 0;
		this.intervaloDisparo = 1.5;
		this.colorSeleccion = new Color(0, 250, 0, 100);
		this.dxA = 0;
		this.vida = 20;
		this.disparando = false;
	}
	
	
	public void dibujar()
	{
		if(this.seleccion)
		{
			e.dibujarRectangulo(this.x+0.4, this.y+5.5, 109.616, 93.6, this.rotacionSeleccion() , colorSeleccion);
		}
		if (this.seleccionadaParaMover)
		{
	        e.dibujarRectangulo(this.x, this.y+5, 109.616, 93.6, 0, colorSeleccion);
	    }
		e.dibujarImagen(img, this.x, this.y, 0, escala);
	}
	
	
	
	public void nuevaPosicion(double nuevaX, double nuevaY) {
	    this.x = nuevaX;
	    this.y = nuevaY - 5;
	}
	
	
}
