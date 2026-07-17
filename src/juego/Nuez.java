package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Nuez extends Planta {

	Image exp;
	
	public Nuez(double x, double y, Entorno e) {
		this.x = x;
		this.y = y;
		this.e = e;
		this.img = Herramientas.cargarImagen("Imagenes/nuez.gif");
		this.exp = Herramientas.cargarImagen("Imagenes/explosion.gif");
		this.escala = 0.3;
		this.seleccion = false;
		this.plantada = false;
		this.colorSeleccion = new Color(200, 200, 20, 120);
		this.dxA = 0;
		this.vida = 10;
	}
	
	
	public void dibujar()
	{
		if(this.seleccion)
		{
			e.dibujarRectangulo(this.x-3, this.y+5, 109.616, 93.6, this.rotacionSeleccion() , colorSeleccion);
		}
		if (this.seleccionadaParaMover) 
		{
	        e.dibujarRectangulo(this.x-3, this.y+5, 109.616, 93.6, 0, colorSeleccion);
	    }
		e.dibujarImagen(img, this.x, this.y, 0, escala);
	}
	
		
	//Metodo para saber si el mause esta encima de una planta
	public boolean encima(double mx, double my) {
		double dist = Math.sqrt(Math.pow(mx - this.x, 2) + Math.pow(my - this.y, 2));  

	    return dist < 30;
	}
	
	public void nuevaPosicion(double nuevaX, double nuevaY) {
	    this.x = nuevaX +5;
	    this.y = nuevaY - 5;
	}
	
}
