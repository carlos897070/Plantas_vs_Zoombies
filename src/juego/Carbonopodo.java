package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Carbonopodo extends Planta {
	
	Image [] img;
	
	public Carbonopodo(double x, double y, Entorno e) {
		
		this.x = x;
		this.y = y;
		this.e = e;
		this.escala	= 0.09;
		this.img = new Image[] {
			    Herramientas.cargarImagen("Imagenes/guisanteDark5.png"),
			    Herramientas.cargarImagen("Imagenes/guisanteDark1.png"),
			    Herramientas.cargarImagen("Imagenes/guisanteDark2.png"),
			    Herramientas.cargarImagen("Imagenes/guisanteDark3.png")
			    };
		this.seleccion = false;
		this.plantada = false;
		this.tiempoUltimoDisparo = 0;
		this.intervaloDisparo = 1.5;
		this.seleccionadaParaMover = false;
		this.colorSeleccion = new Color(0, 0, 0, 120);
		this.dxA = 0;
		this.vida = 20;
		this.disparando = false;
	}
	
	
	
	//Metodo para dibujar la planta
	
	int frameActual = 0;
	int contador = 0;
	public void dibujar()
	{
		if(this.seleccion) e.dibujarRectangulo(this.x, this.y, 109.616, 93.6, this.rotacionSeleccion() , colorSeleccion);
		
		if (this.seleccionadaParaMover) e.dibujarRectangulo(this.x+2, this.y, 109.616, 93.6, 0, colorSeleccion);
		
		e.dibujarImagen(img[frameActual], x, y, 0, escala);
		
	    contador++;
	    
	    if (contador % 15 == 0) frameActual = (frameActual + 1) % img.length;
	}
	
}

